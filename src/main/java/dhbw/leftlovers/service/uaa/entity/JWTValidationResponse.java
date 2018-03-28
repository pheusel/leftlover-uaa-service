package dhbw.leftlovers.service.uaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTValidationResponse {

    private boolean validated;
}
