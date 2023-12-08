package com.example.miniproject.domain.member.entity;

import com.example.miniproject.global.constant.Role;
import com.example.miniproject.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    private Member(String email, String name, String password, Role role, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }

    public static Member create(String email, String name, String password, Role role, String phoneNumber) {
        return new Member(email, name, password, role, phoneNumber);
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
