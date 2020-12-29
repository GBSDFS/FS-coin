package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Int;

public class Int72 extends Int {
    public static final Int72 DEFAULT = new Int72(BigInteger.ZERO);

    public Int72(BigInteger value) {
        super(72, value);
    }

    public Int72(long value) {
        this(BigInteger.valueOf(value));
    }
}
