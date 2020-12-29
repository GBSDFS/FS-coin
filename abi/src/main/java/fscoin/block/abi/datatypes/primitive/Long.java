
package fscoin.block.abi.datatypes.primitive;

import fscoin.block.abi.datatypes.NumericType;
import fscoin.block.abi.datatypes.generated.Int64;

public final class Long extends Number<java.lang.Long> {

    public Long(long value) {
        super(value);
    }

    @Override
    public NumericType toSolidityType() {
        return new Int64(getValue());
    }
}
