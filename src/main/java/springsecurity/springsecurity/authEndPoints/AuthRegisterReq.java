package springsecurity.springsecurity.authEndPoints;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterReq {

    private String firstname;
    private String lastname;
    private String username;
    private String password;
}
