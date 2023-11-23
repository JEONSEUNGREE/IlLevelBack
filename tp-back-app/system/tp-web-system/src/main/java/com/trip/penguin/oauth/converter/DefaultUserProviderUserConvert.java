package com.trip.penguin.oauth.converter;

import com.trip.penguin.constant.OAuthVendor;
import com.trip.penguin.oauth.model.DefaultUser;
import com.trip.penguin.oauth.model.GoogleUser;
import com.trip.penguin.oauth.model.ProviderUser;
import com.trip.penguin.oauth.util.OAuth2Utils;
import com.trip.penguin.user.domain.UserMS;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class DefaultUserProviderUserConvert implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        if (providerUserRequest.userMS() == null) {
            return null;
        }
        UserMS userMS = providerUserRequest.userMS();

        return new DefaultUser(
                userMS.getUserFirst(),
                userMS.getUserPwd(),
                userMS.getUserEmail(),
                userMS.getSocialProvider(),
                List.of(new SimpleGrantedAuthority(userMS.getUserRole()))
        );
    }
}
