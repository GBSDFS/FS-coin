package fscoin.block.abi.datatypes.generated;

import fscoin.block.abi.datatypes.Bytes;


public class Bytes31 extends Bytes {
    public static final Bytes31 DEFAULT = new Bytes31(new byte[31]);

    public Bytes31(byte[] value) {
        super(31, value);
    }
}
