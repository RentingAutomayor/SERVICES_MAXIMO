/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diegokm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
public class Deletedocu {
    
    public String srtUrlFile;
    private final static String USER_AGENT = "Mozilla/5.0";
    private static HttpURLConnection con;
    public static final String SEPARATOR = ";";
    public static final String QUOTE = "\"";

   
    public void SendPost(String strID) throws Exception {
        String sPassword = "Malangas123*";
        String sUser = "C.MALAVER";
        String url = "https://rentingautomayor.maximo.com/maxrest_b1dk/rest/mbo/PLUSTLICENSE/" + strID + "?_lid=" + sUser + "&_lpwd=" + sPassword;
        String sParameters = "";
        String urlParameters = sParameters;
        System.out.println(url + "" );
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        try {
            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("DELETE");
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
    /*public static String FormatearCadenaDeParametros(String Count) {
        String conts = "&RAENTREGADO=0"+Count;
        return conts;
    }*/
    public void Leerxmlpapa() throws IOException {
        try {
            File archivo = new File("A://CONSE//documentospro.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);
            document.getDocumentElement().normalize();
            System.out.println("Elemento raiz:" + document.getDocumentElement().getNodeName());
            NodeList listaEmpleados = document.getElementsByTagName("PLUSTLICENSE");
            //NodeList listaEmpleados1 = document.getElementsByTagName("INVUSELINE");
            
            for (int temp = 0; temp < listaEmpleados.getLength(); temp++) {
                Node nodo = listaEmpleados.item(temp);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                   String strID = element.getElementsByTagName("PLUSTLICENSEID").item(0).getTextContent();
                   //System.out.println(strID);
                  
                  SendPost(strID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("ex:"+e.getMessage());
        }
    }
    
    
}
