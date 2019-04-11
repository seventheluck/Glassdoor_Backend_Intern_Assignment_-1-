package com.glassdoor.test.intern.first.database;

import com.glassdoor.test.intern.first.payment.PaymentResponse;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * PaymentResponse Tester.
 */
public class PaymentResponseTest {

    @Test
    public void testToString() {
        PaymentResponse paymentResponse = new PaymentResponse(true);
        assertEquals("PaymentResponse{success=true}", paymentResponse.toString());
    }

    @Test
    public void testEquals_happy() {
        PaymentResponse paymentResponse = new PaymentResponse(true);
        PaymentResponse paymentResponse_copy = new PaymentResponse(true);
        assertEquals(paymentResponse, paymentResponse_copy);
    }

    @Test
    public void testEquals_notEqual() {
        PaymentResponse paymentResponse = new PaymentResponse(true);
        PaymentResponse paymentResponse_copy = new PaymentResponse(false);
        assertNotEquals(paymentResponse, paymentResponse_copy);
    }

    @Test
    public void testHashCode() {
        PaymentResponse paymentResponse = new PaymentResponse(true);
        PaymentResponse paymentResponse_copy = new PaymentResponse(true);
        assertEquals(paymentResponse.hashCode(), paymentResponse_copy.hashCode());
    }
} 
