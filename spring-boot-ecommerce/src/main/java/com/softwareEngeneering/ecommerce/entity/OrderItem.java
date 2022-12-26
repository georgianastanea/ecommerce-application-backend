package com.softwareEngeneering.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private BigDecimal unitPrice;

    private int quantity;

    private Long productId;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;


}
