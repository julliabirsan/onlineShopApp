package com.example.onlineShopApp;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;

@SessionScope
@Component
public class UserSession {
    private int id;
    private HashMap<Integer, Integer> shoppingCart = new HashMap<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Integer, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(HashMap<Integer, Integer> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public void addToCart(int id){
        if (shoppingCart.get(id) != null){
            int currentAmount = shoppingCart.get(id);
            int newAmount = currentAmount +1;
            shoppingCart.put(id, newAmount);
        } else {
            shoppingCart.put(id, 1);
        }
    }
    public int getCartSize(){
        int totalSize = 0;
        for (Integer size : shoppingCart.values()){
            totalSize = totalSize + size;
        }
        return totalSize;
    }

    public void clearCart(){
        shoppingCart.clear();
    }
}
