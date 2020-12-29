package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Uint;


public class Uint40 extends Uint {
    public static final Uint40 DEFAULT = new Uint40(BigInteger.ZERO);

    public Uint40(BigInteger value) {
        super(40, value);
    }

    public Uint40(long value) {
        this(BigInteger.valueOf(value));
    }
}
