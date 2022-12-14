/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.data;

import java.time.Instant;
import java.util.List;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import server.machine.Menjadora;

/**
 *
 * @author oriol
 */
public class Dades {
    
    public boolean dreta;
    private Menjadora menjadora;
    
    private int resultats = 72;//168
    
    private double[] gramsRaccio = new double[resultats];
    private double[] limitDiari = new double[resultats];
    private double[] gramsAcumulatsAvui = new double[resultats];
    private double[] sensorPlat = new double[resultats];
    private double[] sensorNivell = new double[resultats];
    private double[] valorAlertaDiposit = new double[resultats];
    private double[] valorDipositBuit = new double[resultats];
    
    private double[] gramsRaccioE = new double[resultats];
    private double[] limitDiariE = new double[resultats];
    private double[] gramsAcumulatsAvuiE = new double[resultats];
    private double[] sensorPlatE = new double[resultats];
    private double[] sensorNivellE = new double[resultats];
    private double[] valorAlertaDipositE = new double[resultats];
    private double[] valorDipositBuitE = new double[resultats];
 
    
    int a=0;int b=0;int c=0;int d=0;int e=0;int f=0;int g=0;
    int h=0;int i=0;int j=0;int k=0;int l=0;int m=0;int n=0;

    private static char[] token = "k2109pBJ6Liiw96bDjsNyiq_40eX0M94B5UnR5xxs9C35ihn6Eu64SlTwGOV95ne9LCD8lb1oZi0rTZBpOQUgw==".toCharArray();
    private static String org = "CollSalvia";
    private static String bucketDreta = "dreta";
    private static String bucketEsquerra = "esquerra";

    //USUARI INFLUX UI: admin
    //PASSWORD: bitnami123
    
    //CONSTRUCTORS
    public Dades(Menjadora menjadora){
        this.menjadora=menjadora;
    }
    public Dades(){
        
    }
    public Dades(boolean dreta){
        this.dreta=dreta;

    }

    //FUNCIONS
    
    //WRITE
    public void recordMenjadoraDreta(String nomDada, double valor){
        
        //GUARDEM LES DADES DE LA SIMULACIÓ. ALGUNES CADA EXECUCIÓ I LES ALTRES EN FINALITZAR EL DIA
        
        //PUJEM LA DADA A INFLUXDB
        try{
            InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucketDreta);
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

            Point point = Point.measurement(nomDada)
                    .addField("value", valor)
                    .time(Instant.now().toEpochMilli(), WritePrecision.S);
            writeApi.writePoint(point);
        }catch(Exception e){
             System.out.println("Influx Error");     
        }
        
        //POSEM TAMBÉ LA DADA AL SEU ARRAY PER MOSTRAR A ESTADISTIQUES
        switch(nomDada){
            // 4 VALORS MENJADORA
            case "gramsRaccio":
                if(a<gramsRaccio.length){
                   gramsRaccio[a] = (double)valor; 
                }else{
                    pushPopArray(gramsRaccio,(double)valor);
                }
                a++;      
            break;
            case "limitDiari":
                if(b<limitDiari.length){
                   limitDiari[b] = (double)valor; 
                }else{
                    pushPopArray(limitDiari,(double)valor);
                }
                b++; 
            break;
            case "gramsAcumulatsAvui":
                if(c<gramsAcumulatsAvui.length){
                   gramsAcumulatsAvui[c] = (double)valor; 
                }else{
                    pushPopArray(gramsAcumulatsAvui,(double)valor);
                }
                c++;
            break;
            case "sensorPlat":
                if(d<sensorPlat.length){
                   sensorPlat[d] = (double)valor; 
                }else{
                    pushPopArray(sensorPlat,(double)valor);
                }
                d++;
            break;
            // 3 VALORS DIPOSIT
            case "sensorNivell":  
                if(e<sensorNivell.length){
                   sensorNivell[e] = (double)valor; 
                }else{
                    pushPopArray(sensorNivell,(double)valor);
                }
                e++;
            break;
            case "valorAlertaDiposit":
                if(f<valorAlertaDiposit.length){
                   valorAlertaDiposit[f] = (double)valor; 
                }else{
                    pushPopArray(valorAlertaDiposit,(double)valor);
                }
                f++;
            break;
            case "valorDipositBuit":
                if(g<valorDipositBuit.length){
                   valorDipositBuit[g] = (double)valor; 
                }else{
                    pushPopArray(valorDipositBuit,(double)valor);
                }
                g++;
            break;
        }
    }
    
    public void recordMenjadoraEsquerra(String nomDada, double valor){
        try{
            InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucketEsquerra);
            WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

            Point point = Point.measurement(nomDada)
                    .addField("value", valor)
                    .time(Instant.now().toEpochMilli(), WritePrecision.S);
            writeApi.writePoint(point);
        }catch(Exception e){
             System.out.println("Influx Error");     
        }
        
        switch(nomDada){
            // 4 VALORS MENJADORA
            case "gramsRaccio":
                if(h<gramsRaccioE.length){
                   gramsRaccioE[h] = (double)valor; 
                }else{
                    pushPopArray(gramsRaccioE,(double)valor);
                } 
                h++;                    
            break;
            case "limitDiari":
                if(i<limitDiariE.length){
                   limitDiariE[i] = (double)valor; 
                }else{
                    pushPopArray(limitDiariE,(double)valor);
                } 
                i++;              
            break;
            case "gramsAcumulatsAvui":
                if(j<gramsAcumulatsAvuiE.length){
                   gramsAcumulatsAvuiE[j] = (double)valor; 
                }else{
                    pushPopArray(gramsAcumulatsAvuiE,(double)valor);
                } 
                j++;
            break;
            case "sensorPlat":
                if(k<sensorPlatE.length){
                   sensorPlatE[k] = (double)valor; 
                }else{
                    pushPopArray(sensorPlatE,(double)valor);
                }                   
                k++;
            break;
            // 3 VALORS DIPOSIT
            case "sensorNivell":
                if(l<sensorNivellE.length){
                   sensorNivellE[l] = (double)valor; 
                }else{
                    pushPopArray(sensorNivellE,(double)valor);
                } 
                l++;
            break;
            case "valorAlertaDiposit":
                if(m<valorAlertaDipositE.length){
                   valorAlertaDipositE[m] = (double)valor; 
                }else{
                    pushPopArray(valorAlertaDipositE,(double)valor);
                } 
                m++; 
            break;
            case "valorDipositBuit":
                if(n<valorDipositBuitE.length){
                   valorDipositBuitE[n] = (double)valor; 
                }else{
                    pushPopArray(valorDipositBuitE,(double)valor);
                } 
                n++;
            break;
        }
    }
    
    private void pushPopArray(double[]arr,double valor){
        for(int i=0;i<arr.length-1;i++){
            arr[i]= arr[i+1];
            arr[arr.length-1]=valor;
        }
    }
    
    //READ
    public double[] llegeixDades(boolean dreta, String mesura){
    
        double[] resultatLlegit = new double[resultats];
                
        if(dreta){
            switch(mesura){
                // 4 VALORS MENJADORA
                case "gramsRaccio":
                    resultatLlegit = gramsRaccio;                             
                break;
                case "limitDiari":
                    resultatLlegit = limitDiari;  
                break;
                case "gramsAcumulatsAvui":
                    resultatLlegit = gramsAcumulatsAvui;  
                break;
                case "sensorPlat":
                    resultatLlegit = sensorPlat;  
                break;
                // 3 VALORS DIPOSIT
                case "sensorNivell":
                    resultatLlegit = sensorNivell;  
                break;
                case "valorAlertaDiposit":
                    resultatLlegit = valorAlertaDiposit;   
                break;
                case "valorDipositBuit":
                    resultatLlegit = valorDipositBuit;  
                break;
            }
        }else if(!dreta){
            switch(mesura){
                // 4 VALORS MENJADORA
                case "gramsRaccio":
                    resultatLlegit = gramsRaccioE;                             
                break;
                case "limitDiari":
                    resultatLlegit = limitDiariE;  
                break;
                case "gramsAcumulatsAvui":
                    resultatLlegit = gramsAcumulatsAvuiE;  
                break;
                case "sensorPlat":
                    resultatLlegit = sensorPlatE;  
                break;
                // 3 VALORS DIPOSIT
                case "sensorNivell":
                    resultatLlegit = sensorNivellE;  
                break;
                case "valorAlertaDiposit":
                    resultatLlegit = valorAlertaDipositE;   
                break;
                case "valorDipositBuit":
                    resultatLlegit = valorDipositBuitE;  
                break;
            }           
        }else{
            System.out.println("Array dades parameters Incorrectes!");
        }
        System.out.println("\t\t\t\tDades llegides!");
        return resultatLlegit;           
              
        
         //AJUSTAR EL RANGE A LA ULTIMA SETMANA = 4 SEGONS * 24H * 7 DIES = 672. Els ultims 672 segons
        //5 SEGONS * 24H * 7 DIES = 840.
        // * 6 = 1008 segons
        
        //InfluxDBClient influxDBClient = null;
        //QueryApi queryApi = null;
        //String flux = "";
        
        /*
        if(dreta){
            influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucketDreta);
            flux = "from(bucket:\"dreta\") |> range(start: -1h)";
            //flux = "from(bucket:\"dreta\") |> range(start: -672s) |> window(every: 40s) |> last() |> fill(usePrevious: true)";
        }else if(!dreta){
            influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucketEsquerra);
            flux = "from(bucket:\"esquerra\") |> range(start: -1h)";
        }else{
            System.out.println("Influx read parameters Incorrectes!");
        }
        queryApi = influxDBClient.getQueryApi();
        
        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                            
                System.out.println(fluxRecord.getTime() + ": " +fluxRecord.getMeasurement() + ": " + fluxRecord.getValueByKey("_value"));
                               
                switch(mesura){
                    // 4 VALORS MENJADORA
                    case "gramsRaccio":
                        if(fluxRecord.getMeasurement().equals("gramsRaccio")){
                            for (int i=0;i<resultats;i++) {
                                resultatLlegit[i] = (double)fluxRecord.getValueByKey("_value");
                            }
                        }
                    break;
                    case "limitDiari":
                        if(fluxRecord.getMeasurement().equals("limitDiari")){
                            for (int i=0;i<resultats;i++) {
                                resultatLlegit[i] = (double)fluxRecord.getValueByKey("_value");
                            }
                        }
                    break;
                    case "gramsAcumulatsAvui":
                        if(fluxRecord.getMeasurement().equals("gramsAcumulatsAvui")){
                            for (int i=0;i<resultats;i++) {
                                resultatLlegit[i] = (double)fluxRecord.getValueByKey("_value");
                            }
                        }
                    break;
                    case "sensorPlat":
                        if(fluxRecord.getMeasurement().equals("sensorPlat")){
                            for (int i=0;i<resultats;i++) {
                                resultatLlegit[i] = (double)fluxRecord.getValueByKey("_value");

                                //resultatLlegit[i] = (double)fluxRecord.getValueByIndex(i);
                                //Map<String, Object> fluxMap = fluxRecord.getValues();
                                //System.out.println("Dades llegides!");    
                            }
                        }
                    break;
                    // 3 VALORS DIPOSIT
                    case "sensorNivell":
                        if(fluxRecord.getMeasurement().equals("sensorNivell")){
                            for (int i=0;i<resultats;i++) {
                                resultatLlegit[i] = (double)fluxRecord.getValueByKey("_value");
                            }
                        }
                    break;
                    case "valorAlertaDiposit":
                        if(fluxRecord.getMeasurement().equals("gramsRaccio")){
                            for (int i=0;i<resultats;i++) {
                                resultatLlegit[i] = (double)fluxRecord.getValueByKey("_value");
                            }
                        }
                    break;
                    case "valorDipositBuit":
                        if(fluxRecord.getMeasurement().equals("gramsRaccio")){
                            for (int i=0;i<resultats;i++) {
                                resultatLlegit[i] = (double)fluxRecord.getValueByKey("_value");
                            }
                        }
                    break;
                }
            }
        }        
        influxDBClient.close();
        System.out.println("Dades llegides!");

    */

        //return resultatLlegit;       
    }
    
    //ACCESSORS
    public boolean isDreta() {
        return dreta;
    }
    
    
}
