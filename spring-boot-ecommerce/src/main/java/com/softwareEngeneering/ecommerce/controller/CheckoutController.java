package com.softwareEngeneering.ecommerce.controller;

import com.softwareEngeneering.ecommerce.dto.Purchase;
import com.softwareEngeneering.ecommerce.dto.PurchaseResponse;
import com.softwareEngeneering.ecommerce.service.CheckoutService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase){

        return checkoutService.placeOrder(purchase);
    }
}
