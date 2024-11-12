package poomasi.domain.image.linker;

import org.springframework.stereotype.Service;
import poomasi.domain.image.entity.Image;
import poomasi.domain.image.entity.ImageType;
import poomasi.domain.member._profile.entity.MemberProfile;
import poomasi.domain.member._profile.service.MemberProfileService;

@Service
public class MemberProfileImageLinker implements ImageLinker {

    private final MemberProfileService memberProfileService;

    public MemberProfileImageLinker(MemberProfileService memberProfileService) {
        this.memberProfileService = memberProfileService;
    }

    @Override
    public boolean supports(ImageType type) {
        return type == ImageType.MEMBER_PROFILE;
    }

    @Override
    public void link(Long referenceId, Image savedImage) {
        MemberProfile memberProfile = memberProfileService.getMemberProfileById(referenceId);
        memberProfile.setProfileImage(savedImage);
        memberProfileService.saveMemberProfile(memberProfile);
    }
}


