package poomasi.domain.image.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poomasi.domain.image.dto.ImageRequest;
import poomasi.domain.image.entity.Image;
import poomasi.domain.image.entity.ImageType;
import poomasi.domain.image.service.ImageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageController {
    private final ImageService imageService;

    // 이미지 업로드
    @PostMapping
    public ResponseEntity<?> saveImageInfo(@RequestBody ImageRequest imageRequest) {
            Image savedImage = imageService.saveImage(imageRequest);
            return ResponseEntity.ok(savedImage);
    }

    // 여러 이미지 업로드
    @PostMapping("/multiple")
    public ResponseEntity<List<Image>> saveMultipleImages(@RequestBody List<ImageRequest> imageRequests) {
        List<Image> savedImages = imageService.saveMultipleImages(imageRequests);
        return ResponseEntity.ok(savedImages);
    }

    // 특정 이미지 삭제
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
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
    @PutMapping("/{id}")
    public ResponseEntity<?> updateImageInfo(@PathVariable Long id, @RequestBody ImageRequest imageRequest) {
        Image updatedImage = imageService.updateImage(id, imageRequest);
        return ResponseEntity.ok(updatedImage);
    }


}