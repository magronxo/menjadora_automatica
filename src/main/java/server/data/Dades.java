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
    
    
    public void escriuDades(double gramsAcumulatAvui){
        
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        //
        // Write by Data Point
        //
        /*Point point = Point.measurement("temperature")
                .addTag("location", "west")
                .addField("value", 55D)
                .time(Instant.now().toEpochMilli(), WritePrecision.MS);

        writeApi.writePoint(point);*/

        //
        // Write by LineProtocol
        //
        writeApi.writeRecord(WritePrecision.NS, "Menjadora,dreta?="+ menjadora.isDreta()+" gramsAcumulatAvui="+gramsAcumulatAvui);

        //
        // Write by POJO
        //
        Raccions raccions = new Raccions();
        //raccions.limitRaccionsDia = this.menjadora.getLimitRaccionsDia();
        raccions.menjadora = menjadora.isDreta();
        raccions.value = this.menjadora.getRaccionsAcumuladesAvui();
        raccions.time = Instant.now();

        writeApi.writeMeasurement( WritePrecision.NS, raccions);
        influxDBClient.close();
        
        System.out.println("Dades escrites!");
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
