/*
This code for the method POST send "0" to the 55 license plate that has "1" in the  delivery
 */
package diegokm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author c.malaver
 */
public class Porentregara0 {
    
     public String srtUrlFile;
    private final static String USER_AGENT = "Mozilla/5.0";
    private static HttpURLConnection con;
    public static final String SEPARATOR = ";";
    public static final String QUOTE = "\"";

    public void readCsv(String strPlaca, String srtID) throws IOException, Exception {
        BufferedReader br = null;
        int count = 0;
        String sParametros = "";
        String IDVehiculo = "";
        try {
            br = new BufferedReader(new FileReader("A://porentregara0.csv"));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                String placaCsv = fields[0];
              // System.out.println(placaCsv);
                 if (strPlaca.equals(placaCsv)) {
                    sParametros = FormatearCadenaDeParametros(fields[0]);
                    //System.out.println(sParametros);
                   try {
                     SendPost(srtID, sParametros);
                 } catch (IOException ex) {
                    Logger.getLogger(Leercsv.class.getName()).log(Level.SEVERE, null, ex);
                 }
                }
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
    public void SendPost(String strID, String sParameters) throws Exception {
        String sPassword = "Renting123*";
        String sUser = "maxadmin";
        String url = "https://rentingautomayor.maximo.com/maxrest_b1dk/rest/os/RA_ASSET_CONTRACT/" + strID + "?_lid=" + sUser + "&_lpwd=" + sPassword;
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
    public static String FormatearCadenaDeParametros(String Count) {
        String conts = "&RAENTREGADO=0";
        return conts;
    }
    public void Leerxmlpapa() throws IOException {
        try {
            File archivo = new File("A://papapa.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);
            document.getDocumentElement().normalize();
            System.out.println("Elemento raiz:" + document.getDocumentElement().getNodeName());
            //NodeList listaEmpleados = document.getElementsByTagName("RA_ASSET_CONTRACTSet");
            NodeList listaEmpleados1 = document.getElementsByTagName("RA_ACTIVOXCONTRATO");
            
            for (int temp = 0; temp < listaEmpleados1.getLength(); temp++) {
                Node nodo = listaEmpleados1.item(temp);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    String strID = element.getElementsByTagName("RA_ACTIVOXCONTRATOID").item(0).getTextContent();
                    String strPlaca = element.getElementsByTagName("RAACTIVO").item(0).getTextContent();
                   //System.out.println(strID);
                   //System.out.println(strPlaca);
                   readCsv(strPlaca, strID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("ex:"+e.getMessage());
        }
    }
    
}
