package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Int;

public class Int208 extends Int {
    public static final Int208 DEFAULT = new Int208(BigInteger.ZERO);

    public Int208(BigInteger value) {
        super(208, value);
    }

    public Int208(long value) {
        this(BigInteger.valueOf(value));
    }
}
