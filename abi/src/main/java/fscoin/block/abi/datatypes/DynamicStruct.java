
package fscoin.block.abi.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicStruct extends DynamicArray<Type> implements StructType {

    private final List<Class<Type>> itemTypes = new ArrayList<>();

    public DynamicStruct(List<Type> values) {
        this(Type.class, values);
    }

    private DynamicStruct(Class<Type> type, List<Type> values) {
        super(type, values);
        for (Type value : values) {
            itemTypes.add((Class<Type>) value.getClass());
        }
    }

    @Override
    public int bytes32PaddedLength() {
        return super.bytes32PaddedLength() + 32;
    }

    public DynamicStruct(Type... values) {
        this(Arrays.asList(values));
    }

    @SafeVarargs
    public DynamicStruct(Class<Type> type, Type... values) {
        this(type, Arrays.asList(values));
    }

    @Override
    public String getTypeAsString() {
        final StringBuilder type = new StringBuilder("(");
        for (int i = 0; i < itemTypes.size(); ++i) {
            final Class<Type> cls = itemTypes.get(i);
            if (StructType.class.isAssignableFrom(cls)) {
                type.append(getValue().get(i).getTypeAsString());
            } else {
                type.append(AbiTypes.getTypeAString(cls));
            }
            if (i < itemTypes.size() - 1) {
                type.append(",");
            }
        }
        type.append(")");
        return type.toString();
    }
}
