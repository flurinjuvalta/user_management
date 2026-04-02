package ch.avelon.demo.service;

import ch.avelon.demo.converter.UserConverter;
import ch.avelon.demo.entities.Customer;
import ch.avelon.demo.entities.User;
import ch.avelon.demo.repository.CustomerRepository;
import ch.avelon.demo.repository.UserRepository;
import ch.avelon.demo.to.UserTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final Set<String> userNames = new HashSet<>();
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @PostConstruct
    void initUserNames() {
        userRepository.findAll().forEach(u -> userNames.add(u.getUserName()));
    }

    @Override
    public UserTO getUser(final int userId) {
        return userRepository.findById(userId)
                .map(userConverter::toTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
    }

    @Override
    public UserTO getUserByName(final String userName) {
        return userRepository.findByUserName(userName)
                .map(userConverter::toTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
    }

    @Override
    public List<UserTO> getUsersByCustomer(final int customerId) {
        return customerRepository.findById(customerId)
                .map(userRepository::getByCustomer)
                .map(userConverter::toTO)
                .orElseGet(Collections::emptyList);
    }

    @Override
    public int createUser(final UserTO userTO) {
        validateUserName(userTO);
        userNames.add(userTO.getName());
        final Customer customer = customerService.getCustomerForAddingUser(userTO.getCustomerId());
        final User user = new User();
        user.setCustomer(customer);
        userConverter.copyPropertiesFromTO(user, userTO);

        return userRepository.save(user).getId();
    }

    @Override
    public void updateUser(int userId, final UserTO userTO) {
        validateUserName(userTO);
        final User user = userRepository.findById(userTO.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));

        userConverter.copyPropertiesFromTO(user, userTO);

        userRepository.save(user);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

    private void validateUserName(final UserTO userTO) {
        if (userNames.contains(userTO.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already taken");
        }
    }
}

