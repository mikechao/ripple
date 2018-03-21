package com.mike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

public class UserTest {

    @Test
    public void testName() {
        String name = "Chewie";
        
        User chewie = new User();
        chewie.setName(name);
        assertEquals("The name used in the setter did NOT match the getter", 
                name, chewie.getName());
        
        chewie = new User(name);
        assertEquals("The name used in the constructor did NOT match the getter",
                name,
                chewie.getName());
    }
    
    @Test
    public void testEquals() {
        User bob = new User("Bob");
        User alice = new User("Alice");
        assertTrue("User object should equal itself", bob.equals(bob));
        assertFalse("User object should NOT equal a string", bob.equals("bob"));
        assertFalse("User Bob should not equal to User Alice", bob.equals(alice));
        assertFalse("User object should not equal a null", bob.equals(null));
    }
    
    @Test
    public void testSerialzation() {
        User john = new User("John");
        int hashBefore = john.hashCode();
        
        try {
            byte[] bytes = SerializationUtils.serialize(john);
            User after = SerializationUtils.deserialize(bytes);
            int hashAfter = after.hashCode();
            assertEquals("The toString before and after serialization did NOT match", 
                    john.toString(), after.toString());
            assertEquals("The hash code before and after serialzation did NOT match", 
                    hashBefore, hashAfter);
            assertEquals("The objects should be equal before and after serialzation, but they were NOT",
                    john, after);
        } catch (Throwable t) {
            fail("Failed during serialization " + User.class + " " + t.getMessage());
        }
    }
}
