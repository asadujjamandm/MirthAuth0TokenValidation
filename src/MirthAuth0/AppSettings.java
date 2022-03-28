package src.MirthAuth0;

import java.io.*;
import java.io.FileReader;
import com.google.gson.Gson;

public class AppSettings {
        
    public String Audience;
    public String Issuer;
    private log _log = new log();

    public AppSettings GetAppConfig(){
        try(FileReader reader = new FileReader("C:\\Program Files\\Mirth Connect\\custom-lib\\config.json")){
            Gson gson = new Gson();
            AppSettings appSettings = gson.fromJson(reader, AppSettings.class);

            return appSettings;
        }
        catch(Exception e){ 
            _log.write("GetAppConfig() "+ e.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            String stackTrace = sw.toString();
            _log.write("VerifyToken() "+ stackTrace);           
            return null;
        }
    }

}
