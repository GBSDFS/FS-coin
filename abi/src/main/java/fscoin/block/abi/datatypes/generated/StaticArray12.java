package fscoin.block.abi.datatypes.generated;

import java.util.List;
import fscoin.block.abi.datatypes.StaticArray;
import fscoin.block.abi.datatypes.Type;


public class StaticArray12<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray12(List<T> values) {
        super(12, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray12(T... values) {
        super(12, values);
    }

    public StaticArray12(Class<T> type, List<T> values) {
        super(type, 12, values);
    }

    @SafeVarargs
    public StaticArray12(Class<T> type, T... values) {
        super(type, 12, values);
    }
}
