package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Uint;


public class Uint48 extends Uint {
    public static final Uint48 DEFAULT = new Uint48(BigInteger.ZERO);

    public Uint48(BigInteger value) {
        super(48, value);
    }

    public Uint48(long value) {
        this(BigInteger.valueOf(value));
    }
}
