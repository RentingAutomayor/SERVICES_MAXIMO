/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diegokm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author c.malaver
 */
public class CargarAvvillas {
    
    public String srtUrlFile;
    private final static String USER_AGENT = "Mozilla/5.0";
    private static HttpURLConnection con;
    public static final String SEPARATOR = ";";
    public static final String QUOTE = "\"";

    public void readCsv() throws IOException, Exception {
        BufferedReader br = null;
        int count = 0;
        String sParametros = "";
        String IDVehiculo = "";
        try {
            br = new BufferedReader(new FileReader("A://avvillas1.csv"));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                
                    sParametros = Cadena(fields[0],fields[1],fields[2]);
                    sParametros = sParametros.toUpperCase();
                    sParametros = sParametros.trim();
                    sParametros = sParametros.replaceAll("\\s","");
                    System.out.println(sParametros);
                    //SendPost(sParametros);
                    
               
                count++;
                if (fields.length == 0) {
                    line = null;
                } else {
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (null != br) {
                br.close();
            }
        }
    }
    public void SendPost(String sParameters) throws Exception {
        //String  fechanueva = fechan();
        String sPassword = "A123456a";
        String sUser = "sa";
        String url = "https://hola" +" / "+ sUser +" / "+ sPassword + " / ";
        String urlParameters = sParameters;
        System.out.println(url + "" + sParameters);
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
       try {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            StringBuilder content;
            int cont = 0;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                    cont++;
                }
            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage() + "Line:" + cont);
            }
        } finally {
            con.disconnect();
        }
    }
    public static String Cadena(String gerente,String apellido,String ciudad) {
        String conts = "NOMBRE : " + gerente + "  APELLIDO : " + apellido + "  CIUDAD : " + ciudad;
        return conts;
    }   
}
