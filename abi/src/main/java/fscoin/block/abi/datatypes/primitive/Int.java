
package fscoin.block.abi.datatypes.primitive;

import fscoin.block.abi.datatypes.NumericType;
import fscoin.block.abi.datatypes.generated.Int32;

public final class Int extends Number<java.lang.Integer> {

    public Int(int value) {
        super(value);
    }

    @Override
    public NumericType toSolidityType() {
        return new Int32(getValue());
    }
}
