package src.MirthAuth0;

import java.io.FileReader;
import com.google.gson.Gson;

public class AppSettings {
        
    public String Audience;
    public String Issuer;
    private log _log = new log();

    public AppSettings GetAppConfig(){
        try(FileReader reader = new FileReader("config.json")){
            Gson gson = new Gson();
            AppSettings appSettings = gson.fromJson(reader, AppSettings.class);

            return appSettings;
        }
        catch(Exception e){ 
            _log.write(e.getMessage());
            return null;
        }
    }

}
