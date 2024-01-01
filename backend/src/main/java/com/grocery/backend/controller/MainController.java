package com.grocery.backend.controller;

import com.grocery.backend.entity.*;
import com.grocery.backend.service.MainService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping("/cart")
    public List<CartItem> cartDetails() {
        return mainService.getCartItems();
    }

    @PostMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateCartDetails(@RequestBody String request) {
        JSONObject requestJson = new JSONObject(request);
        String res = "Error";

        try {
            res = mainService.addOrUpdateCartItems(requestJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    @DeleteMapping("/cart/{productID}")
    public String deleteItemsInCart(@PathVariable String productID) {
        mainService.deleteProductInCart(productID);
        return "cart delete";
    }

    @GetMapping("/order")
    public List<Order> orderDetails() {
        return mainService.getOrders();
    }

    @PostMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateOrderDetails(@RequestBody String request) {
        JSONObject requestJson = new JSONObject(request);
        String res = "Error";

        try {
            res = mainService.confirmOrder(requestJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    @DeleteMapping("/order")
    public String deleteItemsInOrder() {
        return "order delete";
    }

    @PostMapping(path = "/order-details", produces = MediaType.APPLICATION_JSON_VALUE)
    public String orderSingleOrderDetails(@RequestParam("orderID") String orderID) {
        return mainService.getSingleOrderDetails(orderID).toString();
    }

    @PutMapping("/order-details")
    public void updateProcessedOrder(@RequestParam("orderID") String orderID, @RequestParam("productID") String productID, @RequestParam("quantity") int quantity){
        mainService.updateProcessedOrder(orderID,productID,quantity);
    }

    @GetMapping("/perishable")
    public List<Perishables> perishableDetails() {
        return mainService.getPerishableProducts();
    }

    @PostMapping(path = "/perishable", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addOrUpdatePerishable(@RequestParam("productImage") MultipartFile productImage,
                                        @RequestParam("manufacturedDate") String manufacturedDate,
                                        @RequestParam("expiryDate") String expiryDate,
                                        @RequestParam("productName") String productName,
                                        @RequestParam("productPrice") double productPrice) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate manufacturedDateConv = LocalDate.parse(manufacturedDate, formatter);
        LocalDate expiryDateConv = LocalDate.parse(expiryDate, formatter);
        try {
            mainService.addProduct(productImage, manufacturedDateConv, expiryDateConv, productName, productPrice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "perishable confirm";
    }

    @DeleteMapping("/perishable")
    public String deletePerishableItem(@RequestParam("productID") String productID) {
        mainService.removePerishableProduct(productID);
        return "perishable delete";
    }

    @GetMapping("/nonperishable")
    public List<NonPerishables> nonPerishableDetails() {
        return mainService.getNonPerishableProducts();
    }

    @PostMapping("/nonperishable")
    public String addOrUpdateNonPerishable(@RequestParam("productImage") MultipartFile productImage,
                                           @RequestParam("manufacturedDate") String manufacturedDate,
                                           @RequestParam("productName") String productName,
                                           @RequestParam("productPrice") double productPrice) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate manufacturedDateConv = LocalDate.parse(manufacturedDate, formatter);
        try {
            mainService.addProduct(productImage, manufacturedDateConv, productName, productPrice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "nonperishable confirm";
    }

    @DeleteMapping("/nonperishable")
    public String deleteNonPerishableItem(@RequestParam("productID") String productID) {
        mainService.removeNonPerishableProduct(productID);
        return "nonperishable delete";
    }

    @PostMapping("/payment")
    public String confirmPayment() {
        return "nonperishable confirm";
    }

    @PostMapping("/image")
    public String uploadImage(@RequestParam("productImage") MultipartFile productImage) {
//        try {
//            mainService.addProduct(productImage);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        return "upload";
    }

    @GetMapping("/image/{productImage}")
    public ResponseEntity<?> fetchImage(@PathVariable String productImage) {
        byte[] imageDate = null;
        try {
            imageDate = mainService.getImage(productImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageDate);
    }
}
