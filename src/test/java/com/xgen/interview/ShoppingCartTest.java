package com.xgen.interview;

import com.xgen.interview.Pricer;
import com.xgen.interview.ShoppingCart;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;;


public class ShoppingCartTest {

    class MockShoppingCart extends ShoppingCart {

        public MockShoppingCart(Pricer pricer) {
            super(pricer);            
        }

        @Override
        protected void addReceiptLine(String item) {
            int price = pricer.getPrice(item) * contents.get(item);
            double priceFloat = ((double) price) / 100.0;
            String priceString = String.format("€%.2f", priceFloat);
            System.out.println(item + " - " + contents.get(item) + " - " + priceString);
        }
        
        int getTotal() {
            return total;
        }

        LinkedHashMap<String, Integer> getContents() {
            return contents;
        }                
        
    }

    MockShoppingCart sc;
    ByteArrayOutputStream myOut;

    @Before
    public void setup() {
        sc = new MockShoppingCart(new Pricer());
        myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
    }    

    @Test
    public void canAddAnItem() {
        sc.addItem("apple", 1);
        
        assertTrue(sc.getContents().containsKey("apple"));
        assertEquals(Integer.valueOf(1), sc.getContents().get("apple"));
    }

    @Test
    public void cantAddNegativeNumber() {
        sc.addItem("apple", -1);
        assertFalse("The item should not be present in the shopping cart", sc.getContents().containsKey("apple"));
    }

    @Test
    public void canAddMoreThanOneItem() {
        sc.addItem("apple", 2);        
        assertTrue("Should contain apple",sc.getContents().containsKey("apple"));
        assertEquals(Integer.valueOf(2), sc.getContents().get("apple"));        
    }

    @Test
    public void canAddDifferentItems() {
        sc.addItem("apple", 2);
        sc.addItem("banana", 1);

        String[] order = { "apple", "banana" };
        int[] nums = { 2, 1 };
        int i = 0;
        for (Entry<String, Integer> e : sc.getContents().entrySet()) {
            assertTrue("Should contain " + order[i], e.getKey().equals(order[i]));
            assertEquals(Integer.valueOf(nums[i]), e.getValue());
            i++;
        }
    }
    

    @Test
    /*
     * Test if insertion order is perserved when updating the shopping cart
     */
    public void canAddManyDifferentWithCorrectOrder() {
        for (char k = 'a'; k < 'z'; k++) {
            sc.addItem(k + "", 1);
        }
        char k='a';
        for (Entry<String, Integer> e : sc.getContents().entrySet()) {
            assertTrue("Should contain " + e.getKey() + "!", sc.getContents().containsKey(k + ""));
            assertEquals(String.valueOf(e.getKey()), k + "");
            k++;
        }
    }

    @Test
    public void doesntExplodeOnMysteryItem() {
        sc.addItem("crisps", 2);
        assertTrue(sc.getContents().containsKey("crisps"));
        assertEquals(Integer.valueOf(0), Integer.valueOf(sc.getTotal()));
    }
}


