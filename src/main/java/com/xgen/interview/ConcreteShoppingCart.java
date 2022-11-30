package main.java.com.xgen.interview;

import com.xgen.interview.Pricer;
import com.xgen.interview.ShoppingCart;

/*
 * Implementation of ShoppingCart with addReceiptLine implemented.
 */
public class ConcreteShoppingCart extends ShoppingCart {

    public ConcreteShoppingCart(Pricer pricer) {
        super(pricer);
    }
    
    @Override
    public void addReceiptLine(String item) {
        int price = pricer.getPrice(item) * contents.get(item);
        double priceFloat = ((double) price) / 100.0;
        String priceString = String.format("€%.2f", priceFloat);
        System.out.println(item + " - " + contents.get(item) + " - " + priceString);
    }
    
}