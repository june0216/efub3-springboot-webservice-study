package com.jojoldu.book.springboot.config.auth.dto;

import com.jojoldu.book.springboot.domain.user.Role;
import com.jojoldu.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributesTest {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributesTest(Map<String,Object> attributes,
                               String nameAttributeKey, String name,
                               String email, String picture){
        this.attributes=attributes;
        this.nameAttributeKey=nameAttributeKey;
        this.name=name;
        this.email=email;
        this.picture=picture;
    }

    public static OAuthAttributesTest of(String registrationId, String userNameAtributeName, Map<String,Object> attributes){
        return ofGoogle(userNameAtributeName,attributes);
    }
    private static OAuthAttributesTest ofGoogle(String userNameAttributeName, Map<String,Object> attributes){
        return OAuthAttributesTest.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
