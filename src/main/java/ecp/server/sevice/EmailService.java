package ecp.server.sevice;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.SignatureException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final CipherService cipherService;

    @Value("${spring.mail.username}")
    private String username;

    public void sendEmailWithAttachment(String to, String subject, String filePath) throws MessagingException, IOException, SignatureException, InvalidKeyException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(username);
        helper.setTo(to);
        helper.setSubject(subject);
        Path path = Paths.get(filePath);
        byte[] attachment = Files.readAllBytes(path);
        System.out.println(attachment.length);
        ByteArrayResource resource = new ByteArrayResource(attachment);
        var digitalSignature = cipherService.cipherFile(attachment);
        helper.setText("Digital signature: " + digitalSignature);
        helper.addAttachment(path.getFileName().toString(), resource);

        emailSender.send(message);
    }
}
