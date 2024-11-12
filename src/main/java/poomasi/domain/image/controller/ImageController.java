package poomasi.domain.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.auth.security.userdetail.UserDetailsImpl;
import poomasi.domain.image.dto.ImageRequest;
import poomasi.domain.image.entity.Image;
import poomasi.domain.image.entity.ImageType;
import poomasi.domain.image.service.ImageService;
import poomasi.domain.member.entity.Member;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {
    private final ImageService imageService;

    // 이미지 정보 저장
    @PostMapping
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER", "ROLE_ADMIN"})
    public ResponseEntity<?> saveImageInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ImageRequest imageRequest) {
        Member member = userDetails.getMember();
        Image savedImage = imageService.saveImage(member.getId(), imageRequest);
        return ResponseEntity.ok(savedImage);
    }

    // 여러 이미지 정보 저장
    @PostMapping("/multiple")
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER", "ROLE_ADMIN"})
    public ResponseEntity<List<Image>> saveMultipleImages(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody List<ImageRequest> imageRequests) {
        Member member = userDetails.getMember();
        List<Image> savedImages = imageService.saveMultipleImages(member.getId(), imageRequests);
        return ResponseEntity.ok(savedImages);
    }

    // 특정 이미지 삭제
    @DeleteMapping("/delete/{id}")
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER", "ROLE_ADMIN"})
    public ResponseEntity<Void> deleteImage(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        Member member = userDetails.getMember();
        imageService.deleteImage(member.getId(), id);
        return ResponseEntity.noContent().build();
    }

    // 특정 이미지 조회
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImage(@PathVariable Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    // 모든 이미지 조회 (특정 referenceId에 따라)
    @GetMapping("/reference/{type}/{referenceId}")
    public ResponseEntity<List<Image>> getImagesByTypeAndReference(@PathVariable ImageType type, @PathVariable Long referenceId) {
        List<Image> images = imageService.getImagesByTypeAndReferenceId(type, referenceId);
        return ResponseEntity.ok(images);
    }

    // 이미지 정보 수정
    @PutMapping("update/{id}")
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER", "ROLE_ADMIN"})
    public ResponseEntity<?> updateImageInfo(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @PathVariable Long id,
                                             @RequestBody ImageRequest imageRequest) {
        Member member = userDetails.getMember();
        Image updatedImage = imageService.updateImage(member.getId(), id, imageRequest);
        return ResponseEntity.ok(updatedImage);
    }

    @PutMapping("/recover/{id}")
    @Secured({"ROLE_CUSTOMER", "ROLE_FARMER", "ROLE_ADMIN"})
    public ResponseEntity<Void> recoverImage(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        Member member = userDetails.getMember();
        imageService.recoverImage(member.getId(), id);
        return ResponseEntity.noContent().build();
    }


}