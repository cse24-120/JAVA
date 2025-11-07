package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Customer;
import models.Individual;
import models.Company;
import Utils.DBConnection;

public class CustomerRepository {

    public static void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (username, password, type, first_name, last_name, gender, phone_number, company_name, crn, director_name, telephone) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getPassword());

            if (customer instanceof Individual) {
                Individual ind = (Individual) customer;
                stmt.setString(3, "individual");
                stmt.setString(4, ind.getFirstName());
                stmt.setString(5, ind.getLastName());
                stmt.setString(6, ind.getGender());
                stmt.setString(7, String.valueOf(ind.getPhoneNumber()));
                stmt.setNull(8, java.sql.Types.VARCHAR);
                stmt.setNull(9, java.sql.Types.VARCHAR);
                stmt.setNull(10, java.sql.Types.VARCHAR);
                stmt.setNull(11, java.sql.Types.VARCHAR);

            } else if (customer instanceof Company) {
                Company comp = (Company) customer;
                stmt.setString(3, "company");
                stmt.setNull(4, java.sql.Types.VARCHAR);
                stmt.setNull(5, java.sql.Types.VARCHAR);
                stmt.setNull(6, java.sql.Types.VARCHAR);
                stmt.setNull(7, java.sql.Types.VARCHAR);
                stmt.setString(8, comp.getCompanyName());
                stmt.setString(9, comp.getCompanyRegNumber());
                stmt.setString(10, comp.getDirectorName());
                stmt.setString(11, String.valueOf(comp.getTelephone()));
            }

            stmt.executeUpdate();
            System.out.println("Customer added to database.");

        } catch (SQLException e) {
            System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String type = rs.getString("type");
                String username = rs.getString("username");
                String password = rs.getString("password");

                if ("individual".equalsIgnoreCase(type)) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String gender = rs.getString("gender");
                    int phone = Integer.parseInt(rs.getString("phone_number"));

                    Individual individual = new Individual(username, password, firstName, lastName, phone, gender);
                    customers.add(individual);

                } else if ("company".equalsIgnoreCase(type)) {
                    String companyName = rs.getString("company_name");
                    String crn = rs.getString("crn");
                    String directorName = rs.getString("director_name");
                    int telephone = Integer.parseInt(rs.getString("telephone"));

                    Company company = new Company(username, password, companyName, crn, directorName, telephone);
                    customers.add(company);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }

        return customers;
    }
}