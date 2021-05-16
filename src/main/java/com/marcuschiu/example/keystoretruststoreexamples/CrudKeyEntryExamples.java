package com.marcuschiu.example.keystoretruststoreexamples;

import javax.crypto.SecretKey;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

// In the keystore, we can store/read three different kinds of entries, each entry under its alias:
// - Symmetric Keys (referred to as Secret Keys in the JCE)
// - Asymmetric Keys (referred to as Public and Private Keys in the JCE)
// - Trusted Certificates
public class CrudKeyEntryExamples {

    public static void storeSymmetricKey(KeyStore ks) throws KeyStoreException {
        // To save a symmetric key, we'll need three things:
        // - an alias – his is simply the name that we'll use in the future to refer to the entry
        // - a key – which is wrapped in a KeyStore.SecretKeyEntry.
        // - a password – which is wrapped in what is called a ProtectionParam
        SecretKey secretKey = null;
        String passwordString = "password"; // cannot be null but can be empty/blank
        String alias = "db-encryption-secret";
        KeyStore.SecretKeyEntry secret = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter password = new KeyStore.PasswordProtection(passwordString.toCharArray());
        ks.setEntry(alias, secret, password);
    }

    public static void storePrivateKey(KeyStore ks) throws KeyStoreException {
        // to save an asymmetric key, we'll need four things:
        // - an alias, same as before
        // - a private key. Because we aren't using the generic method, the key won't get wrapped. Also, for our case, it should be an instance of PrivateKey
        // - a password for accessing the entry. This time, the password is mandatory
        // - a certificate chain that certifies the corresponding public key
        PrivateKey privateKey = null;
        String alias = "sso-signing-key";
        String password = "password";
        X509Certificate[] certificateChain = new X509Certificate[2];
        certificateChain[0] = null; // clientCert;
        certificateChain[1] = null; // caCert;
        ks.setKeyEntry(alias, privateKey, password.toCharArray(), certificateChain);
    }

    public static void storeTrustedCertificate(KeyStore ks) throws KeyStoreException {
        // Storing trusted certificates is quite simple. It only requires the alias and the certificate itself
        Certificate trustedCertificate = null;
        ks.setCertificateEntry("google.com", trustedCertificate);
    }

    public static void readSingleKeyEntry(KeyStore ks) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        String password = "password";
        SecretKey secretKey = (SecretKey) ks.getKey("db-encryption-secret", password.toCharArray());
        Key ssoSigningKey = ks.getKey("sso-signing-key", password.toCharArray());
        Certificate google = ks.getCertificate("google.com");
    }

    public static void other(KeyStore ks) throws KeyStoreException {
        int numKeyEntries = ks.size();
        ks.containsAlias("widget-api-secret");
        ks.entryInstanceOf("widget-api-secret", KeyStore.PrivateKeyEntry.class);
    }

    public static void deleteKeyEntry(KeyStore ks) throws KeyStoreException {
        ks.deleteEntry("widget-api-secret");
        ks.deleteEntry("some-other-api-secret");
    }
}
