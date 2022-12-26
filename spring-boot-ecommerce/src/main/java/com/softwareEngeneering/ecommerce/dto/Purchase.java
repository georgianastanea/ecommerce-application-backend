package com.softwareEngeneering.ecommerce.dto;

import com.softwareEngeneering.ecommerce.entity.Address;
import com.softwareEngeneering.ecommerce.entity.Customer;
import com.softwareEngeneering.ecommerce.entity.Order;
import com.softwareEngeneering.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;

    private Address shippingAddress;

    private Address billingAddress;

    private Order order;

    private Set<OrderItem> orderItems;

}
