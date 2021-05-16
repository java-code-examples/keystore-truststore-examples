package com.marcuschiu.example.keystoretruststoreexamples;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class Main {

    // Based on: https://www.baeldung.com/java-keystore
    public static void main(String[] args) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
        var keystoreFileName = "newKeyStoreFileName.jks";
        var password = "password";

        createKeystore(keystoreFileName, password);
        KeyStore ks = loadKeystore(keystoreFileName, password);

//        // Store Keys
//        CrudKeyEntryExamples.storeSymmetricKey(ks);
//        CrudKeyEntryExamples.storePrivateKey(ks);
//        CrudKeyEntryExamples.storeTrustedCertificate(ks);
//
//        // Read Keys
//        CrudKeyEntryExamples.readSingleKeyEntry(ks);
//
//        // Other
//        CrudKeyEntryExamples.other(ks);
//
//        // Delete Keys
//        CrudKeyEntryExamples.deleteKeyEntry(ks);
    }

    private static void createKeystore(String keystoreFileName, String password) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        // 1 - Create Keystore Object
        var ks = KeyStore.getInstance(KeyStore.getDefaultType());
//        var ks = KeyStore.getInstance("JKS");
//        var ks = KeyStore.getInstance("pcks12");
        ks.load(null, password.toCharArray());
        // stream = null : tells KeyStore to create a new one
        // password = pwdArray : which will be used for accessing the keystore in the future

        // 3 - Save Keystore Object to File
        try (var fos = new FileOutputStream(keystoreFileName)) {
            ks.store(fos, password.toCharArray());
        }
    }

    private static KeyStore loadKeystore(String keystoreFileName, String password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        // 1 - Load Existing Keystore From File
        var ks = KeyStore.getInstance(KeyStore.getDefaultType());
//        var ks = KeyStore.getInstance("JKS");
//        var ks = KeyStore.getInstance("pcks12");
        ks.load(new FileInputStream(keystoreFileName), password.toCharArray());
        // stream = null : tells KeyStore to load an existing keystore
        // password = pwdArray : which will be used for accessing the keystore in the future

        return ks;
    }
}
