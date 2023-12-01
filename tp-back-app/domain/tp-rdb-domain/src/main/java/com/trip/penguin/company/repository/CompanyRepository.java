package com.trip.penguin.company.repository;

import com.trip.penguin.company.domain.CompanyMS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyMS, Long> {

    Optional<CompanyMS> findByComEmail(String comEmail);

}
