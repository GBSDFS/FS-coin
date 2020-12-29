
package fscoin.block.abi.datatypes.primitive;

import fscoin.block.abi.datatypes.Type;
import fscoin.block.abi.datatypes.Utf8String;

public final class Char extends PrimitiveType<Character> {

    public Char(char value) {
        super(value);
    }

    @Override
    public Type toSolidityType() {
        return new Utf8String(String.valueOf(getValue()));
    }
}
