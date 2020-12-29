package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Uint;


public class Uint104 extends Uint {
    public static final Uint104 DEFAULT = new Uint104(BigInteger.ZERO);

    public Uint104(BigInteger value) {
        super(104, value);
    }

    public Uint104(long value) {
        this(BigInteger.valueOf(value));
    }
}
