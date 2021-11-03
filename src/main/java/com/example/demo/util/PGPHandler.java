package com.example.demo.util;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.CompressionAlgorithmTags;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.jcajce.JcaPGPObjectFactory;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.jcajce.*;
import org.bouncycastle.util.io.Streams;

import java.io.*;
import java.security.*;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;

public class PGPHandler {

    @SuppressWarnings("restriction")
    public static void main(String[] args) throws Exception {

//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//        String passPhrase_ = "123456789";
//        PGPSecretKey secretKey = PGPHandler.getSecretKey("wathdata", passPhrase_, 2048);
//
//        // 这里打印私钥-------------重要
//        System.out.println("私钥--------------------");
//        String privateKeyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//        System.out.println(privateKeyString);
//
//        PGPPublicKey publicKey = secretKey.getPublicKey();
//        // 这里打印公钥----------------重要
//        System.out.println("公钥--------------------");
//        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
//        System.out.println(publicKeyString);

        //-----------------------------------------


        String publicString = "mQENBGF/oUEBCADGJ1czRFs33yVcyV1vGZ8ZJS5IL/2EH6NrfI/sA1M1eZxkUiLKi+2KE1rmx1D7uzE7ASC3eByS4lx9KDbm+KHxtWeUsZVyK0tYeZ7BKFAYDeW8yzYRzJFBoKHkURciA1ItzhY+kktjpZUy+xX/75jIV1BMe/mJzpf1pfJth/7j4eK5CYVBhj6uSDfaxvE+PE0+9u+JMvLxkSKUgIfEmKy+ZDqV4Ve83V4hJa6PpcmctlkLsfQvR6wFNdOF955OuBWYi1jlzIrFmLTneWvxZT7pUOKE6U/erIkVnu8Xh7pNQHKUOVB7CYbgpxqSWy+hD2aCiwAG+TpC/jSSrc+Wjhv/ABEBAAG0CHdhdGhkYXRhiQEcBBABAgAGBQJhf6FBAAoJECx31WxCByo4/SUIAKaRklnnykiXce7Fkb/sEcotp3+5cGgd6flO7C0dFSVvSOwc0AaRNKUOWWPoQOHW7facwPDrYtAXxmMpObeI8Zo2/fZQXckR+326IV3DBXys12/MsnzndYvz6Nj86lJEGKKL0VbjConlYrgEFyS9qYnKVu8YCvfnX7Ec3tm/Ea351fjjVvS217vGjOFksUoufE9z3tb8yjSIhjTek5ykoMvgFiuK3APb+wfM+ysC+1CtjcVdlv2AP0rkR2X7cOIud3TDr0lEbM0K2zCQv25Tu+UgRMB0mMmAveZxVfSIVwTVwzYegBA0luzy9zipAnOO325nKwmV/QGp8GJFylT2Plg=";

        String privateString = "lQO+BGF/oUEBCADGJ1czRFs33yVcyV1vGZ8ZJS5IL/2EH6NrfI/sA1M1eZxkUiLKi+2KE1rmx1D7uzE7ASC3eByS4lx9KDbm+KHxtWeUsZVyK0tYeZ7BKFAYDeW8yzYRzJFBoKHkURciA1ItzhY+kktjpZUy+xX/75jIV1BMe/mJzpf1pfJth/7j4eK5CYVBhj6uSDfaxvE+PE0+9u+JMvLxkSKUgIfEmKy+ZDqV4Ve83V4hJa6PpcmctlkLsfQvR6wFNdOF955OuBWYi1jlzIrFmLTneWvxZT7pUOKE6U/erIkVnu8Xh7pNQHKUOVB7CYbgpxqSWy+hD2aCiwAG+TpC/jSSrc+Wjhv/ABEBAAH+AwMCR+0/gcOf68tgNe8sqHgOd3McbqknbdJ9WVvVBE0uDFP+lWDYvCWqXDDK/XUdshBLAcLnOFIaVLJMBCTO2SYHgxgUCBc0tw3mDeDu30eHtWsyIAKVtdI5eP9Biwt1n6rqgd/eJJQ74rFI3K3T9o5hSTS50+38fY4KhPBrv/O+WEZnTAtAMueAa8yxJbTzRBjDmGdVV4wydNbAlOtq3rs9KLGF6/d3PhxVS4CxtYUArPmgu3rarvJEk/5N6ZpqUxkamxGrtsU/d1US25EHkhh+PccgxVHakoQU/NHq//wLj51ixTMcwd7rztMeSCXW20V686JotP+38plpf0CzeLfDs4/q43NokYiXvEtspahTcCSr9rz6eack2M+6cZHo7eQMd171dGJmSrm+9oijEaorEt+T/pEF0LB8HBBi7cDx6+8UJ1T0IK6L3vf7njlw/CcmBckzxd/pSdUWVnyQfeqLp8SjsSlP+TeK8U59eYwSBNdzLGe7ljEQNwBdu3uFfy152Z05gXNREZMMR6X/8z8sIefwrSrCghPyMY2F0/7JSt+Gq7EP30LZqO3heelltj+scQlrgwqG+0fDBGQtCrZKr/8WXvBF/Yb+KBTmVl/U4mcFiqsIZ2AHPXo2NhiieABsuTcjYnjbXkwxDnMW84GROyuyBtpbSKV+MpTt19OrdkCY07Jf8xD4FvyMHXv8sd9HEfhK1kU4sAMLq+kpQZKEo83ap1ufE3wuyxAxK4nOZQ6E4Jv/xI0erbp5Ty7BRLePEzVEOzoxmXQha4buVL2OULu0L0csWMmTTmH8JpbLS/2g0sPfEAt+dfzJM8mPNF6VodvwPxF7tiidCFIdG1xVDMqdDNnJlIf9NTIgi1PdQQAGV2aYKXRtcLaKxe6t020ngzWWt6SDfI/H4pagk7QId2F0aGRhdGGJARwEEAECAAYFAmF/oUEACgkQLHfVbEIHKjj9JQgAppGSWefKSJdx7sWRv+wRyi2nf7lwaB3p+U7sLR0VJW9I7BzQBpE0pQ5ZY+hA4dbt9pzA8Oti0BfGYyk5t4jxmjb99lBdyRH7fbohXcMFfKzXb8yyfOd1i/Po2PzqUkQYoovRVuMKieViuAQXJL2picpW7xgK9+dfsRze2b8RrfnV+ONW9LbXu8aM4WSxSi58T3Pe1vzKNIiGNN6TnKSgy+AWK4rcA9v7B8z7KwL7UK2NxV2W/YA/SuRHZftw4i53dMOvSURszQrbMJC/blO75SBEwHSYyYC95nFV9IhXBNXDNh6AEDSW7PL3OKkCc47fbmcrCZX9AanwYkXKVPY+WA==";

        Security.addProvider(new BouncyCastleProvider());
        encryptFile("campaign_aud_2021-10-29.csv.gz.gpg", "campaign_aud_2021-10-29.csv", publicString, true, true); // 加密文件
        decryptFile("campaign_aud_2021-10-29.csv.gz.gpg", "c://", "test.csv", privateString, "123456789".toCharArray());

    }

    /**
     * @param inputFileName   要解密的文件名
     * @param key             私钥
     * @param passwd          私钥解密key
     * @param defaultFileName 输出解密的文件
     * @throws IOException
     * @throws NoSuchProviderException
     */
    public static void decryptFile(String inputFileName, String path, String defaultFileName, String key, char[] passwd) throws IOException {

        InputStream in = new BufferedInputStream(new FileInputStream(inputFileName));
        byte[] decode = Base64.getDecoder().decode(key);
        decryptFile(in, path, defaultFileName, decode, passwd);
        in.close();
    }

    /**
     * decrypt the passed in message stream
     */

    private static void decryptFile(InputStream in, String path, String defaultFileName, byte[] keyIn, char[] passwd) throws IOException {

        in = PGPUtil.getDecoderStream(in);

        try {

            JcaPGPObjectFactory pgpF = new JcaPGPObjectFactory(in);

            PGPEncryptedDataList enc;

            Object o = pgpF.nextObject();

            // the first object might be a PGP marker packet.
            if (o instanceof PGPEncryptedDataList) {
                enc = (PGPEncryptedDataList) o;
            } else {
                enc = (PGPEncryptedDataList) pgpF.nextObject();
            }

            // find the secret key
            Iterator it = enc.getEncryptedDataObjects();

            PGPPrivateKey sKey = null;

            PGPPublicKeyEncryptedData pbe = null;

            PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(keyIn, new JcaKeyFingerprintCalculator());

            while (sKey == null && it.hasNext()) {

                pbe = (PGPPublicKeyEncryptedData) it.next();

                sKey = com.cathaypacific.csp.campaign.utils.PGPHandleUtil.findPrivateKey(pgpSec, pbe.getKeyID(), passwd);

            }

            if (sKey == null) {
                throw new IllegalArgumentException("secret key for message not found.");
            }

            InputStream clear = pbe.getDataStream(new JcePublicKeyDataDecryptorFactoryBuilder().setProvider("BC").build(sKey));

            JcaPGPObjectFactory plainFact = new JcaPGPObjectFactory(clear);

            Object message = plainFact.nextObject();

            if (message instanceof PGPCompressedData) {

                PGPCompressedData cData = (PGPCompressedData) message;

                JcaPGPObjectFactory pgpFact = new JcaPGPObjectFactory(cData.getDataStream());

                message = pgpFact.nextObject();

            }

            if (message instanceof PGPLiteralData) {

                PGPLiteralData ld = (PGPLiteralData) message;

                String outFileName = ld.getFileName();

                if (outFileName.length() == 0) {

                    outFileName = defaultFileName;

                } else {

                    String fileName = outFileName.substring(outFileName.lastIndexOf(File.separator) + 1);

                    outFileName = path + fileName;

                }

                InputStream unc = ld.getInputStream();

                OutputStream fOut = new BufferedOutputStream(new FileOutputStream(outFileName));

                Streams.pipeAll(unc, fOut);

                fOut.close();

            } else if (message instanceof PGPOnePassSignatureList) {

                throw new PGPException("encrypted message contains a signed message - not literal data.");

            } else {

                throw new PGPException("message is not a simple encrypted file - type unknown.");

            }

            if (pbe.isIntegrityProtected()) {

                if (!pbe.verify()) {

                    System.err.println("message failed integrity check");

                } else {

                    System.err.println("message integrity check passed");

                }

            } else {

                System.err.println("no message integrity check");

            }

        } catch (PGPException e) {

            System.err.println(e);

            if (e.getUnderlyingException() != null) {

                e.getUnderlyingException().printStackTrace();

            }

        }

    }

    /**
     * @param outputFileName     输出的加密文件名 2.pgp
     * @param inputFileName      输入的要加密的文件
     * @param encryKey           公钥
     * @param armor              true
     * @param withIntegrityCheck true
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws PGPException
     */
    public static void encryptFile(

            String outputFileName,

            String inputFileName,

            String encryKey,

            boolean armor,

            boolean withIntegrityCheck)

            throws IOException, NoSuchProviderException, PGPException {

        OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFileName));
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(encryKey);
        PGPPublicKey encKey = com.cathaypacific.csp.campaign.utils.PGPHandleUtil.readPublicKey(new ByteArrayInputStream(decode));

        encryptFile(out, inputFileName, encKey, armor, withIntegrityCheck);

        out.close();

    }

    private static void encryptFile(

            OutputStream out,

            String fileName,

            PGPPublicKey encKey,

            boolean armor,

            boolean withIntegrityCheck)

            throws IOException, NoSuchProviderException {

        if (armor) {

            out = new ArmoredOutputStream(out);

        }

        try {

            byte[] bytes = com.cathaypacific.csp.campaign.utils.PGPHandleUtil.compressFile(fileName, CompressionAlgorithmTags.ZIP);

            PGPEncryptedDataGenerator encGen = new PGPEncryptedDataGenerator(

                    new JcePGPDataEncryptorBuilder(PGPEncryptedData.CAST5).setWithIntegrityPacket(withIntegrityCheck)
                            .setSecureRandom(new SecureRandom()).setProvider("BC"));

            encGen.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(encKey).setProvider("BC"));

            OutputStream cOut = encGen.open(out, bytes.length);

            cOut.write(bytes);

            cOut.close();

            if (armor) {

                out.close();

            }

        } catch (PGPException e) {

            System.err.println(e);

            if (e.getUnderlyingException() != null) {

                e.getUnderlyingException().printStackTrace();

            }

        }

    }


    /**
     * 私有方法，用于生成指定位宽的PGP RSA密钥对
     *
     * @param rsaWidth_ RSA密钥位宽
     * @return 未经私钥加密的PGP密钥对
     * @throws Exception IO错误，数值错误等
     */
    private static PGPKeyPair generateKeyPair(int rsaWidth_) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");//获取密钥对生成器实例
        kpg.initialize(rsaWidth_);//设定RSA位宽
        KeyPair kp = kpg.generateKeyPair();//生成RSA密钥对
        return new JcaPGPKeyPair(PGPPublicKey.RSA_GENERAL, kp, new Date());//返回根据日期，密钥对生成的PGP密钥对
    }

    /**
     * 获取PGP密钥<br>
     * 密钥是将密钥对的私钥部分用对称的加密方法CAST-128算法加密，再加上公钥部分
     *
     * @param identity_   密钥ID也就是key值，可以用来标记密钥属于谁
     * @param passPhrase_ 密钥的密码，用来解出私钥
     * @param rsaWidth_   RSA位宽
     * @return PGP密钥
     * @throws Exception IO错误和数值错误等
     */
    public static PGPSecretKey getSecretKey(String identity_, String passPhrase_, int rsaWidth_) throws Exception {
        char[] passPhrase = passPhrase_.toCharArray(); //将passPharse转换成字符数组
        PGPKeyPair keyPair = PGPHandler.generateKeyPair(rsaWidth_); //生成RSA密钥对
        PGPDigestCalculator sha1Calc = new JcaPGPDigestCalculatorProviderBuilder().build().get(HashAlgorithmTags.SHA1); //使用SHA1作为证书的散列算法
        /**
         * 用证书等级生成的认证，将公私钥对和PGP ID密码绑定构造PGP密钥（SecretKey）
         *
         * @param certificationLevel PGP密钥的证书等级
         * @param keyPair 需要绑定的公私钥对
         * @param id 需要绑定的ID
         * @param checksumCalculator 散列值计算器，用于计算私钥密码散列
         * @param hashedPcks the hashed packets to be added to the certification.（先不管）
         * @param unhashedPcks the unhashed packets to be added to the certification.（也先不管）
         * @param certificationSignerBuilder PGP证书的生成器
         * @param keyEncryptor 如果需要加密私钥，需要在这里传入私钥加密器
         * @throws PGPException 一些PGP错误
         */
        return new PGPSecretKey(
                PGPSignature.DEFAULT_CERTIFICATION,
                keyPair,
                identity_,
                sha1Calc,
                null,
                null,
                new JcaPGPContentSignerBuilder(keyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1),
                //密钥的加密方式
                new JcePBESecretKeyEncryptorBuilder(PGPEncryptedData.CAST5, sha1Calc).setProvider("BC").build(passPhrase)
        );
    }


}
