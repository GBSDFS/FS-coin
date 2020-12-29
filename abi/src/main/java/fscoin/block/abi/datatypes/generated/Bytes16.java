package fscoin.block.abi.datatypes.generated;

import fscoin.block.abi.datatypes.Bytes;


public class Bytes16 extends Bytes {
    public static final Bytes16 DEFAULT = new Bytes16(new byte[16]);

    public Bytes16(byte[] value) {
        super(16, value);
    }
}
