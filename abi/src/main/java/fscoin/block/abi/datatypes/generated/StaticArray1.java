package fscoin.block.abi.datatypes.generated;

import java.util.List;
import fscoin.block.abi.datatypes.StaticArray;
import fscoin.block.abi.datatypes.Type;

public class StaticArray1<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray1(List<T> values) {
        super(1, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray1(T... values) {
        super(1, values);
    }

    public StaticArray1(Class<T> type, List<T> values) {
        super(type, 1, values);
    }

    @SafeVarargs
    public StaticArray1(Class<T> type, T... values) {
        super(type, 1, values);
    }
}