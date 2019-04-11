package com.glassdoor.test.intern.first.metrics;

// One other important feature I want to add into this project is the Metrics.
// Metrics is very important for us to understand how the system performs (error rate, latency, etc).
// So I define this Metrics interface, and use it in the business logic code.

/**
 * An interface of metrics.
 */
public interface Metrics {

    /**
     * Return a Metrics instance that do nothing. Just for demo purpose.
     * We can replace it by using some real metrics systems.
     */
    static Metrics nullMetrics() {
        return NullMetrics.INSTANCE;
    }

    /**
     * Add counter by given name and count value.
     */
    void addCount(String name, long count);

    /**
     * Add latency time by given name, and time (in milliseconds).
     */
    void addTimer(String name, long timeInMillis);

    class NullMetrics implements Metrics {
        private static final NullMetrics INSTANCE = new NullMetrics();

        @Override
        public void addCount(String name, long count) {

        }

        @Override
        public void addTimer(String name, long timeInMillis) {

        }
    }
}
