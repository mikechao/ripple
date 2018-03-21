package com.mike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

public class TransactionTest {

    @Test
    public void testSettersAndGetters() {
        User fromUser = new User("Bob");
        User toUser = new User("Alice");
        Amount amount = new Amount(10.0, "GBP");
        
        Transaction transaction = new Transaction();
        transaction.setFromUser(fromUser);
        User actualFromUser = transaction.getFromUser();
        assertEquals("The fromUser did NOT match the setter", fromUser, actualFromUser);
        
        transaction.setToUser(toUser);
        User actualToUser = transaction.getToUser();
        assertEquals("The toUser did NOT match the setter", toUser, actualToUser);
        
        transaction.setAmount(amount);
        Amount actualAmount = transaction.getAmount();
        assertEquals("The amount did NOT match the setter", amount, actualAmount);
    }
    
    @Test
    public void testSerialzation() {
        Transaction before = new Transaction(new User("Bob"), new User("Alice"), new Amount(10.0, "GBP"));
        String beforeToString = before.toString();
        int beforeHash = before.hashCode();
        try {
            byte[] bytes = SerializationUtils.serialize(before);
            Transaction after = SerializationUtils.deserialize(bytes);
            String afterToString = after.toString();
            int afterHash = after.hashCode();
            assertEquals("The Transaction object should be equal after serialization but was NOT", before, after);
            assertEquals("The toStrings should be equal after serialization but was NOT", beforeToString, afterToString);
            assertEquals("The hashcode should be equal after serialization but was NOT", beforeHash, afterHash);
        } catch (Throwable t) {
            fail("Failed during serialization " + Transaction.class + " " + t.getMessage());
        }
    }
    
    @Test
    public void testEquals() {
        Transaction t = new Transaction(new User("Bob"), new User("Alice"), new Amount(10.0, "USD"));
        Transaction t2 = new Transaction(new User("Bob"), new User("Alice"), new Amount(11.0, "USD"));
        assertTrue("Transaction object should equal itself", t.equals(t));
        assertFalse("2 Transaction object with different amount should NOT be equal", t.equals(t2));
        assertFalse("Transaction object should NOT equal a String", t.equals("String"));
        assertFalse("Transaction object should NOT equal NULL", t.equals(null));
    }
}
