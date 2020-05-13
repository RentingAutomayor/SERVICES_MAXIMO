/*
CODE: UPDATE KM IN MAXIMO
DATE: 07/11/2019
DESCRIPCION: THIS CODE COMPARE TWO DATABASE AFTER THIS HAS THE METHOD POST FOR UPLOAD TO IBM MAXIMO THE KILOMETERS VALUES
MADE FOR: CRISTIAN MALAVER
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

/**
 * @author c.malaver
 */
public class Leercsv {

    public String sPassword = "";
    public String sUser = "";

    public Leercsv(String pass, String usu) {
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
            br = new BufferedReader(new FileReader("A://cont.csv"));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                String placaCsv = fields[0];

                sParametros = FormatearCadenaDeParametros(fields[1], fields[0]);
               // System.out.println(sParametros);
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

        String url = "https://rentingautomayor.maximo.com/maxrest_b1dk/rest/os/MXMETERDATA/?_lid=" + sUser + "&_lpwd=" + sPassword;
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

    public static String FormatearCadenaDeParametros(String KM, String placaCsv) {
        //String fechanueva = fechan();
        String conts = "&NEWREADING=" + KM + "&ASSETNUM=" + placaCsv + "&METERNAME=RAODOMETRO&SITEID=RTAM";
        return conts;
    }

    /*public static String fechan() {
        Date fecha = new Date();
        String fe = "";
        String hr = "";
        SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss");
        fe = formatoFecha.format(fecha.getTime());
        hr = formatoHora.format(fecha.getTime());
        // return formatoFecha.format(fecha);
        //System.out.println(formatoFecha.format(fecha));
        //Leerxmlpapa(formatoFecha);
        String Fch = fe + "T" + hr + "+00:00";
        //System.out.println(Fch);

        return Fch;

    }*/
}
