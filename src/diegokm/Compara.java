/*
CODE: UPDATE KM IN MAXIMO
DATE: 07/11/2019
DESCRIPCION: THIS CODE COMPARE TWO DATABASE AFTER THIS COMPARE THE KILOMETERS VALUES. 
MADE FOR: CRISTIAN MALAVER
 */
package diegokm;

import static diegokm.Leercsv.FormatearCadenaDeParametros;
import static diegokm.Leercsv.SEPARATOR;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
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
public class Compara {

    private final static String USER_AGENT = "Mozilla/5.0";
    private static HttpURLConnection con;
    public static final String SEPARATOR = ";";
    public static final String QUOTE = "\"";

    public void Leerxmlpapa() throws IOException {

        try {

            File archivo = new File("A://val.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);
            document.getDocumentElement().normalize();
           // System.out.println("Elemento raiz:" + document.getDocumentElement().getNodeName());
            NodeList listaEmpleados = document.getElementsByTagName("ASSETMETER");

            for (int temp = 0; temp < listaEmpleados.getLength(); temp++) {
                Node nodo = listaEmpleados.item(temp);
                // System.out.println("Elemento:" + nodo.getNodeName());
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    String strID = element.getElementsByTagName("ASSETMETERID").item(0).getTextContent();
                    String strPlaca = element.getElementsByTagName("ASSETNUM").item(0).getTextContent();
                    String strKM = element.getElementsByTagName("PLUSTINITLTD").item(0).getTextContent();
                    //System.out.println("ID: " + element.getElementsByTagName("ASSETMETERID").item(0).getTextContent());
                    //System.out.println("placa: " + element.getElementsByTagName("ASSETNUM").item(0).getTextContent());
                    //System.out.println("kilometraje: " + element.getElementsByTagName("PLUSTINITLTD").item(0).getTextContent());
                    readCsv(strPlaca, strID, strKM);
                    //if (strPlaca.equals("DQQ900")) {
                    //  System.out.println(srtID);

                    //}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readCsv(String strPlaca, String srtID, String KM) throws IOException, Exception {
        BufferedReader br = null;
        boolean boolValidate = true;
        int count = 0;
        try {
            br = new BufferedReader(new FileReader("A://cont.csv"));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                String Placacsv = fields[0];
                String KMcsv = fields[1];
                if (boolValidate) {
                    if (fields.length == 0) {
                        line = null;
                    } else {
                        if (strPlaca.equals(Placacsv)) {              
                            if(!KM.equals(KMcsv)){
                                boolValidate = false;
                                System.out.println("placa xml: " + strPlaca + " KM : " + KM + "  placa csv: " + Placacsv + " KM :" + KMcsv);
                                line = null;
                            } 
                        }
                        line = br.readLine();
                    }
                } else {
                    line = null;
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
}
