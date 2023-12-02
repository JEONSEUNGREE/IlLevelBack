package com.trip.penguin.cate;

import com.trip.penguin.category.domain.CategoryMS;
import com.trip.penguin.category.dto.CategoryDepthListDTO;
import com.trip.penguin.category.repository.CateRepository;
import com.trip.penguin.category.service.CateService;
import com.trip.penguin.config.JpaDataConfig;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@JpaDataConfig
@DataJpaTest(properties = "classpath:application.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CateCRUDTest {

    private EntityManager em;

    private CateService cateService;

    private CateRepository cateRepository;


    @Autowired
    public CateCRUDTest(EntityManager em, CateService cateService,CateRepository cateRepository) {
        this.em = em;
        this.cateService = cateService;
        this.cateRepository = cateRepository;
    }

    @Test
    @DisplayName("카테 고리 서비스")
    void createRoomTest() {
        String[] parentList = new String[]{"기초 케어", "가방", "음식", "가전 제품"};
        String[] childList1 = new String[]{"기초 케어 1", "기초 케어 1.1", "기초 케어 1.2", "기초 케어 1.2"};
        String[] childList2 = new String[]{"기초 케어 2", "기초 케어 2.1", "기초 케어 2.2", "기초 케어 2.3"};
        String[] childList3 = new String[]{"기초 케어 3", "기초 케어 3.1", "기초 케어 3.2", "기초 케어 3.3"};

        for (String cate : parentList) {
            CategoryMS category = cateService.createCategory(
                    new CategoryMS(0, null, cate, LocalDate.now(), LocalDate.now())
            );
        }

        List<CategoryMS> all = cateRepository.findAll();

        for (String cate : childList1) {
            cateService.createCategory(
                    new CategoryMS(1, all.get(0), cate, LocalDate.now(), LocalDate.now())
            );
        }

        List<CategoryMS> all1 = cateRepository.findAll();

        for (String cate : childList2) {
            CategoryMS category = cateService.createCategory(
                    new CategoryMS(2, all1.get(5), cate, LocalDate.now(), LocalDate.now())
            );
        }

        List<CategoryMS> all2 = cateRepository.findAll();

        for (String cate : childList3) {
            CategoryMS category = cateService.createCategory(
                    new CategoryMS(3, all2.get(5), cate, LocalDate.now(), LocalDate.now())
            );
        }

        Map<Integer, List<CategoryDepthListDTO>> untilCurrentDepthCategory = cateService.getUntilCurrentDepthCategory(List.of(1L,2L), 3);

        // 하위 카테 고리 부터 위에 더한다.
        for (int i = untilCurrentDepthCategory.size() - 1; 0 < i; i--) {

            // 마지막 뎁스 카테 고리 리스트
            List<CategoryDepthListDTO> currentDepthCateList = untilCurrentDepthCategory.get(i);

            for (int j = 0; j < currentDepthCateList.size(); j++) {

                // 현재 카테 고리 부모 카테 고리 매핑
                CategoryDepthListDTO currentDepthCate = currentDepthCateList.get(j);

                // 바로 위에 부모 카테 고리 찾기
                List<CategoryDepthListDTO> parentCateList = untilCurrentDepthCategory.get(i - 1);

                // 부모 카테 고리와 아이디 비교
                for (int k = 0; k < parentCateList.size(); k++) {
                    if (currentDepthCate.getParentId().equals(parentCateList.get(k).getParentId())) {
                        parentCateList.get(k).addChildCate(currentDepthCate);
                        break;
                    }
                }
            }
        }

        List<CategoryDepthListDTO> categoryDepthListDTOS = untilCurrentDepthCategory.get(0);


        List<CategoryDepthListDTO> currentParentIdCate = cateService.getCurrentParentIdCate(1L, 2, 6L);

        List<CategoryDepthListDTO> allCategory = cateService.getAllCategory();
        System.out.println(allCategory);
        System.out.println(currentParentIdCate);

        CategoryDepthListDTO untilCurrentDepthCategory1 = cateService.getUntilCurrentFlatOneCategory(1L, 3, 12L);



//
//        List<CategoryDepthListDTO> test = new ArrayList<>();
//
////        untilCurrentCategory4.stream().collect(groupingBy(cd -> cd.get))
//        untilCurrentCategory4.stream().forEach(cate ->
//        {
//            untilCurrentCategory4.stream().forEach(parent -> {
//                if (parent.getId().equals(cate.getParentCate().getId())) {
//                    parent.changeDTO().addChildCate(cate.changeDTO());
//                    untilCurrentCategory4.pop();
//                }
//            });
//        });

//        untilCurrentCategory4.



//        for (int i = 0; i < untilCurrentCategory4.size(); i++) {
//            categoryDepthListDTO.addChildCate(untilCurrentCategory4.get(i).changeDTO());
//        }

    }
}
