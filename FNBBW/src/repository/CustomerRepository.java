package repository;

import models.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory repository for storing registered customers.
 */
public class CustomerRepository {
    private static List<Customer> customers = new ArrayList<>();

    public static List<Customer> getAllCustomers() {
        return customers;
    }

    public static void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
