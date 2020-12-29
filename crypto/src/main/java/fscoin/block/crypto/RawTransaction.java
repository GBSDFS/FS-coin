
package fscoin.block.crypto;

import java.math.BigInteger;

import fscoin.block.utils.Numeric;

public class RawTransaction {

    private BigInteger nonce;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private String to;
    private BigInteger value;
    private String data;
    private BigInteger gasPremium;
    private BigInteger feeCap;

    protected RawTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String data) {
        this(nonce, gasPrice, gasLimit, to, value, data, null, null);
    }

    protected RawTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String data,
            BigInteger gasPremium,
            BigInteger feeCap) {
        this.nonce = nonce;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.to = to;
        this.value = value;
        this.data = data != null ? Numeric.cleanHexPrefix(data) : null;
        this.gasPremium = gasPremium;
        this.feeCap = feeCap;
    }

    public static RawTransaction createContractTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            BigInteger value,
            String init) {

        return new RawTransaction(nonce, gasPrice, gasLimit, "", value, init);
    }

    public static RawTransaction createEtherTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value) {

        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, "");
    }

    public static RawTransaction createEtherTransaction(
            BigInteger nonce,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            BigInteger gasPremium,
            BigInteger feeCap) {
        return new RawTransaction(nonce, null, gasLimit, to, value, "", gasPremium, feeCap);
    }

    public static RawTransaction createTransaction(
            BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, String data) {
        return createTransaction(nonce, gasPrice, gasLimit, to, BigInteger.ZERO, data);
    }

    public static RawTransaction createTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String data) {

        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data);
    }

    public static RawTransaction createTransaction(
            BigInteger nonce,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            BigInteger value,
            String data,
            BigInteger gasPremium,
            BigInteger feeCap) {

        return new RawTransaction(nonce, gasPrice, gasLimit, to, value, data, gasPremium, feeCap);
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public String getTo() {
        return to;
    }

    public BigInteger getValue() {
        return value;
    }

    public String getData() {
        return data;
    }

    public BigInteger getGasPremium() {
        return gasPremium;
    }

    public BigInteger getFeeCap() {
        return feeCap;
    }

    public boolean isLegacyTransaction() {
        return gasPrice != null && gasPremium == null && feeCap == null;
    }

    public boolean isEIP1559Transaction() {
        return gasPrice == null && gasPremium != null && feeCap != null;
    }
}
