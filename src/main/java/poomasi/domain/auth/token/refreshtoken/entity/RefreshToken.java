package poomasi.domain.auth.token.refreshtoken.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String tokenKey;

    @Column(nullable = false)
    private String data;

    @Column(nullable = false)
    private LocalDateTime expireAt;
}
