package fscoin.block.abi.datatypes.generated;

import java.util.List;
import fscoin.block.abi.datatypes.StaticArray;
import fscoin.block.abi.datatypes.Type;


public class StaticArray3<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray3(List<T> values) {
        super(3, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray3(T... values) {
        super(3, values);
    }

    public StaticArray3(Class<T> type, List<T> values) {
        super(type, 3, values);
    }

    @SafeVarargs
    public StaticArray3(Class<T> type, T... values) {
        super(type, 3, values);
    }
}