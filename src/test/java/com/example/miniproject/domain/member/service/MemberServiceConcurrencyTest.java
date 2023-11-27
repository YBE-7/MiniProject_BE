package com.example.miniproject.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.miniproject.domain.member.dto.request.MemberSignUpRequest;
import com.example.miniproject.domain.member.entity.Member;
import com.example.miniproject.domain.member.repository.MemberRepository;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceConcurrencyTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    public void concurrent_signup() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    memberService.signUp(
                        new MemberSignUpRequest(
                            "email@email.com",
                            "password123!",
                            "마이콜"
                        )
                    );
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(1);
    }
}