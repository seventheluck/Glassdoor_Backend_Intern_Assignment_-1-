package com.glassdoor.test.intern.first;

import com.glassdoor.test.intern.first.payment.PaymentProcessor;
import com.glassdoor.test.intern.first.payment.PaymentProcessorImpl;
import com.glassdoor.test.intern.first.payment.PaymentRequest;
import com.glassdoor.test.intern.first.payment.PaymentResponse;

import java.math.BigDecimal;

public class PaymentApplication {
    public static void main(String[] args) {
        // This is jsut an example how the system get called:

        PaymentProcessor paymentProcessor = new PaymentProcessorImpl();

        PaymentRequest request = new PaymentRequest(
                1,
                "ABC",
                "123 Some Street, City Name, ST",
                BigDecimal.valueOf(9.9),
                "some-dummy-card-number"
        );

        PaymentResponse response = paymentProcessor.tryProcessPayment(request);

        System.out.println(response);
    }
}