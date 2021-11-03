package com.example.demo.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.bc.BcPBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.bc.BcPGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.bc.BcPublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyPair;

import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;

public class PGPHandleUtil {

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


    public static byte[] compressFile(String var0, int var1) throws IOException {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        PGPCompressedDataGenerator var3 = new PGPCompressedDataGenerator(var1);
        PGPUtil.writeFileToLiteralData(var3.open(var2), 'b', new File(var0));
        var3.close();
        return var2.toByteArray();
    }

    public static PGPPublicKey readPublicKey(InputStream var0) throws IOException, PGPException {
        PGPPublicKeyRingCollection var1 = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(var0), new JcaKeyFingerprintCalculator());
        Iterator var2 = var1.getKeyRings();
        while (var2.hasNext()) {
            PGPPublicKeyRing var3 = (PGPPublicKeyRing) var2.next();
            Iterator var4 = var3.getPublicKeys();
            while (var4.hasNext()) {
                PGPPublicKey var5 = (PGPPublicKey) var4.next();
                if (var5.isEncryptionKey()) {
                    return var5;
                }
            }
        }
        throw new IllegalArgumentException("Can't find encryption key in key ring.");
    }

    public static PGPSecretKey readSecretKey(InputStream var0) throws IOException, PGPException {
        PGPSecretKeyRingCollection var1 = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(var0), new JcaKeyFingerprintCalculator());
        Iterator var2 = var1.getKeyRings();
        while (var2.hasNext()) {
            PGPSecretKeyRing var3 = (PGPSecretKeyRing) var2.next();
            Iterator var4 = var3.getSecretKeys();
            while (var4.hasNext()) {
                PGPSecretKey var5 = (PGPSecretKey) var4.next();
                if (var5.isSigningKey()) {
                    return var5;
                }
            }
        }
        throw new IllegalArgumentException("Can't find signing key in key ring.");
    }

    public static PGPPrivateKey findPrivateKey(PGPSecretKeyRingCollection pgpSec, long keyID, char[] pass)
            throws PGPException {
        return findPrivateKey(pgpSec.getSecretKey(keyID), pass);
    }

    public static PGPPrivateKey findPrivateKey(InputStream keyIn, long keyID, char[] pass)
            throws IOException, PGPException {
        PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(keyIn), new JcaKeyFingerprintCalculator());
        return findPrivateKey(pgpSec.getSecretKey(keyID), pass);
    }
    /**
     * 169
     * Load a secret key and find the private key in it
     * 170
     *
     * @param pgpSecKey The secret key
     * @param pass      passphrase to decrypt secret key with
     * @throws PGPException
     */
    public static PGPPrivateKey findPrivateKey(PGPSecretKey pgpSecKey, char[] pass)
            throws PGPException {
        if (pgpSecKey == null) return null;
        PBESecretKeyDecryptor decryptor = new BcPBESecretKeyDecryptorBuilder(new BcPGPDigestCalculatorProvider()).build(pass);
        return pgpSecKey.extractPrivateKey(decryptor);
    }

    @SuppressWarnings("unchecked")
    public static void decryptFile(InputStream in, OutputStream out, InputStream keyIn, char[] password)
            throws IOException, PGPException {
        Security.addProvider(new BouncyCastleProvider());
        in = org.bouncycastle.openpgp.PGPUtil.getDecoderStream(in);
        PGPObjectFactory pgpF = new PGPObjectFactory(in, new JcaKeyFingerprintCalculator());
        PGPEncryptedDataList enc;
        Object o = pgpF.nextObject();
        // the first object might be a PGP marker packet.
        if (o instanceof PGPEncryptedDataList) {
            enc = (PGPEncryptedDataList) o;
        } else {
            enc = (PGPEncryptedDataList) pgpF.nextObject();
        }
        Iterator<PGPPublicKeyEncryptedData> it = enc.getEncryptedDataObjects();
        PGPPrivateKey sKey = null;
        PGPPublicKeyEncryptedData pbe = null;
        while (sKey == null && it.hasNext()) {
            pbe = it.next();
            sKey = findPrivateKey(keyIn, pbe.getKeyID(), password);
        }
        if (sKey == null) {
            throw new IllegalArgumentException("Secret key for message not found.");
        }
        InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));
        PGPObjectFactory plainFact = new PGPObjectFactory(clear, new JcaKeyFingerprintCalculator());
        Object message = plainFact.nextObject();
        if (message instanceof PGPCompressedData) {
            PGPCompressedData cData = (PGPCompressedData) message;
            PGPObjectFactory pgpFact = new PGPObjectFactory(cData.getDataStream(), new JcaKeyFingerprintCalculator());
            message = pgpFact.nextObject();
        }
        if (message instanceof PGPLiteralData) {
            PGPLiteralData ld = (PGPLiteralData) message;
            InputStream unc = ld.getInputStream();
            int ch;
            while ((ch = unc.read()) >= 0) {
                out.write(ch);
            }
        } else if (message instanceof PGPOnePassSignatureList) {
            throw new PGPException("Encrypted message contains a signed message - not literal data.");
        } else {
            throw new PGPException("Message is not a simple encrypted file - type unknown.");
        }
        if (pbe.isIntegrityProtected()) {
            if (!pbe.verify()) {
                throw new PGPException("Message failed integrity check");
            }
        }
    }
}
