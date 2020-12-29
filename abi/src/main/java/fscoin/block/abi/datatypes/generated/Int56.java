package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Int;

public class Int56 extends Int {
    public static final Int56 DEFAULT = new Int56(BigInteger.ZERO);

    public Int56(BigInteger value) {
        super(56, value);
    }

    public Int56(long value) {
        this(BigInteger.valueOf(value));
    }
}
