package com.grocery.backend.service;

import com.grocery.backend.entity.*;
import com.grocery.backend.repository.*;
import org.aspectj.weaver.ast.Or;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MainService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    PerishableRepository perishableRepository;

    @Autowired
    NonPerishableRepository nonPerishableRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    PaymentRepository paymentRepository;

    static final String userID = "admin";
    static final String folder = "C:/photos/";


    public byte[] getImage(String fileName) throws IOException {
        Path path = Paths.get(folder + fileName);
        byte[] image = Files.readAllBytes(path);
        return image;
    }


    //Polymorphism
    //Logic to save Non-Perishable product details in DB
    public String addProduct(MultipartFile productImage,
                             LocalDate manufacturedDate, String productName, double productPrice) throws IOException {
        byte[] bytes = productImage.getBytes();
        Path path = Paths.get(folder + productImage.getOriginalFilename());
        Files.write(path, bytes);

        NonPerishables nonPerishable = new NonPerishables();
        nonPerishable.setManufacturedDate(manufacturedDate);
        nonPerishable.setProductName(productName);
        nonPerishable.setProductPrice(productPrice);
        nonPerishable.setProductImage(productImage.getOriginalFilename());
        nonPerishable.setCategory("Non-Perishables");
        nonPerishableRepository.save(nonPerishable);
        return null;
    }

    //Logic to save Perishable product details in DB
    public String addProduct(MultipartFile productImage,
                             LocalDate manufacturedDate, LocalDate expiryDate, String productName, double productPrice) throws IOException {
        byte[] bytes = productImage.getBytes();
        Path path = Paths.get(folder + productImage.getOriginalFilename());
        Files.write(path, bytes);

        Perishables perishable = new Perishables();
        perishable.setManufacturedDate(manufacturedDate);
        perishable.setExpiryDate(expiryDate);
        perishable.setProductName(productName);
        perishable.setProductPrice(productPrice);
        perishable.setProductImage(productImage.getOriginalFilename());
        perishable.setCategory("Perishables");
        perishableRepository.save(perishable);
        return null;
    }

    public List<Perishables> getPerishableProducts() {
        List<Perishables> allPerishableProducts = perishableRepository.findAll();
        return allPerishableProducts;
    }

    public List<NonPerishables> getNonPerishableProducts() {
        List<NonPerishables> allNonPerishableProducts = nonPerishableRepository.findAll();
        return allNonPerishableProducts;
    }

    public String removePerishableProduct(String productID){
        try{
            List<CartItem> cartItemsInDB = cartRepository.findAll();
            boolean isInCart = cartItemsInDB.stream().anyMatch(o -> o.getProductID().equals(productID));
            if(isInCart){
                deleteProductInCart(productID);
            }
            perishableRepository.deleteByProductID(productID);
            return "removed";
        }catch (Exception e){
            return "error";
        }
    }

    public String removeNonPerishableProduct(String productID){
        try{
            List<CartItem> cartItemsInDB = cartRepository.findAll();
            boolean isInCart = cartItemsInDB.stream().anyMatch(o -> o.getProductID().equals(productID));
            if(isInCart){
                deleteProductInCart(productID);
            }
            nonPerishableRepository.deleteByProductID(productID);
            return "removed";
        }catch (Exception e){
            return "error";
        }
    }

    //Logic to add / update / remove cart items in DB for admin user
    public String addOrUpdateCartItems(JSONObject requestJson) {

        List<CartItem> cartItemsInDB = cartRepository.findAll();
        ArrayList<String> currentCartItems = new ArrayList<>();

        String[] keys = JSONObject.getNames(requestJson);

        for (String productID : keys) {
            currentCartItems.add(productID);
            boolean isInDB = cartItemsInDB.stream().anyMatch(o -> o.getProductID().equals(productID));
            Object quantity = requestJson.get(productID);

            if(Integer.parseInt((String) quantity) == 0){
                deleteProductInCart(productID);
                return "removed product";
            }

            double singleProductPrice = 0;
            String productImage = "";
            String productName = "";

            try{
                 singleProductPrice = perishableRepository.findPriceOfProduct(productID);
                 productImage = perishableRepository.findImageOfProduct(productID);
                 productName = perishableRepository.findNameOfProduct(productID);
            }catch (Exception e){
                singleProductPrice = nonPerishableRepository.findPriceOfProduct(productID);
                productImage = nonPerishableRepository.findImageOfProduct(productID);
                productName = nonPerishableRepository.findNameOfProduct(productID);
            }

            double totalProductsPrice = singleProductPrice * Integer.parseInt((String) quantity);
            if (isInDB) {
                cartRepository.updateCartValues(productID, Integer.parseInt((String) quantity), totalProductsPrice);
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setUserID(userID);
                cartItem.setProductName(productName);
                cartItem.setProductImage(productImage);
                cartItem.setProductID(productID);
                cartItem.setQuantity(Integer.parseInt((String) quantity));
                cartItem.setProductAmount(totalProductsPrice);
                cartRepository.save(cartItem);
            }
        }

//        for (CartItem singleItem : cartItemsInDB) {
//            if (!currentCartItems.contains(singleItem.getProductID())) {
//                cartRepository.deleteByProductID(singleItem.getProductID());
//            }
//        }

        return "Success";
    }

    public String deleteProductInCart(String productID){
        try{
            cartRepository.deleteByProductID(productID);
        }catch (Exception e){
            return "error";
        }

        return "Success";
    }

    public List<CartItem> getCartItems() {
        return cartRepository.findAll();
    }

    public String confirmOrder(JSONObject requestJson){

        Order newOrder = new Order();
        newOrder.setUserID(userID);
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatus("Placed");
        orderRepository.save(newOrder);

        double totalOrderAmount = 0;
        String[] keys = JSONObject.getNames(requestJson);
        for (String productID : keys) {
            double singleProductPrice = 0;
            Object quantity = requestJson.get(productID);

            try{
                singleProductPrice = perishableRepository.findPriceOfProduct(productID);
            }catch (Exception e){
                singleProductPrice = nonPerishableRepository.findPriceOfProduct(productID);
            }
            double totalProductsPrice = singleProductPrice * (Integer) quantity;
            totalOrderAmount += totalProductsPrice;

            OrderDetails newOrderDetails = new OrderDetails();
            newOrderDetails.setProductID(productID);
            newOrderDetails.setQuantity((Integer) quantity);
            newOrderDetails.setOrderID(newOrder.getOrderID());
            newOrderDetails.setProductAmount(totalProductsPrice);
            orderDetailsRepository.save(newOrderDetails);

            deleteProductInCart(productID);
        }

        confirmPayment(newOrder.getOrderID(), totalOrderAmount);

        return newOrder.getOrderID();
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public List<JSONObject> getSingleOrderDetails(String orderID){
        List<JSONObject> jsonList = new ArrayList<>();

        for(OrderDetails orderItem : orderDetailsRepository.findAll()){
            JSONObject jsonOrderItem = new JSONObject(orderItem);

            if(orderItem.getOrderID().equals(orderID)){
                String productImage = "";
                String productName = "";
                double singleProductPrice = 0;
                try{
                    productImage = perishableRepository.findImageOfProduct(jsonOrderItem.getString("productID"));
                    productName = perishableRepository.findNameOfProduct(jsonOrderItem.getString("productID"));
                    singleProductPrice = perishableRepository.findPriceOfProduct(jsonOrderItem.getString("productID"));
                }catch (Exception e){
                    productImage = nonPerishableRepository.findImageOfProduct(jsonOrderItem.getString("productID"));
                    productName = nonPerishableRepository.findNameOfProduct(jsonOrderItem.getString("productID"));
                    singleProductPrice = nonPerishableRepository.findPriceOfProduct(jsonOrderItem.getString("productID"));
                }
                jsonOrderItem.put("productImage",productImage);
                jsonOrderItem.put("productName",productName);
                jsonOrderItem.put("singleProductPrice",singleProductPrice);
                jsonList.add(jsonOrderItem);
            }
        }

        return jsonList;
    }

    public void updateProcessedOrder(String orderID, String productID, int quantity){

        System.out.println(orderDetailsRepository.findProductInOrder(orderID,productID));
        String[] productDetails = orderDetailsRepository.findProductInOrder(orderID,productID).split(",");

        int quantityInDB = Integer.parseInt(productDetails[4]);

        double singleProductPrice = Double.parseDouble(productDetails[2]) / quantityInDB;
        double totalMoneyOwed = Double.parseDouble(productDetails[5]);

        if(quantity < quantityInDB){
             totalMoneyOwed += (quantityInDB - quantity) * singleProductPrice;
             double tempMoneyOwed = (quantityInDB - quantity) * singleProductPrice;
            double currentPricePaid = Double.parseDouble(productDetails[2]) - tempMoneyOwed;

            orderDetailsRepository.updateProductDetailsInOrder(orderID,productID,quantity,currentPricePaid,totalMoneyOwed);
        }
    }

    public String confirmPayment(String orderID, double orderAmout){
        Payment newPayment = new Payment();
        newPayment.setOrderID(orderID);
        newPayment.setAmount(orderAmout);
        paymentRepository.save(newPayment);
        return "Success";
    }

}
