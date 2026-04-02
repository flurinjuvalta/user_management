package ch.avelon.demo.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTO {
    private int id;
    private int customerId;
    private String name;
    private String password;
    private String fullName;
    private String role;
}
