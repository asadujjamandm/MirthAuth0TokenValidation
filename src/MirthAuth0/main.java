package src.MirthAuth0;


import com.google.gson.Gson;
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
            
            Gson gson = new Gson();
            Response res = gson.fromJson(response.getBody(), Response.class);

            System.out.println(res.access_token);

            Auth0 auth0 = new Auth0(res.access_token);

            System.out.println(auth0.VerifyToken());

        }

        catch(Exception ex){

            ex.printStackTrace();

        }        

    }

}