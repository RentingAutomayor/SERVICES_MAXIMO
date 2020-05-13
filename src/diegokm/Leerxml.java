/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diegokm;

import java.io.File;
import java.io.IOException;
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
public class Leerxml {

    public void Leerxmlpapa() throws IOException {

        try {
            File archivo = new File("A://datosid.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);
            document.getDocumentElement().normalize();
            System.out.println("Elemento raiz:" + document.getDocumentElement().getNodeName());
            NodeList listaEmpleados = document.getElementsByTagName("ASSETMETER");
            for (int temp = 0; temp < listaEmpleados.getLength(); temp++) {
                Node nodo = listaEmpleados.item(temp);
               // System.out.println("Elemento:" + nodo.getNodeName());
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    String srtID = element.getElementsByTagName("ASSETMETERID").item(0).getTextContent();
                    String strPlaca = element.getElementsByTagName("ASSETNUM").item(0).getTextContent();

                    //System.out.println("ID: " + element.getElementsByTagName("ASSETMETERID").item(0).getTextContent());
                    //System.out.println("placa: " + element.getElementsByTagName("ASSETNUM").item(0).getTextContent());
                    //System.out.println("kilometraje: " + element.getElementsByTagName("PLUSTINITLTD").item(0).getTextContent());
                    if (strPlaca.equals("DQQ900")) {
                        System.out.println(srtID);
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
