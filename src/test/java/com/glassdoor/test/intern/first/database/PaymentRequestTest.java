package com.glassdoor.test.intern.first.database;

import com.glassdoor.test.intern.first.payment.PaymentRequest;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * PaymentRequest Tester.
 */
public class PaymentRequestTest {

    @Test
    public void testToString() {
        PaymentRequest paymentRequest = new PaymentRequest(4L,
                "Tom",
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        assertEquals("PaymentRequest{userId=4, userName=Tom, billingAddress=101 Main Street, Hot City, SS, amount=123456, cardNumber=00112233445566778899}", paymentRequest.toString());
    }

    @Test
    public void testEquals_happy() {
        PaymentRequest paymentRequest = new PaymentRequest(4L,
                "Tom",
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        PaymentRequest paymentRequest_copy = new PaymentRequest(4L,
                "Tom",
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        assertEquals(paymentRequest, paymentRequest_copy);

    }

    @Test
    public void testEquals_notEqual() {
        PaymentRequest paymentRequest = new PaymentRequest(4L,
                "Tom",
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        PaymentRequest paymentRequest_copy = new PaymentRequest(4L,
                "TOm",
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        assertNotEquals(paymentRequest, paymentRequest_copy);

    }

    @Test
    public void testHashCode() {
        PaymentRequest paymentRequest = new PaymentRequest(4L,
                "Tom",
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        PaymentRequest paymentRequest_copy = new PaymentRequest(4L,
                "Tom",
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        assertEquals(paymentRequest.hashCode(), paymentRequest_copy.hashCode());

    }
} 
