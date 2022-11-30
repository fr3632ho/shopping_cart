package com.xgen.interview;

import java.lang.reflect.Array;
import java.util.*;


/**
 * This is the current implementation of ShoppingCart.
 * Please write a replacement
 */
public abstract class ShoppingCart implements IShoppingCart {
    protected LinkedHashMap<String, Integer> contents = new LinkedHashMap<>();
    protected Pricer pricer;
    protected int total;

    public ShoppingCart(Pricer pricer) {
        this.pricer = pricer;
        total = 0;
    }

    @Override
    public void addItem(String itemType, int number) {
        if (number < 0) {
            return;
        }

        contents.put(itemType, contents.getOrDefault(itemType, 0) + number);
        total += pricer.getPrice(itemType) * number;
    }

    @Override
    public void printReceipt() {
        for (String key : contents.keySet()) {
            addReceiptLine(key);
        }
        printTotal();
    }
    
    protected void printTotal() {        
        System.out.println("Total: " + String.format("€%.2f", ((double) total)/100.0));
    }
    
    /*
     * Override to define behaviour of each printed item
     */
    protected abstract void addReceiptLine(String item);
    
}
