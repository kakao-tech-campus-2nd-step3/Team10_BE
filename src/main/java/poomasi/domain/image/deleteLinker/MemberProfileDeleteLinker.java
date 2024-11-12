package poomasi.domain.image.deleteLinker;

import org.springframework.stereotype.Component;
import poomasi.domain.image.entity.Image;
import poomasi.domain.member._profile.entity.MemberProfile;
import poomasi.domain.member._profile.service.MemberProfileService;

@Component
public class MemberProfileDeleteLinker implements ImageDeleteLinker {

    private final MemberProfileService memberProfileService;

    public MemberProfileDeleteLinker(MemberProfileService memberProfileService) {
        this.memberProfileService = memberProfileService;
    }

    @Override
    public void handleImageDeletion(Image image) {
        MemberProfile memberProfile = memberProfileService.getMemberProfileById(image.getReferenceId());
        memberProfile.setProfileImage(null);
        memberProfileService.saveMemberProfile(memberProfile);
    }
}

