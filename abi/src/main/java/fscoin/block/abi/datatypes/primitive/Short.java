
package fscoin.block.abi.datatypes.primitive;

import fscoin.block.abi.datatypes.NumericType;
import fscoin.block.abi.datatypes.generated.Int16;

public final class Short extends Number<java.lang.Short> {

    public Short(short value) {
        super(value);
    }

    @Override
    public NumericType toSolidityType() {
        return new Int16(getValue());
    }
}
