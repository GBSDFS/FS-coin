package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Int;
public class Int248 extends Int {
    public static final Int248 DEFAULT = new Int248(BigInteger.ZERO);

    public Int248(BigInteger value) {
        super(248, value);
    }

    public Int248(long value) {
        this(BigInteger.valueOf(value));
    }
}
