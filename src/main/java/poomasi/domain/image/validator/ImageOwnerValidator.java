package poomasi.domain.image.validator;

public interface ImageOwnerValidator {
    boolean validateOwner(Long memberId, Long referenceId);
}