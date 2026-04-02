package ch.avelon.demo.converter;

import ch.avelon.demo.entities.User;
import ch.avelon.demo.to.UserTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class UserConverter {

    public List<UserTO> toTO(final List<User> users) {
        return users.stream()
                .map(this::toTO)
                .collect(toList());
    }

    public UserTO toTO(final User user) {
        return UserTO.builder()
                .id(user.getId())
                .customerId(user.getCustomer().getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .name(user.getUserName())
                .role(user.getRole())
                .password(user.getPassword())
                .build();
    }

    public void copyPropertiesFromTO(final User user, final UserTO userTO) {
        final String[] names = userTO.getFullName().split(" ", 2);

        user.setFirstName(names[0]);
        user.setLastName(names[1]);
        user.setUserName(userTO.getName());
        user.setRole(userTO.getRole());
        user.setPassword(userTO.getPassword());
    }
}
