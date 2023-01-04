package com.softwareEngeneering.ecommerce.service;

import com.softwareEngeneering.ecommerce.dto.PaymentInfo;
import com.softwareEngeneering.ecommerce.dto.Purchase;
import com.softwareEngeneering.ecommerce.dto.PurchaseResponse;
import com.softwareEngeneering.ecommerce.entity.Customer;
import com.softwareEngeneering.ecommerce.entity.Order;
import com.softwareEngeneering.ecommerce.entity.OrderItem;
import com.softwareEngeneering.ecommerce.entity.Product;
import com.softwareEngeneering.ecommerce.repository.CustomerRepository;
import com.softwareEngeneering.ecommerce.repository.ProductRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository, @Value("${stripe.key.secret}") String secretKey, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        Stripe.apiKey = secretKey;
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

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        return PaymentIntent.create(params);

    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }

    @Override
    public List<String> getRecommendations(Purchase purchase) {

        Set<OrderItem> orderItems = purchase.getOrderItems();

        List<Product> products = new ArrayList<>();

        List<String> productsOrdered = new ArrayList<>();

        orderItems.forEach(orderItem -> products.add(productRepository.findById(orderItem.getProductId())
                .orElseThrow(() -> new RuntimeException("No product found"))));



        List<Product> recommendations = new ArrayList<>();

        products.forEach(product -> {

            if(product.getCategory().getCategoryName().equals("Books")){
                String keyword = getKeyWordForCategory1(product.getName());
                List<Product> productsFound = productRepository.findProductsByNameContaining(keyword);
                productsFound.forEach(product1 ->
                {
                    if(!product1.getName().equals(product.getName())){
                        recommendations.add(product1);
                    }
                });
            }
            else{
                String keyword = getKeyWordsForRestCategories(product.getName());
                List<Product> productsFound = productRepository.findProductsByNameContaining(keyword);
                productsFound.forEach(product1 -> {
                    if(!product1.getName().equals(product.getName())){
                    recommendations.add(product1);}
                });
            }


        });

        return recommendations.stream().map(recommendation -> recommendation.getName()).collect(Collectors.toList());


    }

    public String getKeyWordForCategory1(String productName) {

        String[] nameParts = productName.split(" ");
        List<String> acceptedKeywords = new ArrayList<>();

        for (String namePart : nameParts) {
            if (namePart.length() >= 3) {
                acceptedKeywords.add(namePart);
            }
        }

        acceptedKeywords.sort((s1, s2) -> s2.length() - s1.length());
        List<String> unwantedValues = List.of("Crash", "Guru", "Exploring", "Techniques", "Course", "Cookbook", "Guide", "Become");

        List<String> filteredValues = acceptedKeywords.stream().filter(keyword -> !unwantedValues.contains(keyword)).collect(Collectors.toList());

        return filteredValues.get(0);

    }

    public String getKeyWordsForRestCategories(String productName){

        String[] nameParts = productName.split("-");
        return nameParts[1];

    }




}
