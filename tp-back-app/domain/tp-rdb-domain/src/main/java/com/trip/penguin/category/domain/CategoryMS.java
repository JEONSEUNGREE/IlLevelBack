package com.trip.penguin.category.domain;

import com.trip.penguin.category.dto.CategoryDepthListDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CATEGORY_MS")
public class CategoryMS {

    public CategoryMS(Integer depth, CategoryMS parentCate, String cateNm, LocalDate createdDate, LocalDate modifiedDate) {
        this.depth = depth;
        this.parentCate = parentCate;
        this.cateNm = cateNm;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
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

    public CategoryDepthListDTO changeDTO() {
        return new CategoryDepthListDTO(
                this.getId(), this.getAncestor(), this.getParentCate().getId(), this.getCateNm(),
                this.getParentCate().getCateNm(), this.getDepth(), new ArrayList<>());
    }

}