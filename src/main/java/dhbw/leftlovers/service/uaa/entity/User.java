package dhbw.leftlovers.service.uaa.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "The username must be entered.")
    private String username;

    @NotNull(message = "The password must be entered.")
    private String password;
}

// TODO: Stadt Email