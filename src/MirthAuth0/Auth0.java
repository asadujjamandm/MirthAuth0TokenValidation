package src.MirthAuth0;

import java.io.*;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import java.util.Date;
import java.security.interfaces.RSAPublicKey;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

public class Auth0 {

    private AppSettings _appSettings = new AppSettings();
    private String _Token;
    private log _log;

    public Auth0(String token){  
        _appSettings = _appSettings.GetAppConfig();      
        _log = new log();
        _Token = token;
    }

    
    public boolean VerifyToken() {
        try{
            String issuer = _appSettings.Issuer;
            _log.write("issuer :"+issuer);
            DecodedJWT decodedJwt = JWT.decode(this._Token);
            _log.write("decodedJwt :"+decodedJwt);
            JwkProvider jwkProvider = new JwkProviderBuilder(issuer).build();
            Jwk jwk = jwkProvider.get(decodedJwt.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            Verification verifier = JWT.require(algorithm);
            var validate = verifier.build().verify(decodedJwt);
            String audience = validate.getAudience().toString();
            _log.write("audience :"+audience);
            var expDate=validate.getExpiresAt(); 
            _log.write("expDate :"+expDate);        
            Date currentDate=new Date(); 
            boolean isVerify=false;
            if(audience.equals("["+_appSettings.Audience+"]")&&expDate.after(currentDate)){
                isVerify=true;
            }
            
            return isVerify;
            
        }
        catch(Exception e){
            _log.write("VerifyToken() :"+e.getMessage());   

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            String stackTrace = sw.toString();
            _log.write("VerifyToken() "+ stackTrace);           
            return false;
        }
        
    }

}
