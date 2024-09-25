package com.avocado.ecomus.entity;

import com.avocado.ecomus.entity.id.IdProductCategory;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "product_category")
public class ProductCategory {

    @EmbeddedId
    private IdProductCategory idProductCategory;

    @ManyToOne
    @MapsId("idCategory")
    @JoinColumn(name = "id_category", insertable = false, updatable = false)
    private CategoryEntity category;

    @ManyToOne
    @MapsId("idProduct")
    @JoinColumn(name = "id_product", insertable = false, updatable = false)
    private ProductEntity product;
}
