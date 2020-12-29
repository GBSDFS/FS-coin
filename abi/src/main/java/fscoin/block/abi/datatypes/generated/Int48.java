package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Int;

public class Int48 extends Int {
    public static final Int48 DEFAULT = new Int48(BigInteger.ZERO);

    public Int48(BigInteger value) {
        super(48, value);
    }

    public Int48(long value) {
        this(BigInteger.valueOf(value));
    }
}
