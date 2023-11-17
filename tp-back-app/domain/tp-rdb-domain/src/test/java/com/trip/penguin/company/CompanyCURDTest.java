package com.trip.penguin.company;


import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.company.service.CompanyService;
import com.trip.penguin.constant.CommonConstant;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("local")
@DataJpaTest(properties = "classpath:application.yaml")
@ComponentScan(value = "com.trip.penguin")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CompanyCURDTest {

    private CompanyService companyService;

    private EntityManager em;

    private CompanyMS beforeCommitCompany;

    @Autowired
    public CompanyCURDTest(CompanyService companyService, EntityManager em) {
        this.companyService = companyService;
        this.em = em;
    }

    @BeforeEach
    public void beforeTest() {
        /* 회사 가입 */
        beforeCommitCompany = CompanyMS.builder()
                .com_nm("testNm")
                .comEmail("test@test.com")
                .comPwd("testPwd")
                .comImg("defaultImg")
                .comAddress("location")
                .comApproval(CommonConstant.N.name())
                .userRole("ROLE_COM")
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("회사 등록 테스트")
    void createCompanyTest() {

        // given
        /* 회사 가입 */
        CompanyMS afterCommitCompany = companyService.createCompany(beforeCommitCompany);

        em.flush();
        em.clear();

        // when
        /* 회사 조회 */
        Optional<CompanyMS> findCompany = companyService.getCompanyInfo(afterCommitCompany);

        // then
        /* 회사 검증 */
        assertEquals(findCompany.orElseThrow(IllegalAccessError::new).getId(), afterCommitCompany.getId());
    }


    @Test
    @DisplayName("회사 수정 테스트")
    void updateCompanyTest() {

        // given
        /* 회사 가입 */
        CompanyMS afterCommitCompany = companyService.createCompany(beforeCommitCompany);

        em.flush();
        em.clear();

        // when
        /* 회사 정보 조회, 수정 */
        CompanyMS findCompany = companyService.getCompanyInfo(afterCommitCompany).orElseThrow(IllegalAccessError::new);
        findCompany.setCom_nm("change");

        CompanyMS afterUpdateCompany = companyService.updateCompanyInfo(findCompany);

        em.flush();
        em.clear();

        // then
        /* 회사 검증 */
        assertEquals(afterUpdateCompany.getId(), afterCommitCompany.getId());
        assertNotEquals(afterUpdateCompany.getCom_nm(), afterCommitCompany.getCom_nm());
    }

    @Test
    @DisplayName("회사 삭제 테스트")
    void deleteCompanyTest() {

        // given
        /* 회사 가입 */
        CompanyMS afterCommitCompany = companyService.createCompany(beforeCommitCompany);

        em.flush();
        em.clear();

        // when
        /* 회사 정보 조회, 수정 */
        companyService.deleteCompany(afterCommitCompany);

        em.flush();
        em.clear();

        // then
        /* 회사 검증 */
        assertFalse(companyService.getCompanyInfo(afterCommitCompany).isPresent());
    }
}
