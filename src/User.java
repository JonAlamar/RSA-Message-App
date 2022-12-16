import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.security.MessageDigest;

/* This User class will be the blueprint to create and register users. When each new user is registered,
they will be given BigInteger values for N, e, and d, which will make up their public and private key for RSA encryption.
Each user will also be given a unique salt that is constant for each user. The messageInbox string will be used
to hold messages that have been received from other users.
 */
public class User {

    private BigInteger N;
    private BigInteger e;
    private BigInteger d;
    private String userName;
    private String saltedHash;
    private String messageInbox;
    private int salt;


    /* User class constructor that will receive a username and password from the user registering
    from the RegisterGui. The RSA class is instantiated and used to generate a public modulus
    N, a public e, and a private d. These values will be used for RSA.

    The random salt integer is generated for the new user, and then it is passed along with
    the passed into the generateSaltedPassword method. The user will then have a salted
     */
    public User(String userName, String password) {
        RSA rsa = new RSA();
        rsa.genPublicKey();
        this.N = rsa.N;
        this.e = rsa.e;
        this.d = rsa.d;
        this.userName = userName;
        Random random = new Random();
        this.salt = random.nextInt();
        saltedHash = generateSaltedPassword(password, salt);
    }

    public String getUserName() {
        return userName;
    }

    public String getSaltedHash() {
        return saltedHash;
    }

    public int getSalt() {
        return salt;
    }

    public BigInteger getN() {
        return N;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }

    public String getMessageInbox() {
        return messageInbox;
    }

    public void setMessageInbox(String inbox) {
        this.messageInbox = inbox;
    }

    /* This method will generate a salted password using SHA256 cryptographic hash algorithm.
    First the salt is concatenated with the password. The concatenated salt and password
    are put through SHA256 and the output will be the hashed salted password that will be
    stored in the user's saltedhash class variable. This variable will be used to
    verify logins.
     */
    static public String generateSaltedPassword(String password, int salt) {
        String saltAndPass = salt + password;
        byte[] input = saltAndPass.getBytes();

        MessageDigest SHA256 = null;
        try {
            SHA256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        SHA256.update(input);
        byte[] digest = SHA256.digest();

        StringBuffer hexDigest = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            hexDigest.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("SHA256 of " + saltAndPass + " is " + hexDigest);
        return hexDigest.toString();
    }
}
