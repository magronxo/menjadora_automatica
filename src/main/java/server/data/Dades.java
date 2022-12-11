/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.data;

import java.time.Instant;
import java.util.List;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.Authorization;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.BucketRetentionRules;
import com.influxdb.client.domain.Organization;
import com.influxdb.client.domain.Permission;
import com.influxdb.client.domain.PermissionResource;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import java.util.Arrays;
import server.machine.Menjadora;

/**
 *
 * @author oriol
 */
public class Dades {
    
    public boolean dreta;
    
    private Menjadora menjadora;
    
    private static char[] token = "k2109pBJ6Liiw96bDjsNyiq_40eX0M94B5UnR5xxs9C35ihn6Eu64SlTwGOV95ne9LCD8lb1oZi0rTZBpOQUgw==".toCharArray();
    private static String org = "CollSalvia";
    private static String bucket = "data";
    
    //USUARI INFLUX UI: admin
    //PASSWORD: bitnami123
    
    //CONSTRUCTORS
    public Dades(Menjadora menjadora){
        this.menjadora=menjadora;

    }
    public Dades(boolean dreta){
        this.dreta=dreta;

    }
    public Dades(){
    }
    
    
    //METODES
    public static Dades addDades(Menjadora menjadora){
        return new Dades(menjadora);
    }
    
    //FUNCIONS
    
    
    public void recordMenjadoraDreta(String nomDada, double valor){
        
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        Point point = Point.measurement(nomDada)
                .addField("value", valor)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
        writeApi.writePoint(point);
    }
    
    public void recordMenjadoraEsquerra(String nomDada, double valor){      
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        Point point = Point.measurement(nomDada)
                .addField("value", valor)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
        writeApi.writePoint(point);
    }
    
    public void recordDipositDreta(String nomDada, double valor){ 
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        Point point = Point.measurement(nomDada)
                .addField("value", valor)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
        writeApi.writePoint(point);
    }
    
    public void recordDipositEsquerra(String nomDada, double valor){   
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        Point point = Point.measurement(nomDada)
                .addField("value", valor)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);
        writeApi.writePoint(point);
    }
    
    
    
    
    public Menjadora llegeixDades(){
        
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
        String flux = "from(bucket:\"data\") |> range(start: 0)";

        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(flux);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord fluxRecord : records) {
                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
            }
        }

        influxDBClient.close();
        System.out.println("Dades llegides!");
        
  
        return menjadora;
    }
    
    @Measurement(name = "raccions")
    private static class Raccions {

        @Column(tag = true)
        Boolean menjadora;

        @Column
        Integer value;

        @Column(timestamp = true)
        Instant time;
        
    }
}
