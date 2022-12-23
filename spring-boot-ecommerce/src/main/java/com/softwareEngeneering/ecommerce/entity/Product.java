package com.softwareEngeneering.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    private String name;

    private String description;

    private BigDecimal unitPrice;

    private String imageUrl;

    private boolean active;

    private int unitsInStock;
    @CreationTimestamp
    private Date dateCreated;
    @CreationTimestamp
    private Date lastUpdated;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private ProductCategory category;


}
