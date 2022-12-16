import java.math.BigInteger;
import java.util.Random;


/* This RSA class will be responsible for generating public and private keys for
each user. This class will also perform RSA encryption and decryption.
 */
public class RSA {

    BigInteger p;
    BigInteger q;
    BigInteger N;
    BigInteger n;
    BigInteger e;
    BigInteger d;


    /* This method will be called by the User class constructor when a new user
    is registered. The method will first find a 1024 bit long integer for both
    p and q. N will be calculated by multiplying p and q.


    The value n will be calculated with the formula n = (p - 1) * (q - 1). Then e is found
    by checking for a relative prime number with n.
     */
    public void genPublicKey() {

        // Find random prime number 1024 bits long
        p = BigInteger.probablePrime(1024, new Random());
        q = BigInteger.probablePrime(1024, new Random());
        // N = pq
        N = p.multiply(q);

        // n = (p - 1) * (q - 1)
        n = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));

        boolean findingRelativePrime = true;

        /* This while loop will keep calling the findRandom() method
        to obtain a random BigInteger. Then it will check to see if it shares
        a gcd of 1. If so, that will now be the value for e.
         */
        while (findingRelativePrime) {
            e= findRandom();
            if (n.gcd(e).equals(BigInteger.valueOf(1))) {
                findingRelativePrime = false;
            }
        }

        // d = e mod n, (This is the private key calculation)
        d = e.modInverse(n);
    }

    // This method will find a random BigInteger
    public BigInteger findRandom() {
        BigInteger maxLimit = new BigInteger("1000000");
        BigInteger minLimit = new BigInteger("3");
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger res = new BigInteger(len, randNum);
        if (res.compareTo(minLimit) < 0)
            res = res.add(minLimit);
        if (res.compareTo(bigInteger) >= 0)
            res = res.mod(bigInteger).add(minLimit);
        return res;
    }

    // Convert String message into BigInteger
    public static BigInteger toBigInteger(String foo)
    {
        return new BigInteger(foo.getBytes());
    }

    // Convert BigInteger into String message
    public static String fromBigInteger(BigInteger bar)
    {
        return new String(bar.toByteArray());
    }

    // RSA encryption. C = M^e mod N
    public static BigInteger encrypt(String M, BigInteger N, BigInteger e) {
        BigInteger message = RSA.toBigInteger(M);
        BigInteger cipherText = message.modPow(e, N);
        return cipherText;
    }

    // RSA decryption. M = C^d mod N
    public static String decrypt(BigInteger c, BigInteger N, BigInteger d) {
        BigInteger message = c.modPow(d, N);
        String M = fromBigInteger(message);
        return M;
    }
}
