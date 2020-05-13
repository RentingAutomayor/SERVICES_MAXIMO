/*
This adriana's code, for upload documents like SOAT,TRM and others 
 */
package diegokm;

import static diegokm.Diegokm.sPassword;
import static diegokm.Diegokm.sUser;
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

public class Cargardocu {
    
    public String sPassword = "";
    public String sUser = "";
    public Cargardocu(String pass, String usu) {
        this.sPassword = pass;
        this.sUser = usu;
    }
    
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
            br = new BufferedReader(new FileReader("A://RTM.csv"));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                String placaCsv = fields[0];
                String desCsv = fields[1];
                String autoriCsv = fields[2];
                String costeCsv = fields[3];
                String vencimiCsv = fields[4];
                String inicioCsv = fields[5];
                String documentoCsv = fields[6];
                String tipoCsv = fields[7];
                
                
                    sParametros = Parametros(fields[0],fields[1],fields[2],fields[3],fields[4],fields[5],fields[6],fields[7]);
                    SendPost(sParametros);
                    
               
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
        String url = "https://rentingautomayor.maximo.com/maxrest_b1dk/rest/mbo/PLUSTLICENSE/?_lid=" + sUser + "&_lpwd=" + sPassword;
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
    public static String Parametros(String placa, String desc, String autoridad, String cost, String enddate, String startdate, String codigo, String tipo) {
        String conts = "&ASSETNUM="+placa+"&DESCRIPTION="+desc+"&LANGCODE=ES&AUTHORITY="+autoridad+"&COST="+cost+"&ENDDATE="+enddate+"&STARTDATE="+startdate+"&LICENSENUM="+codigo+"&TYPE="+tipo+"&HASLD=0&SITEID=RTAM&ORGID=RA";
        return conts;
    }
}
