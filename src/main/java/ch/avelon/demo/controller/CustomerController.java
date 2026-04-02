package ch.avelon.demo.controller;

import ch.avelon.demo.entities.Customer;
import ch.avelon.demo.service.CustomerService;
import ch.avelon.demo.to.CustomerTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    public Customer getCustomer(int customerId) {
        return customerService.getCustomerById(customerId);
    }

    public List<CustomerTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public int createCustomer(CustomerTO customerTO) {
        return customerService.createCustomer(customerTO);
    }

    public void updateCustomer(int customerId, CustomerTO customerTO) {
        customerService.updateCustomer(customerId, customerTO);
    }

    public void deleteCustomer(int customerId) {
        customerService.deleteCustomer(customerId);
    }

    public void assignUserToCustomer(int customerId, int userId) {
        customerService.assignUserToCustomer(customerId, userId);
    }

}
