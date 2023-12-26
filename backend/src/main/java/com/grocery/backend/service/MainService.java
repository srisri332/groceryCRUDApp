package com.grocery.backend.service;

import com.grocery.backend.entity.CartItem;
import com.grocery.backend.entity.NonPerishables;
import com.grocery.backend.entity.Perishables;
import com.grocery.backend.repository.CartRepository;
import com.grocery.backend.repository.NonPerishableRepository;
import com.grocery.backend.repository.PerishableRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class MainService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    PerishableRepository perishableRepository;

    @Autowired
    NonPerishableRepository nonPerishableRepository;

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
                             LocalDateTime manufacturedDate, String productName, double productPrice) throws IOException {
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
                             LocalDateTime manufacturedDate, LocalDateTime expiryDate, String productName, double productPrice) throws IOException {
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


    //Logic to add / update / remove cart items in DB for admin user
    public String addOrUpdateCartItems(JSONObject requestJson) {

        List<CartItem> cartItemsInDB = cartRepository.findAll();
        ArrayList<String> currentCartItems = new ArrayList<>();

        String[] keys = JSONObject.getNames(requestJson);

        for (String productID : keys) {
            currentCartItems.add(productID);
            boolean isInDB = cartItemsInDB.stream().anyMatch(o -> o.getProductID().equals(productID));
            Object quantity = requestJson.get(productID);
            if (isInDB) {
                cartRepository.updateCartValues(productID, (Integer) quantity);
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setUserID(userID);
                cartItem.setProductID(productID);
                cartItem.setQuantity((Integer) quantity);
                cartRepository.save(cartItem);
            }
        }

        for (CartItem singleItem : cartItemsInDB) {
            if (!currentCartItems.contains(singleItem.getProductID())) {
                cartRepository.deleteByProductID(singleItem.getProductID());
            }
        }

        return "Success";
    }

    public List<CartItem> getCartItems() {
        return cartRepository.findAll();
    }


}
