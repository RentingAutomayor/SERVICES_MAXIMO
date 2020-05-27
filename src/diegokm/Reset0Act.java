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
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.DOMException;

/**
 *
 * @author c.malaver
 */
public class Reset0Act {

    //inicializa las credenciales
    public String sPassword = "";
    public String sUser = "";

    //trae las credenciales
    public Reset0Act(String pass, String usu) {
        this.sPassword = pass;
        this.sUser = usu;
    }
    //se define parametros a utilizar
    public String srtUrlFile;
    private final static String USER_AGENT = "Mozilla/5.0";
    private static HttpURLConnection con;
    public static final String SEPARATOR = ";";
    public static final String QUOTE = "\"";

    public void Reset0Act() throws IOException, Exception {
        BufferedReader br = null;
        int count = 0;
        String sParametros = "";
        //Lectura de un archivo csv y trae el campo de la columna 0 
        try {
            br = new BufferedReader(new FileReader("A://act2.csv"));
            String line = br.readLine();
            while (null != line) {
                String[] fields = line.split(SEPARATOR);
                //System.out.println(fields[3]);
                sParametros = FormatearCadenaDeParametros(fields[1]);
                //System.out.println(sParametros);
                //envia a la funcion los parametro de consulta
                SendGet(sParametros);

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

    //funcion de consulta en formato JSON 
    public void SendGet(String sParameters) throws Exception {

        String url = "https://rentingautomayor.maximo.com/maxrest_b1dk/rest/mbo/METERREADING?_format=json&_lid=" + sUser + "&_lpwd=" + sPassword;
        String urlParameters = url + "" + sParameters;
        //System.out.println(urlParameters);
        //abre conexion
        URL obj = new URL(urlParameters);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        //Add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + urlParameters);
        String td = ("Response Code : " + responseCode);
        //System.out.println(td);
        //construye la respuesta
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                //System.out.println("DATA:"+response.toString());
            }
        }
        //con la libreria json Trae los nodos y los empieza a leer
        try {
            //System.out.println(response.toString());
            JSONObject json = new JSONObject(response.toString());
            //este es el nodo papa
            JSONObject prueba = json.getJSONObject("METERREADINGMboSet");
            //System.out.println(prueba.toString());
            //este es el nodo hijo
            JSONArray json_array = new JSONArray(prueba.getJSONArray("METERREADING").toString());

            //System.out.println(json_array);
            //bucle para extraer los datos de los nodos
            for (int i = 0; i < json_array.length(); i++) {
                JSONObject objeto = json_array.getJSONObject(i);
                JSONObject atributos = objeto.getJSONObject("Attributes");
                //este es contenido que se necesita en rta json
                JSONObject documento = atributos.getJSONObject("METERREADINGID");
                String Idmedicion = documento.getString("content");
                //System.out.println(i);
                //envia en forma de lista los datos extraidos
                SendPost(i, Idmedicion);

            }
        } catch (DOMException e) {
            System.out.println("[ERROR]: " + e.getMessage());
        }
    }

    //esta funcion agrega los parametros de consulta para el CSV 
    public static String FormatearCadenaDeParametros(String placaCsv) {
        String conts = "&ASSETNUM=" + placaCsv;
        return conts;
    }

    //funcion que envia un post con los 
    public void SendPost(int i,String Idmedicion) throws Exception {
        String reset = "&PLUSTLTD=0&READING=0&DELTA=0";
        String url = "https://rentingautomayor.maximo.com/maxrest_b1dk/rest/mbo/METERREADING/" + Idmedicion + "?_lid=" + sUser + "&_lpwd=" + sPassword; 
        String urlParameters = reset;
        System.out.println(i+" : "+url+urlParameters);
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
}
