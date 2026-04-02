package ch.avelon.demo.service;

import ch.avelon.demo.to.UserTO;

import java.util.List;

public interface UserService {
    UserTO getUser(int userId);
    UserTO getUserByName(String userName);
    List<UserTO> getUsersByCustomer(int customerId);
    int createUser(UserTO userTO);
    void updateUser(int userId, UserTO userTO);
    void deleteUser(int userId);
}
