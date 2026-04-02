package ch.avelon.demo.service;

import ch.avelon.demo.converter.UserConverter;
import ch.avelon.demo.entities.Customer;
import ch.avelon.demo.entities.User;
import ch.avelon.demo.repository.CustomerRepository;
import ch.avelon.demo.repository.UserRepository;
import ch.avelon.demo.to.CustomerTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    public static final int MAX_USERS_PER_CUSTOMER = 1000;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional
    public Customer getCustomerById(int customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
    }

    @Transactional
    public Customer getCustomerForAddingUser(int customerId) {
        final Customer customer = getCustomerById(customerId);
        if (customer.getNrOfUsers() == MAX_USERS_PER_CUSTOMER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reached maximum number of users");
        }
        customer.setNrOfUsers(customer.getNrOfUsers() + 1);
        return customerRepository.save(customer);
    }

    public List<CustomerTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(c -> CustomerTO.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .users(userConverter.toTO(c.getUsers()))
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public int createCustomer(CustomerTO customerTO) {
        final Customer customer = new Customer();
        customer.setName(customerTO.getName());

        customerRepository.save(customer);
        return customer.getId();
    }

    @Transactional
    public void updateCustomer(int customerId, CustomerTO customerTO) {
        final Customer customer = getCustomerById(customerId);
        customer.setName(customerTO.getName());
        customerRepository.save(customer);
    }

    @Transactional
    public void assignUserToCustomer(int customerId, int userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));

        final Customer customer = getCustomerById(customerId);

        user.setCustomer(customer);
        customer.getUsers().add(user);

        customerRepository.save(customer);
    }

    public void deleteCustomer(int customerId) {
        customerRepository.deleteById(customerId);
    }
}
