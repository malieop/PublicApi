package pt.feup.nursery.authentication;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class Auth {

    //public static final String secretKey= "4C8kum4LxyKWYLM78sKdXrzbBjDCFyfX";
    public static final String secretKey= "NurserySecret";

    // TOKEN
    public static String createJWT(String id, long expirationMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setHeaderParam(Header.TYPE, Header.JWT_TYPE).setId(id)
                .setIssuedAt(now)
                .claim("user_id",id)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (expirationMillis >= 0) {
            long expMillis = nowMillis + expirationMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    private void parseJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.get("user_id"));
        System.out.println("Expiration: " + claims.getExpiration());
    }
}
