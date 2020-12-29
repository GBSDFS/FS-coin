package fscoin.block.abi.datatypes.generated;

import java.util.List;
import fscoin.block.abi.datatypes.StaticArray;
import fscoin.block.abi.datatypes.Type;


public class StaticArray14<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray14(List<T> values) {
        super(14, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray14(T... values) {
        super(14, values);
    }

    public StaticArray14(Class<T> type, List<T> values) {
        super(type, 14, values);
    }

    @SafeVarargs
    public StaticArray14(Class<T> type, T... values) {
        super(type, 14, values);
    }
}