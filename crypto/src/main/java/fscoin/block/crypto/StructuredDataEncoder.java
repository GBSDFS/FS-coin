
package fscoin.block.crypto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import fscoin.block.abi.TypeEncoder;
import fscoin.block.abi.datatypes.AbiTypes;
import fscoin.block.abi.datatypes.Type;
import fscoin.block.utils.Numeric;

import static fscoin.block.crypto.Hash.sha3;
import static fscoin.block.crypto.Hash.sha3String;

public class StructuredDataEncoder {
    public final StructuredData.EIP712Message jsonMessageObject;


    final String arrayTypeRegex = "^([a-zA-Z_$][a-zA-Z_$0-9]*)((\\[([1-9]\\d*)?\\])+)$";
    final Pattern arrayTypePattern = Pattern.compile(arrayTypeRegex);

    final String bytesTypeRegex = "^bytes[0-9][0-9]?$";
    final Pattern bytesTypePattern = Pattern.compile(bytesTypeRegex);
    final String arrayDimensionRegex = "\\[([1-9]\\d*)?\\]";
    final Pattern arrayDimensionPattern = Pattern.compile(arrayDimensionRegex);


    final String typeRegex = "^[a-zA-Z_$][a-zA-Z_$0-9]*(\\[([1-9]\\d*)*\\])*$";
    final Pattern typePattern = Pattern.compile(typeRegex);
    final String identifierRegex = "^[a-zA-Z_$][a-zA-Z_$0-9]*$";
    final Pattern identifierPattern = Pattern.compile(identifierRegex);

    public StructuredDataEncoder(String jsonMessageInString) throws IOException, RuntimeException {
        this.jsonMessageObject = parseJSONMessage(jsonMessageInString);
    }

    public Set<String> getDependencies(String primaryType) {
        HashMap<String, List<StructuredData.Entry>> types = jsonMessageObject.getTypes();
        Set<String> deps = new HashSet<>();

        if (!types.containsKey(primaryType)) {
            return deps;
        }

        List<String> remainingTypes = new ArrayList<>();
        remainingTypes.add(primaryType);

        while (remainingTypes.size() > 0) {
            String structName = remainingTypes.get(remainingTypes.size() - 1);
            remainingTypes.remove(remainingTypes.size() - 1);
            deps.add(structName);

            for (StructuredData.Entry entry : types.get(primaryType)) {
                if (!types.containsKey(entry.getType())) {
                } else if (deps.contains(entry.getType())) {
                } else {
                    remainingTypes.add(entry.getType());
                }
            }
        }

        return deps;
    }

    public String encodeStruct(String structName) {
        HashMap<String, List<StructuredData.Entry>> types = jsonMessageObject.getTypes();

        StringBuilder structRepresentation = new StringBuilder(structName + "(");
        for (StructuredData.Entry entry : types.get(structName)) {
            structRepresentation.append(String.format("%s %s,", entry.getType(), entry.getName()));
        }
        structRepresentation =
                new StringBuilder(
                        structRepresentation.substring(0, structRepresentation.length() - 1));
        structRepresentation.append(")");

        return structRepresentation.toString();
    }

    public String encodeType(String primaryType) {
        Set<String> deps = getDependencies(primaryType);
        deps.remove(primaryType);

        List<String> depsAsList = new ArrayList<>(deps);
        Collections.sort(depsAsList);
        depsAsList.add(0, primaryType);

        StringBuilder result = new StringBuilder();
        for (String structName : depsAsList) {
            result.append(encodeStruct(structName));
        }

        return result.toString();
    }

    public byte[] typeHash(String primaryType) {
        return Numeric.hexStringToByteArray(sha3String(encodeType(primaryType)));
    }

    public List<Integer> getArrayDimensionsFromDeclaration(String declaration) {
        Matcher arrayTypeMatcher = arrayTypePattern.matcher(declaration);
        arrayTypeMatcher.find();
        String dimensionsString = arrayTypeMatcher.group(1);
        Matcher dimensionTypeMatcher = arrayDimensionPattern.matcher(dimensionsString);
        List<Integer> dimensions = new ArrayList<>();
        while (dimensionTypeMatcher.find()) {
            String currentDimension = dimensionTypeMatcher.group(1);
            if (currentDimension == null) {
                dimensions.add(Integer.parseInt("-1"));
            } else {
                dimensions.add(Integer.parseInt(currentDimension));
            }
        }

        return dimensions;
    }

    @SuppressWarnings("unchecked")
    public List<Pair> getDepthsAndDimensions(Object data, int depth) {
        if (!(data instanceof List)) {
            return new ArrayList<>();
        }

        List<Pair> list = new ArrayList<>();
        List<Object> dataAsArray = (List<Object>) data;
        list.add(new Pair(depth, dataAsArray.size()));
        for (Object subdimensionalData : dataAsArray) {
            list.addAll(getDepthsAndDimensions(subdimensionalData, depth + 1));
        }

        return list;
    }

    public List<Integer> getArrayDimensionsFromData(Object data) throws RuntimeException {
        List<Pair> depthsAndDimensions = getDepthsAndDimensions(data, 0);
        Map<Object, List<Pair>> groupedByDepth =
                depthsAndDimensions.stream().collect(Collectors.groupingBy(Pair::getFirst));
        Map<Integer, List<Integer>> depthDimensionsMap = new HashMap<>();
        for (Map.Entry<Object, List<Pair>> entry : groupedByDepth.entrySet()) {
            List<Integer> pureDimensions = new ArrayList<>();
            for (Pair depthDimensionPair : entry.getValue()) {
                pureDimensions.add((Integer) depthDimensionPair.getSecond());
            }
            depthDimensionsMap.put((Integer) entry.getKey(), pureDimensions);
        }

        List<Integer> dimensions = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : depthDimensionsMap.entrySet()) {
            Set<Integer> setOfDimensionsInParticularDepth = new TreeSet<>(entry.getValue());
            if (setOfDimensionsInParticularDepth.size() != 1) {
                throw new RuntimeException(
                        String.format(
                                "Depth %d of array data has more than one dimensions",
                                entry.getKey()));
            }
            dimensions.add(setOfDimensionsInParticularDepth.stream().findFirst().get());
        }

        return dimensions;
    }

    public List<Object> flattenMultidimensionalArray(Object data) {
        if (!(data instanceof List)) {
            return new ArrayList<Object>() {
                {
                    add(data);
                }
            };
        }

        List<Object> flattenedArray = new ArrayList<>();
        for (Object arrayItem : (List) data) {
            flattenedArray.addAll(flattenMultidimensionalArray(arrayItem));
        }

        return flattenedArray;
    }

    @SuppressWarnings("unchecked")
    public byte[] encodeData(String primaryType, HashMap<String, Object> data)
            throws RuntimeException {
        HashMap<String, List<StructuredData.Entry>> types = jsonMessageObject.getTypes();

        List<String> encTypes = new ArrayList<>();
        List<Object> encValues = new ArrayList<>();

        encTypes.add("bytes32");
        encValues.add(typeHash(primaryType));

        for (StructuredData.Entry field : types.get(primaryType)) {
            Object value = data.get(field.getName());

            if (field.getType().equals("string")) {
                encTypes.add("bytes32");
                byte[] hashedValue = Numeric.hexStringToByteArray(sha3String((String) value));
                encValues.add(hashedValue);
            } else if (field.getType().equals("bytes")) {
                encTypes.add(("bytes32"));
                encValues.add(sha3(Numeric.hexStringToByteArray((String) value)));
            } else if (types.containsKey(field.getType())) {
                byte[] hashedValue =
                        sha3(encodeData(field.getType(), (HashMap<String, Object>) value));
                encTypes.add("bytes32");
                encValues.add(hashedValue);
            } else if (bytesTypePattern.matcher(field.getType()).find()) {
                encTypes.add(field.getType());
                encValues.add(Numeric.hexStringToByteArray((String) value));
            } else if (arrayTypePattern.matcher(field.getType()).find()) {
                String baseTypeName = field.getType().substring(0, field.getType().indexOf('['));
                List<Integer> expectedDimensions =
                        getArrayDimensionsFromDeclaration(field.getType());
                List<Integer> dataDimensions = getArrayDimensionsFromData(value);

                final String format =
                        String.format(
                                "Array Data %s has dimensions %s, "
                                        + "but expected dimensions are %s",
                                value.toString(),
                                dataDimensions.toString(),
                                expectedDimensions.toString());
                if (expectedDimensions.size() != dataDimensions.size()) {
                    throw new RuntimeException(format);
                }
                for (int i = 0; i < expectedDimensions.size(); i++) {
                    if (expectedDimensions.get(i) == -1) {
                        continue;
                    }
                    if (!expectedDimensions.get(i).equals(dataDimensions.get(i))) {
                        throw new RuntimeException(format);
                    }
                }

                List<Object> arrayItems = flattenMultidimensionalArray(value);
                ByteArrayOutputStream concatenatedArrayEncodingBuffer = new ByteArrayOutputStream();
                for (Object arrayItem : arrayItems) {
                    byte[] arrayItemEncoding =
                            encodeData(baseTypeName, (HashMap<String, Object>) arrayItem);
                    concatenatedArrayEncodingBuffer.write(
                            arrayItemEncoding, 0, arrayItemEncoding.length);
                }
                byte[] concatenatedArrayEncodings = concatenatedArrayEncodingBuffer.toByteArray();
                byte[] hashedValue = sha3(concatenatedArrayEncodings);
                encTypes.add("bytes32");
                encValues.add(hashedValue);
            } else {
                encTypes.add(field.getType());
                encValues.add(value);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < encTypes.size(); i++) {
            Class<Type> typeClazz = (Class<Type>) AbiTypes.getType(encTypes.get(i));

            boolean atleastOneConstructorExistsForGivenParametersType = false;
            Constructor[] constructors = typeClazz.getConstructors();
            for (Constructor constructor : constructors) {
                try {
                    Class[] parameterTypes = constructor.getParameterTypes();
                    byte[] temp =
                            Numeric.hexStringToByteArray(
                                    TypeEncoder.encode(
                                            typeClazz
                                                    .getDeclaredConstructor(parameterTypes)
                                                    .newInstance(encValues.get(i))));
                    baos.write(temp, 0, temp.length);
                    atleastOneConstructorExistsForGivenParametersType = true;
                    break;
                } catch (IllegalArgumentException
                        | NoSuchMethodException
                        | InstantiationException
                        | IllegalAccessException
                        | InvocationTargetException ignored) {
                }
            }

            if (!atleastOneConstructorExistsForGivenParametersType) {
                throw new RuntimeException(
                        String.format(
                                "Received an invalid argument for which no constructor"
                                        + " exists for the ABI Class %s",
                                typeClazz.getSimpleName()));
            }
        }
        byte[] result = baos.toByteArray();

        return result;
    }

    public byte[] hashMessage(String primaryType, HashMap<String, Object> data)
            throws RuntimeException {
        return sha3(encodeData(primaryType, data));
    }

    @SuppressWarnings("unchecked")
    public byte[] hashDomain() throws RuntimeException {
        ObjectMapper oMapper = new ObjectMapper();
        HashMap<String, Object> data =
                oMapper.convertValue(jsonMessageObject.getDomain(), HashMap.class);

        if (data.get("chainId") != null) {
            data.put("chainId", ((HashMap<String, Object>) data.get("chainId")).get("value"));
        } else {
            data.remove("chainId");
        }

        data.put(
                "verifyingContract",
                ((HashMap<String, Object>) data.get("verifyingContract")).get("value"));

        if (data.get("salt") == null) {
            data.remove("salt");
        }
        return sha3(encodeData("EIP712Domain", data));
    }

    public void validateStructuredData(StructuredData.EIP712Message jsonMessageObject)
            throws RuntimeException {
        for (String structName : jsonMessageObject.getTypes().keySet()) {
            List<StructuredData.Entry> fields = jsonMessageObject.getTypes().get(structName);
            for (StructuredData.Entry entry : fields) {
                if (!identifierPattern.matcher(entry.getName()).find()) {
                    throw new RuntimeException(
                            String.format(
                                    "Invalid Identifier %s in %s", entry.getName(), structName));
                }
                if (!typePattern.matcher(entry.getType()).find()) {
                    throw new RuntimeException(
                            String.format("Invalid Type %s in %s", entry.getType(), structName));
                }
            }
        }
    }

    public StructuredData.EIP712Message parseJSONMessage(String jsonMessageInString)
            throws IOException, RuntimeException {
        ObjectMapper mapper = new ObjectMapper();
        StructuredData.EIP712Message tempJSONMessageObject =
                mapper.readValue(jsonMessageInString, StructuredData.EIP712Message.class);
        validateStructuredData(tempJSONMessageObject);

        return tempJSONMessageObject;
    }

    @SuppressWarnings("unchecked")
    public byte[] hashStructuredData() throws RuntimeException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final String messagePrefix = "\u0019\u0001";
        byte[] prefix = messagePrefix.getBytes();
        baos.write(prefix, 0, prefix.length);

        byte[] domainHash = hashDomain();
        baos.write(domainHash, 0, domainHash.length);

        byte[] dataHash =
                hashMessage(
                        jsonMessageObject.getPrimaryType(),
                        (HashMap<String, Object>) jsonMessageObject.getMessage());
        baos.write(dataHash, 0, dataHash.length);

        byte[] result = baos.toByteArray();
        return sha3(result);
    }
}
