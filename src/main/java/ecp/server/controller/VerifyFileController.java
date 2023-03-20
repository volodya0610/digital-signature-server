package ecp.server.controller;

import ecp.server.dto.VerifyRequest;
import ecp.server.sevice.CipherService;
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
public class VerifyFileController {
    private final CipherService cipherService;

    @PostMapping(path = "/verify", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMessage(@RequestBody VerifyRequest request) throws MessagingException, IOException, SignatureException, InvalidKeyException {
        var result = cipherService.verifyFile(request.getFilePath(), request.getSignature());
        return ResponseEntity.ok(result);
    }
}
