
package fscoin.block.abi;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import fscoin.block.abi.datatypes.Function;
import fscoin.block.abi.datatypes.Type;
import fscoin.block.abi.spi.FunctionEncoderProvider;
import fscoin.block.crypto.Hash;
import fscoin.block.utils.Numeric;

import static fscoin.block.abi.TypeDecoder.instantiateType;
import static fscoin.block.abi.TypeReference.makeTypeReference;


public abstract class FunctionEncoder {

    private static FunctionEncoder DEFAULT_ENCODER;

    private static final ServiceLoader<FunctionEncoderProvider> loader =
            ServiceLoader.load(FunctionEncoderProvider.class);

    public static String encode(final Function function) {
        return encoder().encodeFunction(function);
    }

    public static String encodeConstructor(final List<Type> parameters) {
        return encoder().encodeParameters(parameters);
    }

    public static Function makeFunction(
            String fnname,
            List<String> solidityInputTypes,
            List<Object> arguments,
            List<String> solidityOutputTypes)
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
                    IllegalAccessException, InvocationTargetException {
        List<Type> encodedInput = new ArrayList<>();
        Iterator argit = arguments.iterator();
        for (String st : solidityInputTypes) {
            encodedInput.add(instantiateType(st, argit.next()));
        }
        List<TypeReference<?>> encodedOutput = new ArrayList<>();
        for (String st : solidityOutputTypes) {
            encodedOutput.add(makeTypeReference(st));
        }
        return new Function(fnname, encodedInput, encodedOutput);
    }

    protected abstract String encodeFunction(Function function);

    protected abstract String encodeParameters(List<Type> parameters);

    protected static String buildMethodSignature(
            final String methodName, final List<Type> parameters) {

        final StringBuilder result = new StringBuilder();
        result.append(methodName);
        result.append("(");
        final String params =
                parameters.stream().map(Type::getTypeAsString).collect(Collectors.joining(","));
        result.append(params);
        result.append(")");
        return result.toString();
    }

    protected static String buildMethodId(final String methodSignature) {
        final byte[] input = methodSignature.getBytes();
        final byte[] hash = Hash.sha3(input);
        return Numeric.toHexString(hash).substring(0, 10);
    }

    private static FunctionEncoder encoder() {
        final Iterator<FunctionEncoderProvider> iterator = loader.iterator();
        return iterator.hasNext() ? iterator.next().get() : defaultEncoder();
    }

    private static FunctionEncoder defaultEncoder() {
        if (DEFAULT_ENCODER == null) {
            DEFAULT_ENCODER = new DefaultFunctionEncoder();
        }
        return DEFAULT_ENCODER;
    }
}
