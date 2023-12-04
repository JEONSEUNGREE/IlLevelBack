package com.trip.penguin.account.converter;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.constant.OAuthVendor;
import com.trip.penguin.account.model.CompanyUser;
import com.trip.penguin.account.model.ProviderUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CompanyProviderConvert implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        if (providerUserRequest.companyMS() == null) {
            return null;
        }

        CompanyMS companyMS = providerUserRequest.companyMS();

        return new CompanyUser(
                companyMS.getCom_nm(),
                companyMS.getComPwd(),
                companyMS.getComEmail(),
                OAuthVendor.COMPANY.getOAuthVendor(),
                List.of(new SimpleGrantedAuthority(companyMS.getUserRole().getUserRole()))
        );
    }
    }
