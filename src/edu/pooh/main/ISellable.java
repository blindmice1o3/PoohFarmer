package edu.pooh.main;

import edu.pooh.entities.statics.statics2x2.ShippingBin;

public interface ISellable {
    int getPrice();
    void dropIntoShippingBin(ShippingBin shippingBin);
} // **** end ISellable interface ****