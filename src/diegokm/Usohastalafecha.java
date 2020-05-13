/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class Usohastalafecha {

    public String srtUrlFile;
    private final static String USER_AGENT = "Mozilla/5.0";
    private static HttpURLConnection con;
    public static final String SEPARATOR = ";";
    public static final String QUOTE = "\"";

    public void SendPost(String id, String sParameters) throws Exception {
        String sPassword = "Malangas123*";
        String sUser = "C.MALAVER";
        String url = "https://rentingautomayor.maximo.com/maxrest_b1dk/rest/mbo/METERREADING/" + id + "?_lid=" + sUser + "&_lpwd=" + sPassword;
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

    public static String hola() {
        String conts = "&PLUSTLTD=0&DELTA=0&READING=0";
        return conts;
    }

    public void Leerxmlpapa() throws IOException {
        try {
            File archivo = new File("A://km uso hasta la fecha.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);
//String usr = document.getElementsByTagName("DESCRIPTION").item(0).getTextContent();
//String pwd = document.getElementsByTagName("password").item(0).getTextContent(); 
//System.out.println(usr);
            NodeList listaEmpleados = document.getElementsByTagName("METERREADING");

            for (int temp = 0; temp < (listaEmpleados.getLength()); temp++) {
                Node nodo = listaEmpleados.item(temp);
                // System.out.println("Elemento:" + nodo.getNodeName());
                //System.out.println("[NODO NUMERO " + temp + "]:: " + nodo.getNodeType());
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    String id = element.getElementsByTagName("METERREADINGID").item(0).getTextContent();
                    System.out.println(temp+" : "+id);
                    
                    String con = hola();
                    SendPost(id,con);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
