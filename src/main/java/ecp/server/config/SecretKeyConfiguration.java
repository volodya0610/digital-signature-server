package ecp.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class SecretKeyConfiguration {
    // Генерация ключей
    @Bean
    public KeyPair keyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        var keys = keyPairGenerator.generateKeyPair();
        //Сохранить ключ
        saveKeys(keys.getPublic(), keys.getPrivate());
        return keys;
    }

    private void saveKeys(PublicKey publicKey, PrivateKey privateKey) {
        String keyFolder = "C:/Users/Volodya/ecp-server-api/src/main/resources/keys/";
        try {
            // Сохранить Public Key.
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                    publicKey.getEncoded());
            FileOutputStream fos = new FileOutputStream(keyFolder + "/public.key");
            fos.write(x509EncodedKeySpec.getEncoded());
            fos.close();

            // Сохранить Private Key.
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                    privateKey.getEncoded());
            fos = new FileOutputStream(keyFolder + "/private.key");
            fos.write(pkcs8EncodedKeySpec.getEncoded());
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Создание объекта для подписи сообщения
    @Bean
    public Signature signature() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA256withRSA");
    }

}
