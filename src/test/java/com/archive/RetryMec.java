package com.archive;

// Functional interface for operations that might throw exceptions
@FunctionalInterface
interface Operation {
    void execute() throws Exception;
}

public class RetryMec {

    /**
     * Executes an operation and retries up to maxRetries times if it fails,
     * with a delay between retries.
     *
     * @param operation        The operation to execute.
     * @param maxRetries       The maximum number of retries.
     * @param retryDelayMillis The delay between retries in milliseconds.
     * @throws Exception If the operation fails all attempts.
     */
    public static void executeWithRetries(Operation operation, int maxRetries, long retryDelayMillis) throws Exception {
        int attempt = 0;
        while (true) {
            try {
                operation.execute();  // Attempt to execute the operation
                return;  // Success, exit the method
            } catch (Exception ex) {
                attempt++;
                if (attempt > maxRetries) {
                    throw ex;  // All attempts failed, rethrow the last exception
                }
                try {
                    Thread.sleep(retryDelayMillis);  // Delay before next attempt
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();  // Restore the interrupted status
                    throw new Exception("Retry operation was interrupted", ie);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            executeWithRetries(() -> {
                System.out.println("Attempting operation...");
                // Simulate operation that may fail
                if (Math.random() > 0.5) {
                    throw new RuntimeException("Operation failed");
                }
                System.out.println("Operation succeeded");
            }, 5, 1000);
        } catch (Exception e) {
            System.err.println("Operation failed after retries: " + e.getMessage());
        }
    }
}

