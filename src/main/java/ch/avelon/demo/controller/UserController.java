package ch.avelon.demo.controller;

import ch.avelon.demo.service.UserService;
import ch.avelon.demo.to.UserTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public UserTO getUserById(@PathVariable("userId") final Integer userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/filter")
    public UserTO getUserByName(@RequestParam("name") final String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("")
    public List<UserTO> getUserByCustomer(@RequestParam("customerId") final int customerId) {
        return userService.getUsersByCustomer(customerId);
    }

    @PostMapping("")
    public int createUser(@RequestBody final UserTO userTO) {
        return userService.createUser(userTO);
    }

    @PostMapping("/{userId}")
    public void updateUser(@PathVariable("userId") final int userId, @RequestBody final UserTO userTO) {
        userService.updateUser(userId, userTO);
    }

    public void deleteUser(@RequestBody final int userId) {
        userService.deleteUser(userId);
    }

}
