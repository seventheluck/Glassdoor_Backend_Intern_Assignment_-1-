package com.glassdoor.test.intern.first.payment;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class PaymentResponse {
    // success flag of submit payment.
    private boolean success;

    public PaymentResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("success", success)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentResponse response = (PaymentResponse) o;
        return success == response.success;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(success);
    }
}
