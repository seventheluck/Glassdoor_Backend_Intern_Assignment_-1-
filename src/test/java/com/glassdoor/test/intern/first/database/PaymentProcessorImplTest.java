package com.glassdoor.test.intern.first.database;

import com.glassdoor.test.intern.first.payment.PaymentProcessorImpl;
import com.glassdoor.test.intern.first.payment.PaymentRequest;
import com.glassdoor.test.intern.first.payment.PaymentResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * PaymentProcessorImpl Tester.
 */
public class PaymentProcessorImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private PaymentProcessorImpl paymentProcessor = new PaymentProcessorImpl();

    @Test
    public void tryProcessPayment_happy() {
        PaymentRequest paymentRequest = new PaymentRequest(4L,
                "Tom",
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        PaymentResponse paymentResponse = paymentProcessor.tryProcessPayment(paymentRequest);
        assertTrue(paymentResponse.isSuccess());
    }

    @Test
    public void tryProcessPayment_nullRequest() {
        thrown.expect(NullPointerException.class);
        paymentProcessor.tryProcessPayment(null);
    }

    @Test
    public void tryProcessPayment_wrongUserId() {
        thrown.expect(IllegalArgumentException.class);
        PaymentRequest paymentRequest = new PaymentRequest(0L,
                "Tom",
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        paymentProcessor.tryProcessPayment(paymentRequest);
    }

    @Test
    public void tryProcessPayment_nullUserName() {
        PaymentRequest paymentRequest = new PaymentRequest(4L,
                null,
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        PaymentResponse paymentResponse = paymentProcessor.tryProcessPayment(paymentRequest);
        assertFalse(paymentResponse.isSuccess());
    }

    @Test
    public void tryProcessPayment_emptyUserName() {
        PaymentRequest paymentRequest = new PaymentRequest(4L,
                "",
                "101 Main Street, Hot City, SS",
                new BigDecimal("123456"),
                "00112233445566778899");
        PaymentResponse paymentResponse = paymentProcessor.tryProcessPayment(paymentRequest);
        assertFalse(paymentResponse.isSuccess());
    }

    @Test
    public void tryProcessPayment_nullAddress() {
        PaymentRequest paymentRequest = new PaymentRequest(4L,
                "Tom",
                null,
                new BigDecimal("123456"),
                "00112233445566778899");
        PaymentResponse paymentResponse = paymentProcessor.tryProcessPayment(paymentRequest);
        assertFalse(paymentResponse.isSuccess());
    }

    @Test
    public void tryProcessPayment_emptyAddress() {
        PaymentRequest paymentRequest = new PaymentRequest(4L,
                "Tom",
                "",
                new BigDecimal("123456"),
                "00112233445566778899");
        PaymentResponse paymentResponse = paymentProcessor.tryProcessPayment(paymentRequest);
        assertFalse(paymentResponse.isSuccess());
    }

    @Test
    public void tryProcessPayment_wrongCardNumber() {
        // TODO
        // I do not implement this test case because the submit payment method in PaymentProcessor is not implemented.
    }
} 
