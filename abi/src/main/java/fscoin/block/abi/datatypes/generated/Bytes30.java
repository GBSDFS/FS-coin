package fscoin.block.abi.datatypes.generated;

import fscoin.block.abi.datatypes.Bytes;


public class Bytes30 extends Bytes {
    public static final Bytes30 DEFAULT = new Bytes30(new byte[30]);

    public Bytes30(byte[] value) {
        super(30, value);
    }
}
