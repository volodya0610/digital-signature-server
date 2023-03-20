package ecp.server.controller;

import ecp.server.dto.SendRequest;
import ecp.server.sevice.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.SignatureException;

@RestController
@RequiredArgsConstructor
public class SendMessageController {
    private final EmailService emailService;

    @PostMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendMessage(@RequestBody SendRequest request) throws MessagingException, IOException, SignatureException, InvalidKeyException {
        emailService.sendEmailWithAttachment(request.getTo(), request.getSubject(), request.getFilePath());
        return ResponseEntity.ok().build();
    }
}
