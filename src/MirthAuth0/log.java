package src.MirthAuth0;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class log {
    
    public void write(String msg) {    
        try {
            FileWriter myWriter = new FileWriter("C:\\Program Files\\Mirth Connect\\custom-lib\\log.txt",true);                    

            myWriter.append(new Date().toString() + " " + msg + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
