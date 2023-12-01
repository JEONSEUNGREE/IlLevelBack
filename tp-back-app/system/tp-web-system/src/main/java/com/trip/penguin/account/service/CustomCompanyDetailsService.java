package com.trip.penguin.account.service;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.exception.NotCompanyUserException;
import com.trip.penguin.account.converter.ProviderUserRequest;
import com.trip.penguin.account.model.PrincipalUser;
import com.trip.penguin.account.model.ProviderUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomCompanyDetailsService extends AbstractOAuth2UserService{

    private final CompanyService companyService;

    public UserDetails loadUserByCompanyName(String comEmail) throws UsernameNotFoundException {

        CompanyMS companyMS = companyService.getCompanyByComEmail(comEmail).orElseThrow(NotCompanyUserException::new);

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(companyMS);

        ProviderUser providerUser = providerUser(providerUserRequest);

        return new PrincipalUser(providerUser);
    }
}
