package com.glassdoor.test.intern.first.payment;

import com.glassdoor.test.intern.first.database.User;
import com.glassdoor.test.intern.first.database.UserDatabaseImpl;
import com.glassdoor.test.intern.first.database.UserDatabase;
import com.glassdoor.test.intern.first.metrics.Metrics;
import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * A default implementation of {@link PaymentProcessor}.
 */
public class PaymentProcessorImpl implements PaymentProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProcessorImpl.class);

    private final UserDatabase userDatabase = UserDatabaseImpl.getInstance();
    private final Metrics metrics = Metrics.nullMetrics();

    @Override
    public PaymentResponse tryProcessPayment(@NonNull PaymentRequest request) {
        Preconditions.checkNotNull(request);
        metrics.addCount("process_payment", 1);

        User user = userDatabase.getUserById(request.getUserId());
        if (user == null) {
            metrics.addCount("process_payment.fail.noUserIdFound", 1);
            return new PaymentResponse(false);
        }

        //
        // This is my guessing:
        // If the userId not match the userName/userAddress in the request,
        // this properly someone hack this user's account, or just guess a random user ID.
        // If this happened, I want to report it as a security issue.
        // Maybe I am wrong...
        //
        if (!user.getUserName().equals(request.getUserName())
                || !user.getUserAddress().equals(request.getBillingAddress())) {
            metrics.addCount("process_payment.fail.userInfoNotMatch", 1);
            LOGGER.warn("User ID match but user info not match. This potentially to be an security issue. UserID: {}. Input userName: {}. Input userAddress: {}. User from DB: {}",
                    user.getUserId(), request.getUserName(), request.getBillingAddress(), user);
            return new PaymentResponse(false);
        }

        //
        // At this point, we just continue submit payment using the card number from the request
        // without validate if the card is associate with this user.
        // I assume user can use any card he want, so I don't add any validation here, but just trust
        // what client send us.
        //
        try {
            submitPayment(request.getCardNumber(), request.getAmount());
            metrics.addCount("process_payment.success", 1);
            return new PaymentResponse(true);
        } catch (Exception e) {
            metrics.addCount("process_payment.fail." + e.getClass().getSimpleName(), 1);
            LOGGER.error("Failed to submit payment. Request: {}.", request, e);
            return new PaymentResponse(false);
        }
    }

    //
    // I change this method to private because it is internally used.
    // I change the type of "amount" from int to BigDecimal because of the reasons written in "PaymentRequest" POJO.

    private void submitPayment(@NonNull String card, @NonNull BigDecimal amount) {
        //Don't implement this.
    }
}