package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Uint;


public class Uint216 extends Uint {
    public static final Uint216 DEFAULT = new Uint216(BigInteger.ZERO);

    public Uint216(BigInteger value) {
        super(216, value);
    }

    public Uint216(long value) {
        this(BigInteger.valueOf(value));
    }
}
