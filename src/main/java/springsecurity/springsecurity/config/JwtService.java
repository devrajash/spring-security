package springsecurity.springsecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import springsecurity.springsecurity.user.User;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    public String getUserName(String token) {
        return extractClaims(token,Claims::getSubject);
    }

    private final static String SECRET_KEY= "442A472D4B6150645367556B58703273357638792F423F4528482B4D62516554";
    public Claims extractAllClaims(String token){
      return Jwts.parserBuilder()
              .setSigningKey(getSignKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
    }

    public String genarateToken(Map<String,Object>claims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String genarateTokenWithPayload( User userDetails){
        Map<String,Object> claims =new  HashMap<>();
        claims.put("fistName",userDetails.getFirstname());
        claims.put("lastname",userDetails.getLastname());
        claims.put("userName",userDetails.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public <T> T extractClaims(String token, Function<Claims,T>claimsTFunction){
        Claims getAllClaims = extractAllClaims(token);
        return claimsTFunction.apply(getAllClaims);
    }

    private Key getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername()));
    }


}
