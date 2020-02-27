package pt.feup.nursery.authentication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.Instant;
import java.util.Base64;


public class Token implements Serializable {

    private String clientId;
    private String jwtToken;
    private Instant jwtTimeStamp;
    private JSONArray scope;

    //Inicializador de token interno
    public Token (JSONObject jwtToken){
        // jwtToken é o Json com a resposta ao pedido de token
        this.jwtToken = jwtToken.getString("access_token");
        // Faz decode do jwt para obtermos o timestamp e os scopes
        JSONObject jwtDecoded = new JSONObject(getBodyJWT(this.jwtToken));
        long exp = jwtDecoded.getInt("exp");
        this.jwtTimeStamp = Instant.ofEpochSecond(exp);
        this.scope = jwtDecoded.getJSONArray("scope");
    }

    //Inicializador de token externo
    public Token (String jwtTokenExt) {
        this.jwtToken = jwtTokenExt;
        JSONObject jwtDecoded = new JSONObject(getBodyJWT(jwtTokenExt));
        long exp = jwtDecoded.getInt("exp");
        this.jwtTimeStamp = Instant.ofEpochSecond(exp);
        this.scope = jwtDecoded.getJSONArray("scope");
        this.clientId = jwtDecoded.getString("client_id");
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public Instant getJwtTimeStamp() { return jwtTimeStamp; }

    public void setJwtTimeStamp(Instant jwtTimeStamp) { this.jwtTimeStamp = jwtTimeStamp; }

    public String getClientId() { return clientId; }

    public JSONArray getScope() { return scope; }

    public Boolean getJwtStatus (){
        // Token Expirado
        if(jwtTimeStamp.getEpochSecond() < System.currentTimeMillis()/1000 || jwtTimeStamp == null){
            return false;
        }
        // Token Valido
        return true;
    }
    public static String getBodyJWT(String jwt) {
        String[] split_string = jwt.split("\\.");
        //String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        //String base64EncodedSignature = split_string[2];
        byte[] jwtDecoded = Base64.getDecoder().decode(base64EncodedBody);
        String jwtString = new String(jwtDecoded);
        System.out.println(jwtString);
        return jwtString;
    }
}
