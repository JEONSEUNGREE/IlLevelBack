package com.trip.penguin.cate;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.trip.penguin.category.domain.CategoryMS;
import com.trip.penguin.category.dto.CategoryDepthDTO;
import com.trip.penguin.category.repository.CateRepository;
import com.trip.penguin.category.service.CateService;
import com.trip.penguin.config.JpaDataConfig;

import jakarta.persistence.EntityManager;

@JpaDataConfig
@DataJpaTest(properties = "classpath:application.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CateCRUDTest {

	private EntityManager em;

	private CateService cateService;

	private CateRepository cateRepository;

	@Autowired
	public CateCRUDTest(EntityManager em, CateService cateService, CateRepository cateRepository) {
		this.em = em;
		this.cateService = cateService;
		this.cateRepository = cateRepository;
	}

	@BeforeEach
	public void beforeTest() {
		String[] parentList = new String[] {"기초 케어", "가방", "음식", "가전 제품"};
		String[] childList1 = new String[] {"화장품", "선케어", "클렌징", "남성용", "여성용"};
		String[] childList2 = new String[] {"스킨/토너", "로션", "크림", "에센스/페이스오일"};
		String[] childList3 = new String[] {"립케어", "아이케어", "팩 마스크", "미스트"};

		String[] childList4 = new String[] {"파우더", "스틱", "세트 상품", "단일 상품"};
		String[] childList5 = new String[] {"신발", "지갑", "핸드백", "벨트"};
		String[] childList6 = new String[] {"구두", "샌들", "운동화", "스니커즈/캐주얼"};
		String[] childList7 = new String[] {"정장 벨트", "캐주얼 벨트"};

		for (String cate : parentList) {
			cateService.createCategory(
				new CategoryMS(0, null, cate)
			);
		}

		CategoryMS rootCate = cateService.getCategoryDtoByCategoryNm("기초 케어");

		for (String cate : childList1) {
			cateService.createCategory(
				new CategoryMS(1, rootCate, cate)
			);
		}

		CategoryMS cosmetic = cateService.getCategoryDtoByCategoryNm("화장품");

		for (String cate : childList2) {
			cateService.createCategory(
				new CategoryMS(2, cosmetic, cate)
			);
		}

		CategoryMS skinToner = cateService.getCategoryDtoByCategoryNm("스킨/토너");

		CategoryMS lotion = cateService.getCategoryDtoByCategoryNm("로션");

		CategoryMS bag = cateService.getCategoryDtoByCategoryNm("가방");

		for (String cate : childList3) {
			cateService.createCategory(
				new CategoryMS(3, skinToner, cate)
			);
		}

		for (String cate : childList4) {
			cateService.createCategory(
				new CategoryMS(3, lotion, cate)
			);
		}

		for (String cate : childList5) {
			cateService.createCategory(
				new CategoryMS(1, bag, cate)
			);
		}

		CategoryMS belt = cateService.getCategoryDtoByCategoryNm("벨트");

		for (String cate : childList6) {
			cateService.createCategory(
				new CategoryMS(1, bag, cate)
			);
		}

		for (String cate : childList7) {
			cateService.createCategory(
				new CategoryMS(1, belt, cate)
			);
		}
	}

	@Test
	@DisplayName("카테 고리 전체 상위 -> 하위 계층 구조로 조회")
	void getAllCateList() {

		List<CategoryDepthDTO> allCategory = cateService.getAllCateList();
		System.out.println(allCategory);

		// 최상위 카테 고리 수
		assertEquals(allCategory.size(), 4);
		// 최상위 1뎁스 카테고리 수
		assertEquals(allCategory.get(0).getCateName(), "기초 케어");
		assertEquals(allCategory.get(0).getChildCateList().size(), 5);

		assertEquals(allCategory.get(0).getChildCateList().get(0).getCateName(), "화장품");
		assertEquals(allCategory.get(0).getChildCateList().get(0).getChildCateList().size(), 4);

		assertEquals(allCategory.get(0).getChildCateList().get(0).getChildCateList().get(0).getCateName(), "스킨/토너");
		assertEquals(allCategory.get(0).getChildCateList().get(0).getChildCateList().get(0).getChildCateList().size(),
			4);
	}

	@Test
	@DisplayName("카테고리 뎁스 Key, 같은 뎁스로 그룹지은 카테고리가 value로 조회")
	void getUntilCurrentDepthCateKeyMap() {

		Integer specificDepth = 3;

		CategoryMS firstCate = cateService.getCategoryDtoByCategoryNm("기초 케어");
		CategoryMS secondCate = cateService.getCategoryDtoByCategoryNm("가방");

		Map<Integer, List<CategoryDepthDTO>> untilCurrentDepthCategory = cateService.getUntilCurrentDepthCateKeyMap(
			List.of(firstCate.getAncestor(), secondCate.getAncestor()), specificDepth);

		// 최상위 포함 하여 4 뎁스
		assertEquals(specificDepth + 1, untilCurrentDepthCategory.size());
		assertEquals(untilCurrentDepthCategory.get(0).get(0).getCateName(), "기초 케어");
		assertEquals(untilCurrentDepthCategory.get(1).get(0).getCateName(), "화장품");
		assertEquals(untilCurrentDepthCategory.get(2).get(0).getCateName(), "스킨/토너");
		assertEquals(untilCurrentDepthCategory.get(3).get(0).getCateName(), "립케어");
	}

	@Test
	@DisplayName("지정한 최상위 카테고리들만 상위 -> 하위로 계층 구조로 조회")
	void getUntilCurrentDepthCateList() {

		Integer specificDepth = 3;

		CategoryMS firstCate = cateService.getCategoryDtoByCategoryNm("기초 케어");
		CategoryMS secondCate = cateService.getCategoryDtoByCategoryNm("가방");

		List<CategoryDepthDTO> untilCurrentDepthCategoryChildMap = cateService.getUntilCurrentDepthCateList(
			List.of(firstCate.getAncestor(), secondCate.getAncestor()), specificDepth);

		// 최상위 카테 고리 수 -> 특정 최상위 카테고리만 조회
		assertEquals(untilCurrentDepthCategoryChildMap.size(), 2);
		// 최상위 1뎁스 카테고리 수
		assertEquals(untilCurrentDepthCategoryChildMap.get(0).getCateName(), "기초 케어");
		assertEquals(untilCurrentDepthCategoryChildMap.get(0).getChildCateList().size(), 5);

		assertEquals(untilCurrentDepthCategoryChildMap.get(0).getChildCateList().get(0).getCateName(), "화장품");
		assertEquals(untilCurrentDepthCategoryChildMap.get(0).getChildCateList().get(0).getChildCateList().size(), 4);
		assertEquals(
			untilCurrentDepthCategoryChildMap.get(0).getChildCateList().get(0).getChildCateList().get(0).getCateName(),
			"스킨/토너");
		assertEquals(untilCurrentDepthCategoryChildMap.get(0)
				.getChildCateList()
				.get(0)
				.getChildCateList()
				.get(0)
				.getChildCateList()
				.size(),
			4);
	}

	@Test
	@DisplayName("현재 카테고리의 하위 카테고리들 조회")
	void getCurrentDepthChildCateList() {

		CategoryMS cosmetic = cateService.getCategoryDtoByCategoryNm("화장품");

		List<CategoryDepthDTO> currentParentIdCate = cateService.getCurrentDepthChildCateList(
			cosmetic.getDepth(), cosmetic.getId());

		assertEquals(currentParentIdCate.size(), 4);
	}

	@Test
	@DisplayName("현재 카테고리 에서 최상위 카테고리 까지 조회 (해당 되는 부모 카테고리만)")
	void getUntilCurrentFlatCate() {

		CategoryMS skinToner = cateService.getCategoryDtoByCategoryNm("스킨/토너");

		CategoryDepthDTO untilCurrentFlatOneCategory = cateService.getUntilCurrentDepthFlatCate(
			skinToner.getAncestor(), skinToner.getDepth(), skinToner.getId());

		assertEquals(untilCurrentFlatOneCategory.getCateName(), "기초 케어");
		assertEquals(untilCurrentFlatOneCategory.getChildCateList().get(0).getCateName(), "화장품");
		assertEquals(untilCurrentFlatOneCategory.getChildCateList().size(), 1);
		assertEquals(untilCurrentFlatOneCategory.getChildCateList().get(0).getChildCateList().get(0).getCateName(),
			"스킨/토너");
		assertEquals(untilCurrentFlatOneCategory.getChildCateList().get(0).getChildCateList().size(), 1);
	}

}
