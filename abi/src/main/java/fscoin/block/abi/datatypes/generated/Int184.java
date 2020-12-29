package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Int;


public class Int184 extends Int {
    public static final Int184 DEFAULT = new Int184(BigInteger.ZERO);

    public Int184(BigInteger value) {
        super(184, value);
    }

    public Int184(long value) {
        this(BigInteger.valueOf(value));
    }
}
