package ch.avelon.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(unique = true)
    public String name;
    @OneToMany(mappedBy = "customer")
    public List<User> users;
    public int nrOfUsers;

}
