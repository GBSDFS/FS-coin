package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Int;


public class Int176 extends Int {
    public static final Int176 DEFAULT = new Int176(BigInteger.ZERO);

    public Int176(BigInteger value) {
        super(176, value);
    }

    public Int176(long value) {
        this(BigInteger.valueOf(value));
    }
}
