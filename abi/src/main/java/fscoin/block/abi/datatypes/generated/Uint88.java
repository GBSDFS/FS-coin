package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Uint;


public class Uint88 extends Uint {
    public static final Uint88 DEFAULT = new Uint88(BigInteger.ZERO);

    public Uint88(BigInteger value) {
        super(88, value);
    }

    public Uint88(long value) {
        this(BigInteger.valueOf(value));
    }
}
