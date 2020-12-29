package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Int;


public class Int168 extends Int {
    public static final Int168 DEFAULT = new Int168(BigInteger.ZERO);

    public Int168(BigInteger value) {
        super(168, value);
    }

    public Int168(long value) {
        this(BigInteger.valueOf(value));
    }
}
