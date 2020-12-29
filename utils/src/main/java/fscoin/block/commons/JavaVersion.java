
package fscoin.block.commons;

public class JavaVersion {

    public static String getJavaVersion() {
        return System.getProperty("java.specification.version");
    }

    public static double getJavaVersionAsDouble() {
        return Double.parseDouble(System.getProperty("java.specification.version"));
    }
}
