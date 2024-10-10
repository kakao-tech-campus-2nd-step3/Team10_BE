package poomasi.global.util;

import org.springframework.stereotype.Component;
import poomasi.global.error.ApplicationError;
import poomasi.global.error.ApplicationException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Base64;

@Component
public class EncryptionUtil {

    public String encodeTime(LocalDateTime time) {
        try {
            String timeString = time.toString();
            byte[] hash = MessageDigest.getInstance("SHA-256")
                    .digest(timeString.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw new ApplicationException(ApplicationError.ENCRYPT_ERROR);
        }
    }
}
