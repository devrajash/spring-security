package springsecurity.springsecurity.authEndPoints;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthReqUser {
    private String username;
    private String password;

}
