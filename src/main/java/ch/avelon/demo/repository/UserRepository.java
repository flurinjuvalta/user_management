package ch.avelon.demo.repository;

import ch.avelon.demo.entities.Customer;
import ch.avelon.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> getByCustomer(Customer customer);

    Optional<User> findByUserName(String userName);

}
