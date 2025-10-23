package session;

import models.Customer;

/**
 * Holds the currently logged-in customer across scenes.
 */
public class Session {
    private static Customer currentCustomer;

    public static void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }

    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }
}
