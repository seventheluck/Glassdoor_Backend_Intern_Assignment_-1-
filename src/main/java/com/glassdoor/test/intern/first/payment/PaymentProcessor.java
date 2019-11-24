package com.glassdoor.test.intern.first.payment;

import lombok.NonNull;

public interface PaymentProcessor {

    // I abstract the key method from old PaymentProcess into this interface.
    // This is because I want to make the code much easier to understand and maintain.
    //
    // I rename "IncomingRequest" to "PaymentRequest", to match the method name.
    //
    // I change the return signature of this method from a boolean to this response POJO.
    // Because we can add more info in this response (e.g. an error code or error reason when error happened).
    // or anything else needed for this payment processing.
    //
    // Finally, I rename this method from "process_payment" to "tryProcessPayment".
    // This is because if anything wrong happened, this method will not throw exception (a safe operation),
    // just like Longs.tryParse() method of Guava.
    // Instead, it use the "success" flag in the response object to indicate if error or not.
    // So name it "try~" would make more sense.

    /**
     * Try to process a payment. NOTE that if anything wrong within the payment processing, it will return a response
     * with "success" flag false, instead of throw exception.
     *
     * @param request The payment request. Required.
     * @return The payment response.
     */
    PaymentResponse tryProcessPayment(@NonNull final PaymentRequest request);
}
