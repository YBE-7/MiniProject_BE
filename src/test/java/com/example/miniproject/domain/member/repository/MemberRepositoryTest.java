package com.example.miniproject.domain.member.repository;

import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.global.constant.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @DisplayName("이메일로 멤버 찾기 테스트")
    @Test
    @Transactional
    public void findByEmail() {
        //given
        Member member = Member.create("hyem5019@email.com", "hyemin", "qwerasqweras!1", Role.MEMBER);
        Member member1 = memberRepository.save(member);
        //when
        Optional<Member> userEmail = memberRepository.findByEmail("hyem5019@email.com");
        //then
        Assertions.assertThat(member.getEmail()).isEqualTo(userEmail.get().getEmail());
        System.out.println(member.getEmail());
        System.out.println(userEmail.get());
    }


}