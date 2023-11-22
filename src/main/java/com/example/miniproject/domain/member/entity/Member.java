package com.example.miniproject.domain.member.entity;

import com.example.miniproject.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String passWord;

    private Member(String email, String name, String passWord) {
        this.name = name;
        this.email = email;
        this.passWord = passWord;
    }

    public static Member create(String email, String name, String passWord
    ) {
        return new Member(email, name, passWord);
    }
}
