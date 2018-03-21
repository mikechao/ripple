package com.mike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

public class AmountTest {
    
    private final double DELTA = 0.00001;

    @Test
    public void testAmount() {
        Amount testAmount = new Amount();
        Double expected = 123.4;
        testAmount.setAmount(expected);
        Double actual = testAmount.getAmount();
        assertEquals("The amount set in the setter did NOT match the getter", 
                expected.doubleValue(), actual.doubleValue(), DELTA);
    }
    
    @Test
    public void testCurrency() {
        Amount testAmount = new Amount();
        String expected = "USD";
        testAmount.setCurrency(expected);
        String actual = testAmount.getCurrency();
        assertEquals("The current set in the setter did NOT match the getter",
                expected, actual);
    }
    
    @Test
    public void testSerialzation() {
        Amount before = new Amount(20.0, "GBP");
        int beforeHashCode = before.hashCode();
        try {
            byte[] bytes = SerializationUtils.serialize(before);
            Amount after = SerializationUtils.deserialize(bytes);
            int afterHashCode = after.hashCode();
            assertEquals("The hash code before and after serialzation did NOT match", 
                    beforeHashCode, afterHashCode);
            assertEquals("The objects should be equal before and after serialzation, but they were NOT",
                    before, after);
        } catch (Throwable t) {
            fail("Failed during serialization " + Amount.class);
        }
    }
}
