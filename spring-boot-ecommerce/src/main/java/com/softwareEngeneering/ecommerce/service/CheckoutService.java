package com.softwareEngeneering.ecommerce.service;

import com.softwareEngeneering.ecommerce.dto.Purchase;
import com.softwareEngeneering.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
