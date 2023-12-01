package com.trip.penguin.company.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.repository.CompanyRepository;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private CompanyRepository companyRepository;

	@Autowired
	public CompanyServiceImpl(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	/**
	 * 회사 등록
	 * @param companyMS - 회사 엔티티
	 * @return CompanyMS
	 */
	@Override
	public CompanyMS createCompany(CompanyMS companyMS) {
		companyMS.createCompany();
		return companyRepository.save(companyMS);
	}

	/**
	 * 회사 이메일로 조회
	 * @param comEmail
	 * @return CompanyMs
	 */
	@Override
	public Optional<CompanyMS> getCompanyByComEmail(String comEmail) {
		return companyRepository.findByComEmail(comEmail);
	}

	/**
	 * 회사 정보 조회
	 * @param companyMS - 회사 엔티티
	 * @return
	 */
	@Override
	public Optional<CompanyMS> getCompanyInfo(CompanyMS companyMS) {
		return companyRepository.findById(companyMS.getId());
	}

	/**
	 * 회사 정보 수정
	 * @param companyMS - 회사 엔티티
	 * @return
	 */
	@Override
	public CompanyMS updateCompanyInfo(CompanyMS companyMS) {
		companyMS.setCreatedDate(LocalDateTime.now());
		return companyRepository.save(companyMS);
	}

	/**
	 * 회사 정보 삭제
	 * @param companyMS - 회사 엔티티
	 */
	@Override
	public void deleteCompany(CompanyMS companyMS) {
		companyRepository.deleteById(companyMS.getId());
	}
}
