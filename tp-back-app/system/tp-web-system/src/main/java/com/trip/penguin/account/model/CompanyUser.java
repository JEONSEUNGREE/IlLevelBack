package com.trip.penguin.account.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;


@Getter
@NoArgsConstructor
public class CompanyUser implements ProviderUser {

    public CompanyUser(String comName, String password, String comEmail, String provider, List<? extends GrantedAuthority> authorities) {
        this.comName = comName;
        this.password = password;
        this.comEmail = comEmail;
        this.provider = provider;
        this.authorities = authorities;
    }

    private String comName;

    private String password;

    private String comEmail;

    private String provider;

    private List<? extends GrantedAuthority> authorities;

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getUsername() {
        return comName;
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
        return comEmail;
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
