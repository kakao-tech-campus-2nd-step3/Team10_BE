package poomasi.domain.auth.security.userdetail;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import poomasi.domain.member.entity.Member;
import poomasi.domain.member.repository.MemberRepository;
import poomasi.global.error.BusinessError;
import poomasi.global.error.BusinessException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new BusinessException(BusinessError.MEMBER_NOT_FOUND));
        return new UserDetailsImpl(member);
    }

}
