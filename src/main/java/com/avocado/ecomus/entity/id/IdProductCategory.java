package com.avocado.ecomus.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class IdProductCategory implements Serializable {

    private int idCategory;

    private int idProduct;
}
