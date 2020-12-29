package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Int;

public class Int104 extends Int {
    public static final Int104 DEFAULT = new Int104(BigInteger.ZERO);

    public Int104(BigInteger value) {
        super(104, value);
    }

    public Int104(long value) {
        this(BigInteger.valueOf(value));
    }
}
