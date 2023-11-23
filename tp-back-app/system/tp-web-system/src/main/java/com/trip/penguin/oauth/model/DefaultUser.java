package com.trip.penguin.oauth.model;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DefaultUser implements ProviderUser {

    public DefaultUser(String username, String password, String userEmail, String provider, List<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.userEmail = userEmail;
        this.provider = provider;
        this.authorities = authorities;
    }


    private String username;

    private String password;

    private String userEmail;

    private String provider;

    private List<? extends GrantedAuthority> authorities;

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getEmail() {
        return userEmail;
    }

    @Override
    public String getProvider() {
        return provider;
    }

    @Override
    public String getPicture() {
        return null;
    }

    @Override
    public String provider() {
        return null;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public OAuth2User getOAuth2User() {
        return null;
    }
}