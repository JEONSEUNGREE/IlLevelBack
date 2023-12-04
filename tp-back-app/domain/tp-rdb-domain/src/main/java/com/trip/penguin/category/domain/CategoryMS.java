package com.trip.penguin.category.domain;

import java.time.LocalDate;
import java.util.ArrayList;

import com.trip.penguin.category.dto.CategoryDepthDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CATEGORY_MS")
public class CategoryMS {

	public CategoryMS(Integer depth, CategoryMS parentCate, String cateNm) {
		this.depth = depth;
		this.parentCate = parentCate;
		this.cateNm = cateNm;
		this.createdDate = LocalDate.now();
		this.modifiedDate = LocalDate.now();
	}

	@Id
	@Column(name = "cate_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ancestor")
	private Long ancestor;

	@Column(name = "depth")
	private Integer depth;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private CategoryMS parentCate;

	@Column(name = "cate_nm", nullable = false)
	private String cateNm;

	@Column(name = "created_date", nullable = false)
	private LocalDate createdDate;

	@Column(name = "modified_date", nullable = false)
	private LocalDate modifiedDate;

	public void setAncestor() {
		if (parentCate == null) {
			this.ancestor = this.id;
			this.parentCate = this;
		} else {
			this.ancestor = this.parentCate.getAncestor();
		}
	}

	public CategoryDepthDTO changeDTO() {
		return new CategoryDepthDTO(
			this.getId(), this.getAncestor(), this.getParentCate().getId(), this.getCateNm(),
			this.getParentCate().getCateNm(), this.getDepth(), new ArrayList<>());
	}

}