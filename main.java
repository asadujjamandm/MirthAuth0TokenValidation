import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.regex.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.google.gson.Gson;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

class Main{

    public static void main(String[] args)

    {

        try {            

            HttpResponse<String> response = Unirest.post("https://dev-ald5tw-n.us.auth0.com/oauth/token")
                        .header("content-type", "application/json")
                        .body("{\"client_id\":\"QsvPtE5jlHD3QShy9tCF4Gka0Lsof5y6\",\"client_secret\":\"i8eEBhTSLvz-3Ts-Fn7CFeW6slDnfl3PPT4e7Jvj4qAg0OJrehteWBKNaiV4T7Qn\",\"audience\":\"https://mirth-api.com\",\"grant_type\":\"client_credentials\"}")
                        .asString();

            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
            //DecodedJWT decodedJwt = JWT.decode(token);
            
            Gson gson = new Gson();
            Response res = gson.fromJson(response.getBody(), Response.class);

            String issuer = "https://dev-ald5tw-n.us.auth0.com/";
            DecodedJWT decodedJwt = JWT.decode(res.access_token);
            JwkProvider jwkProvider = new JwkProviderBuilder(issuer).build();
            Jwk jwk = jwkProvider.get(decodedJwt.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            Verification verifier = JWT.require(algorithm);
            var validate = verifier.build().verify(decodedJwt);

            String audience = validate.getAudience().toString();
            var expDate=validate.getExpiresAt();
           //var listString= n.replace(/^\[(.+)\]$/,'$1')
            Date currentDate=new Date(); 
            boolean isVerify=false;
            if(audience.equals("[https://mirth-api.com]")&&expDate.after(currentDate)){
                isVerify=true;
            }
            System.out.println(isVerify);
            // try {
                        //     Algorithm algorithm = Algorithm.RSA256();
                        //     JWTVerifier verifier = JWT.require(algorithm)
                        //         .withIssuer("auth0")
                        //         .build(); //Reusable verifier instanceDecodedJWT jwt = verifier.verify(token);
                        // } catch (JWTVerificationException exception){
                        //     //Invalid signature/claims
                        // }

            System.out.println(response.getBody());

        }

        catch(Exception ex){

            ex.printStackTrace();

        }        

    }

}