package com.trip.penguin.company.service;

import com.trip.penguin.company.domain.CompanyMS;

import java.util.Optional;

public interface CompanyService {

    public CompanyMS createCompany(CompanyMS companyMS);

    public Optional<CompanyMS> getCompanyInfo(CompanyMS companyMS);

    public CompanyMS updateCompanyInfo(CompanyMS companyMS);

    public void deleteCompany(CompanyMS companyMS);

}
