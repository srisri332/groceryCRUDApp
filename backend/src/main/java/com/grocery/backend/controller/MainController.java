package com.grocery.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @GetMapping("/cart")
    public String cartDetails(){
        return "cart";
    }

    @PostMapping("/cart")
    public String updateCartDetails(){
        return "cart confirm";
    }

    @DeleteMapping("/cart")
    public String deleteItemsInCart(){
        return "cart delete";
    }

    @GetMapping("/order")
    public String orderDetails(){
        return "order";
    }

    @PostMapping("/order")
    public String updateOrderDetails(){
        return "order confirm";
    }

    @DeleteMapping("/order")
    public String deleteItemsInOrder(){
        return "order delete";
    }

    @GetMapping("/perishable")
    public String perishableDetails(){
        return "perishable";
    }

    @PostMapping("/perishable")
    public String updatePerishableDetails(){
        return "perishable confirm";
    }

    @DeleteMapping("/perishable")
    public String deletePerishableItem(){
        return "perishable delete";
    }

    @GetMapping("/nonperishable")
    public String nonPerishableDetails(){
        return "nonperishable";
    }

    @PostMapping("/nonperishable")
    public String updateNonPerishableDetails(){
        return "nonperishable confirm";
    }

    @DeleteMapping("/nonperishable")
    public String deleteNonPerishableItem(){
        return "nonperishable delete";
    }

    @PostMapping("/payment")
    public String confirmPayment(){
        return "nonperishable confirm";
    }
}
