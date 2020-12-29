package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Uint;


public class Uint16 extends Uint {
    public static final Uint16 DEFAULT = new Uint16(BigInteger.ZERO);

    public Uint16(BigInteger value) {
        super(16, value);
    }

    public Uint16(long value) {
        this(BigInteger.valueOf(value));
    }
}
