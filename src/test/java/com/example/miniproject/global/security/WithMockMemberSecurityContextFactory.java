package com.example.miniproject.global.security;

import java.util.Arrays;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockMemberSecurityContextFactory implements WithSecurityContextFactory<WithMockMember> {

    @Override
    public SecurityContext createSecurityContext(WithMockMember annotation) {
        MemberDetails memberDetails = MemberDetails.builder()
            .id(annotation.id())
            .email("test@email.com")
            .authorities(Arrays.stream(annotation.authorities())
                .map(SimpleGrantedAuthority::new)
                .toList())
            .build();
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
            new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities())
        );
        return securityContext;
    }
}
