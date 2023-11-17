package com.trip.penguin.company.repository;

import com.trip.penguin.company.domain.CompanyMS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyMS, Long> {

}
