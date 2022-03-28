package src.MirthAuth0;

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
            DecodedJWT decodedJwt = JWT.decode(this._Token);
            JwkProvider jwkProvider = new JwkProviderBuilder(issuer).build();
            Jwk jwk = jwkProvider.get(decodedJwt.getKeyId());
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            Verification verifier = JWT.require(algorithm);
            var validate = verifier.build().verify(decodedJwt);
            String audience = validate.getAudience().toString();
            var expDate=validate.getExpiresAt();           
            Date currentDate=new Date(); 
            boolean isVerify=false;
            if(audience.equals("["+_appSettings.Audience+"]")&&expDate.after(currentDate)){
                isVerify=true;
            }
            
            return isVerify;
            
        }
        catch(Exception e){
            _log.write(e.getMessage());            
            return false;
        }
        
    }

}
