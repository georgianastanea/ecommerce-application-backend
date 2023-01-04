package com.softwareEngeneering.ecommerce.service;

import com.softwareEngeneering.ecommerce.dto.PaymentInfo;
import com.softwareEngeneering.ecommerce.dto.Purchase;
import com.softwareEngeneering.ecommerce.dto.PurchaseResponse;
import com.softwareEngeneering.ecommerce.entity.Product;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.util.List;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);

    PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException;

    List<String> getRecommendations(Purchase purchase);
}
