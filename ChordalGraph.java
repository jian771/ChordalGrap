import java.math.BigInteger;

public class ChordalGraph {

    static BigInteger[] a;
    static BigInteger[] c;

    public static void main(String[] args) {
        int n = 30;
        a = new BigInteger[n + 1];
        c = new BigInteger[n + 1];

        a[0] = BigInteger.ONE;
        c[0] = BigInteger.ZERO;

        for (int i = 1; i <= n; i++) {
            // حساب c(i)
            c[i] = BigInteger.ZERO;
            for (int k = 1; k <= i; k++) {
                BigInteger binom = binomial(i - 1, k - 1);
                c[i] = c[i].add(binom.multiply(a[k - 1]));
            }
            // حساب a(i)
            a[i] = BigInteger.ZERO;
            for (int k = 1; k <= i; k++) {
                BigInteger binom = binomial(i - 1, k - 1);
                a[i] = a[i].add(binom.multiply(c[k]).multiply(a[i - k]));
            }
        }

        System.out.println("n\t c(n)");
        for (int i = 1; i <= n; i++) {
            System.out.println(i + "\t" + c[i]);
        }
    }

    static BigInteger binomial(int n, int k) {
        if (k > n) return BigInteger.ZERO;
        if (k == 0 || k == n) return BigInteger.ONE;
        BigInteger result = BigInteger.ONE;
        for (int i = 0; i < k; i++) {
            result = result.multiply(BigInteger.valueOf(n - i));
            result = result.divide(BigInteger.valueOf(i + 1));
        }
        return result;
    }
}
