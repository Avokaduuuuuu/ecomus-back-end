package com.avocado.ecomus.entity;

import com.avocado.ecomus.entity.id.IdProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "sale")
    private double sale = 0;

    @Column(name = "available")
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "id_brand")
    private BrandEntity brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductCategory> productCategories;

    @OneToMany(mappedBy = "product")
    List<VariantEntity> variants;

    public void addCategory(CategoryEntity category) {
        if (this.productCategories == null) productCategories = new ArrayList<>();
        ProductCategory productCategory = new ProductCategory();
        IdProductCategory idProductCategory = new IdProductCategory();
        idProductCategory.setIdProduct(this.id);
        idProductCategory.setIdCategory(category.getId());
        productCategory.setIdProductCategory(idProductCategory);
        productCategory.setCategory(category);
        productCategory.setProduct(this);
        productCategories.add(productCategory);
    }
}
