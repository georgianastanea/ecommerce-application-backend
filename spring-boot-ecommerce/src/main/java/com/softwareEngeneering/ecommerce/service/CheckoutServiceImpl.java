package com.softwareEngeneering.ecommerce.service;

import com.softwareEngeneering.ecommerce.dto.Purchase;
import com.softwareEngeneering.ecommerce.dto.PurchaseResponse;
import com.softwareEngeneering.ecommerce.entity.Customer;
import com.softwareEngeneering.ecommerce.entity.Order;
import com.softwareEngeneering.ecommerce.entity.OrderItem;
import com.softwareEngeneering.ecommerce.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public PurchaseResponse placeOrder(Purchase purchase) {

        Order order = purchase.getOrder();

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer = purchase.getCustomer();
        customer.add(order);

        customerRepository.save(customer);

        return new PurchaseResponse(orderTrackingNumber);

    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }
}
