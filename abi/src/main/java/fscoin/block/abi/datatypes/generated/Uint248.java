package fscoin.block.abi.datatypes.generated;

import java.math.BigInteger;
import fscoin.block.abi.datatypes.Uint;

public class Uint248 extends Uint {
    public static final Uint248 DEFAULT = new Uint248(BigInteger.ZERO);

    public Uint248(BigInteger value) {
        super(248, value);
    }

    public Uint248(long value) {
        this(BigInteger.valueOf(value));
    }
}
