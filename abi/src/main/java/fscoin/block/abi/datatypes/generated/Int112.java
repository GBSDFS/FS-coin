package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Int;

public class Int112 extends Int {
    public static final Int112 DEFAULT = new Int112(BigInteger.ZERO);

    public Int112(BigInteger value) {
        super(112, value);
    }

    public Int112(long value) {
        this(BigInteger.valueOf(value));
    }
}
