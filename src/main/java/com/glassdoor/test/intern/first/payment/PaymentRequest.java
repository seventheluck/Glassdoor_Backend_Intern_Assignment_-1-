package com.glassdoor.test.intern.first.payment;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;

// Rename it from IncomingRequest to PaymentRequest
// PaymentRequest is more meaningful than IncomingRequest
// Our system will have more and more modules later, "Incoming" is easy to confuse.
public class PaymentRequest {
    // I change all fields from 'public' to 'private final'
    // since they shouldn't be accessed outside this class directly.
    // And I also want to make this class immutable.

    // I change this from int to long because int properly be not enough when user growth.
    /**
     * The identifier of the user.
     * Must be greater than 0.
     */
    private final long userId;

    /**
     * The name of the user. Must not be empty.
     */
    private final String userName;

    // I am not quite sure if the 'billingAddress' and the 'user#address' are the same thing.
    // But by reading the old version of code, it seems the system verify these two values are the same.
    // So I just keep the original name.
    /**
     * The address of the user. Must not be empty.
     */
    private final String billingAddress;

    // I change it from int to BigDecimal.
    // This is because of two reasons:
    // 1. Double is not precise enough, there will be a lot of errors.
    // 2. BigDecimal has many useful method to avoid these errors of Double.
    /**
     * The amount of this payment. Must not be empty.
     * The minimum amount is different in different systems.
     * In our system, it must be greater than 0.
     */
    private final BigDecimal amount;

    // Rename it from 'cardnumber' to 'cardNumber' to follow java naming convention.
    /**
     * The card number of this payment.
     * The length of card number is different in different systems.
     * In our system, it must not be empty.
     */
    private final String cardNumber;

    public PaymentRequest(long userId, String userName, String billingAddress, BigDecimal amount, String cardNumber) {

        Preconditions.checkArgument(userId > 0);

        this.userId = userId;
        this.userName = userName;
        this.billingAddress = billingAddress;
        this.amount = amount;
        this.cardNumber = cardNumber;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("billingAddress", billingAddress)
                .add("amount", amount)
                .add("cardNumber", cardNumber)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentRequest that = (PaymentRequest) o;
        return userId == that.userId &&
                Objects.equal(userName, that.userName) &&
                Objects.equal(billingAddress, that.billingAddress) &&
                Objects.equal(amount, that.amount) &&
                Objects.equal(cardNumber, that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, userName, billingAddress, amount, cardNumber);
    }
}