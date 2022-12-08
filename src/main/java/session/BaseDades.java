/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package session;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.domain.Authorization;
import com.influxdb.client.domain.Bucket;
import com.influxdb.client.domain.BucketRetentionRules;
import com.influxdb.client.domain.Organization;
import com.influxdb.client.domain.Permission;
import com.influxdb.client.domain.PermissionResource;
import java.util.Arrays;

/**
 *
 * @author oriol
 */
public class BaseDades {
    
    private int idMaquina;
    
    private static String findToken() throws Exception {

        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086",
                "admin", "bitnami123".toCharArray());

        String token = influxDBClient.getAuthorizationsApi()
                .findAuthorizations()
                .stream()
                .filter(authorization -> authorization.getPermissions().stream()
                        .map(Permission::getResource)
                        .anyMatch(resource ->
                                resource.getType().equals(PermissionResource.TYPE_ORGS) &&
                                        resource.getId() == null &&
                                        resource.getOrgID() == null))
                .findFirst()
                .orElseThrow(IllegalStateException::new).getToken();

        influxDBClient.close();

        return token;
    }
    
    //Crea una Organizació a InfluxDB per a cada compte
    public void creaDataBase() throws Exception{
        //InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token);
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", findToken().toCharArray());

        Organization org = influxDBClient.getOrganizationsApi().createOrganization("Maquina_" + System.currentTimeMillis());

        String MaquinaId =  org.getId();
        System.out.println("\n El id de la Organització és "+MaquinaId);
        creaBucketDreta(org);
        creaBucketEsquerra(org);
 
    }
    
    public void creaBucketDreta(Organization org) throws Exception{ 
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", findToken().toCharArray());

        //
        // Create bucket "iot_bucket" with data retention set to 3,600 seconds
        //
        BucketRetentionRules retention = new BucketRetentionRules();
        retention.setEverySeconds(3600*24*30*6);

        //Bucket bucket = influxDBClient.getBucketsApi().createBucket("iot-bucket", retention, "12bdc4164c2e8141");
        Bucket bucketDreta = influxDBClient.getBucketsApi().createBucket("menjadora_dreta", retention, org.getId());
        
        // Create access token to Dreta
        PermissionResource resource = new PermissionResource();
        resource.setId(bucketDreta.getId());
        resource.setOrgID(org.getId());
        resource.setType(PermissionResource.TYPE_BUCKETS);

        // Read permission
        Permission read = new Permission();
        read.setResource(resource);
        read.setAction(Permission.ActionEnum.READ);

        // Write permission
        Permission write = new Permission();
        write.setResource(resource);
        write.setAction(Permission.ActionEnum.WRITE);

        Authorization authorization = influxDBClient.getAuthorizationsApi()
                .createAuthorization(org.getId(), Arrays.asList(read, write));

        //
        // Created token that can be use for writes to "iot_bucket"
        //
        String tokenDreta = authorization.getToken();
        System.out.println("Token Dreta: " + tokenDreta);

        influxDBClient.close();
    }
    
    
    
    public void creaBucketEsquerra(Organization org) throws Exception{ 
        InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", findToken().toCharArray());

        //
        // Create bucket "iot_bucket" with data retention set to 3,600 seconds
        //
        BucketRetentionRules retention = new BucketRetentionRules();
        retention.setEverySeconds(3600*24*30*6);

        //Bucket bucket = influxDBClient.getBucketsApi().createBucket("iot-bucket", retention, "12bdc4164c2e8141");
        Bucket bucketDreta = influxDBClient.getBucketsApi().createBucket("menjadora_esquerra", retention, org.getId());
        
        // Create access token to Dreta
        PermissionResource resource = new PermissionResource();
        resource.setId(bucketDreta.getId());
        resource.setOrgID(org.getId());
        resource.setType(PermissionResource.TYPE_BUCKETS);

        // Read permission
        Permission read = new Permission();
        read.setResource(resource);
        read.setAction(Permission.ActionEnum.READ);

        // Write permission
        Permission write = new Permission();
        write.setResource(resource);
        write.setAction(Permission.ActionEnum.WRITE);

        Authorization authorization = influxDBClient.getAuthorizationsApi()
                .createAuthorization(org.getId(), Arrays.asList(read, write));

        //
        // Created token that can be use for writes to "iot_bucket"
        //
        String tokenEsquerra = authorization.getToken();
        System.out.println("TokenEsquerra: " + tokenEsquerra);

        influxDBClient.close();
    }
        
        
}
