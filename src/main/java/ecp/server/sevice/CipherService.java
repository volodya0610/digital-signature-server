package ecp.server.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CipherService {
    public final KeyPair keyPair;
    public final Signature signature;

    public String cipherFile(byte[] file) throws SignatureException, InvalidKeyException {
        signature.initSign(keyPair.getPrivate());
        // Обновление объекта для подписи сообщения данными
        signature.update(file);
        // Генерация подписи
        byte[] digitalSignature = signature.sign();
        String base64Signature = Base64.getEncoder().encodeToString(digitalSignature);
        System.out.println("Digital Signature: " + base64Signature);


        return base64Signature;
    }

    public String verifyFile(String filePath, String clientSignature) throws SignatureException, InvalidKeyException, IOException {
        try {

            Path path = Paths.get(filePath);
            byte[] file = Files.readAllBytes(path);
            byte[] digitalSignature = Base64.getDecoder().decode(clientSignature);
            signature.initSign(keyPair.getPrivate());
            // Проверка подписи
            signature.initVerify(keyPair.getPublic());
            signature.update(file);
            boolean verified = signature.verify(digitalSignature);
            return "Signature Verified: " + verified;
        } catch (Exception e) {
            return "Signature Verified: " + false;
        }
    }



}
