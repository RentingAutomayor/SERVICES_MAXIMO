/*
CODE: UPDATE KM IN MAXIMO
DATE: 07/11/2019
DESCRIPCION: THIS CODE COMPARE TWO DATABASE AFTER THIS HAS THE METHOD POST FOR UPLOAD TO IBM MAXIMO THE KILOMETERS VALUES
MADE FOR: CRISTIAN MALAVER
 */
package diegokm;


import java.io.IOException;

/**
 * @author c.malaver
 */
public class Diegokm {

    public static Integer intValidate = 14;
    public static String sPassword = "Malangas123*";
    public static String  sUser = "C.MALAVER";

    public static void main(String[] args) throws IOException, Exception {
        // HERE HAS TWO METHODS FOR COMPILE, THE ONE COMPILE ALL CODE, THE SECOND COMPILE ONLY THE CLASS "COMPARA"

        switch (intValidate) {
            case 0:
                //Upload the kilometers
                Leercsv objLeercsv = new Leercsv(sPassword,sUser);
                objLeercsv.readCsv();
                break;
            case 1:
                //Compare the Upload kilometers with Downloads Kilometers
                Compara objCompara = new Compara();
                objCompara.Leerxmlpapa();
                break;
            case 2:
                //Upload the MP
                Mpcount objMpcount = new Mpcount(sPassword,sUser);
                objMpcount.Leerxmlpapa();
                break;
            case 3:
                //Upload porentregar
                Porentregar objPorentregar = new Porentregar();
                objPorentregar.Leerxmlpapa();

                break;
            case 4:
                //put por entregar in 0
                Porentregara0 objPorentregara0 = new Porentregara0();
                objPorentregara0.Leerxmlpapa();
                break;
            case 5:
                //Delete register the por entregar        
                Deleteinven objDeleteinven = new Deleteinven();
                objDeleteinven.Leerxmlpapa();
                break;
            case 6:
                //Delete register the contratos de compra        
                DeleteCC objDeleteCC = new DeleteCC();
                objDeleteCC.Leerxmlpapa();
                break;
            case 7:
                //Update contract and put values in tramite
                Contratos objContratos = new Contratos();
                objContratos.Leerxmlpapa();
                break;
            case 8:
                //poner a 0 registros de uso hasta la fecha
                Usohastalafecha objUsohastalafecha = new Usohastalafecha();
                objUsohastalafecha.Leerxmlpapa();
                break;
            case 9:
                //poner a 0 km de activos tr
                Ponera0km objPonera0km = new Ponera0km(sPassword,sUser);
                objPonera0km.Leerxmlpapa();
                break;
            case 10:
                //cargar documentos de adriana 
                Cargardocu objCargardocu = new Cargardocu(sPassword,sUser);
                objCargardocu.readCsv();
                break;
            case 11:
                //eliminar documentos de adriana en activos tr
                Deletedocu objDeletedocu = new Deletedocu();
                objDeletedocu.Leerxmlpapa();
                break;
            case 12:
                //cargar datos de gerentes a avvillas 
                CargarAvvillas objCargarAvvillas = new CargarAvvillas();
                objCargarAvvillas.readCsv();
                break;
            case 13:
                //Vuelve a 0 los registros de los kilometrajes para placas seleccionadas
                Reset0Act objReset0Act = new Reset0Act(sPassword,sUser);
                objReset0Act.Reset0Act();
                break;
            case 14:
                //Vuelve a 0 los valores actuales de lo kilometrajes para placas seleccionadas
                NuevoPonerA0KM objNuevoPonerA0KM = new NuevoPonerA0KM(sPassword,sUser);
                objNuevoPonerA0KM.NuevoPonerA0KM();
                break;
            
        }

    }

}
