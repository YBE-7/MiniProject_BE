package com.example.miniproject.domain.member.service;

import com.example.miniproject.domain.member.dto.request.MemberLoginRequest;
import com.example.miniproject.domain.member.dto.request.MemberSignUpRequest;
import com.example.miniproject.domain.member.dto.response.MemberLoginResponse;
import com.example.miniproject.domain.member.dto.response.MemberMypageResponse;
import com.example.miniproject.domain.member.dto.response.MemberSignUpResponse;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.member.repository.MemberRepository;
import com.example.miniproject.global.exception.DuplicateEmailException;
import com.example.miniproject.global.security.JwtService;
import com.example.miniproject.global.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;

    @Override
    @Transactional
    public MemberSignUpResponse signUp(MemberSignUpRequest request) {
        validateDuplicateEmail(request.email());

        Member member = request.toEntity();
        member.encodePassword(passwordEncoder);

        return new MemberSignUpResponse(
            memberRepository.save(member)
        );
    }

    @Override
    @Transactional
    public MemberLoginResponse login(MemberLoginRequest request) {
        Authentication authentication = authenticationProvider.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        Member member = memberRepository.getReferenceById(memberDetails.getId());
        return new MemberLoginResponse(jwtService.issue(member.getId(), member.getEmail(), member.getRole()));
    }

    @Override
    public MemberMypageResponse getMypage(MemberDetails memberDetails) {
        Long memberId = memberDetails.getId();
        Member nowMember = memberRepository.getReferenceById(memberId);
        return new MemberMypageResponse(nowMember);

    }

    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
            .ifPresent(member -> {
                throw new DuplicateEmailException();
            });
    }
}
