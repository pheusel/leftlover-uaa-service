package dhbw.leftlovers.service.uaa.entity;

import lombok.Data;

@Data
public class UserResponse {

    private long userid;
    private String username;
    private String email;
    private Location city;
}
