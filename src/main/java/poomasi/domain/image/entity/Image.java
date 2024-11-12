package poomasi.domain.image.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import poomasi.domain.image.dto.ImageRequest;

import java.time.LocalDateTime;

@Entity
@Table(name = "image", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"type", "reference_id"})
})
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE image SET deleted_at = current_timestamp WHERE id = ?")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String objectKey;

    @Column(nullable = false, unique = true)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImageType type;

    @Column(name = "reference_id", nullable = false)
    private Long referenceId;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Image(String objectKey, String imageUrl, ImageType type, Long referenceId) {
        this.objectKey = objectKey;
        this.imageUrl = imageUrl;
        this.type = type;
        this.referenceId = referenceId;
    }

    public void update(ImageRequest request) {
        this.objectKey = request.objectKey();
        this.imageUrl = request.imageUrl();
        this.type = request.type();
        this.referenceId = request.referenceId();
    }

    public ImageRequest toRequest(Image image){
        return new ImageRequest(
                image.objectKey,
                image.imageUrl,
                image.type,
                image.referenceId
        );
    }
}