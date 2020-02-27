package pt.feup.nursery.Nurseries;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.feup.nursery.authentication.Auth;
import pt.feup.nursery.authentication.Token;
import pt.feup.nursery.kafka.producer.Sender;
import pt.feup.nursery.results.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class NurseryDaoService implements InitializingBean {
    //para fazer log dos erros
    private static final Logger logger = LogManager.getLogger(NurseryDaoService.class);
    //Pedidos Http
    private final static CloseableHttpClient httpClient = HttpClients.createDefault();
    //links para os serviços internos
    private final String mockup = "http://www.mocky.io/v2/5dd82554310000b77b055d95";
    private final String urlNurseries = "http://nurseries.eu-west-2.elasticbeanstalk.com";
    private final String urlPharmacy = "";
    //links para serviços externos
    private final String urlAuth = "http://sts.luispinto.rocks";
    private static Token token;

    @Autowired
    private Sender sender;

    @Override
    public void afterPropertiesSet() {
    }

    @HystrixCommand
    public Result   getNurseries() {
        try {
            HttpGet request = new HttpGet(urlNurseries+"/nurseries");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);
            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }
    }

    @HystrixCommand
    public Result getNursery(final String id) {
        try {
            HttpGet request = new HttpGet(urlNurseries+"/nurseries/"+id);
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);
            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }
    }

    @HystrixCommand
    public Result getNurseryTypes() {
        try {
            HttpGet request = new HttpGet(urlNurseries+"/nurseries/types");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);

            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }

    }

    @HystrixCommand
    public Result deleteNursery(final String id) {
        try {
            HttpDelete request = new HttpDelete(urlNurseries+"/nurseries/"+id);
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);
            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }
    }

    @HystrixCommand
    public Result createNursery(final String nurseryInfo) {
        try {
            HttpPost request = new HttpPost(urlNurseries+"/nurseries");
           // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            StringEntity entity = new StringEntity(nurseryInfo);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 201) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);

            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }

    }

    @HystrixCommand
    public Result updateNursery(final String id, final String nurseryInfo) {
        try {
            HttpPut request = new HttpPut(urlNurseries+"/nurseries/"+id);
            StringEntity entity = new StringEntity(nurseryInfo);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 201) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);
            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }

    }

    @HystrixCommand
    public Result allocatePatientToNursery(final String patientInfo) {
        try {
            HttpPost request = new HttpPost(urlNurseries+"/patients");
            // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            StringEntity entity = new StringEntity(patientInfo);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200 && statusCode != 201) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);
            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }

    }

    @HystrixCommand
    public Result dischargePatient(final String pid) {
        try {
            HttpDelete request = new HttpDelete(urlNurseries+"/patients/"+pid);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);
            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }

    }

    @HystrixCommand
    public Result getNurserySchedule(final String id) {
        try {
            HttpGet request = new HttpGet(urlNurseries+"/nurseries/"+id+"/schedule");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);
            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }

    }

    @HystrixCommand
    public Result allocateScheduleToNursery(final String id, final String scheduleInfo) {
        try {
            HttpPut request = new HttpPut(urlNurseries+"/nurseries/"+id+"/schedule");
            // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            StringEntity entity = new StringEntity(scheduleInfo);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200 || statusCode != 201) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);
            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }

    }

    @HystrixCommand
    public Result updatePatientTreatments(final String pid, final String treatmentsInfo) {
        try {
            HttpPut request = new HttpPut(urlNurseries+"/patients/treatments/"+pid);
            // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            StringEntity entity = new StringEntity(treatmentsInfo);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                System.out.println(apiOutPut);
                return Result.success(apiOutPut);

            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }
    }

    @HystrixCommand
    public Result getMedicines(final String id) {
        try {
            HttpGet request = new HttpGet(urlNurseries+"/nurseries/"+id+"/medicines");
            // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                return Result.success(apiOutPut);

            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }
    }

    @HystrixCommand
    public Result requestMedine(final String nurseryID, final String medicineRequest) {
        // FALTA POR URL DO ENDPOINT PARA FAZER REQUEST
        JSONObject json = new JSONObject("medicineRequest");
        JSONObject requestmedicine = new JSONObject();
        JSONArray orderItems = new JSONArray();
        JSONObject orderItem = new JSONObject().put("orderItemID",json.getString("medicineId")).put("quantity",json.getString("amount"));
        orderItems.put(orderItem);
        requestmedicine.put("orderItemList",orderItems).put("serviceID",nurseryID).put("withDeliveryService", true);
        try {
            HttpPost request = new HttpPost(urlPharmacy+"/Orders");

            StringEntity entity = new StringEntity(medicineRequest);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer "+token.getJwtToken());

            // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                return Result.success(apiOutPut);

            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }
        //STAND-BY KAFKA
        /* sender.send(medicineRequest);
        return Result.success("Message Sended to Kafka");*/
    }

    @HystrixCommand
    public Result removeMedicineFromNurseryStorage(final String nurseryID, final String medicineInfo) {
        try {
            HttpDelete request = new HttpDelete(urlNurseries+"/nurseries/"+nurseryID+"/medicines");
            // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                return Result.success(apiOutPut);

            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }
        //STAND-BY KAFKA
        /*
        sender.send(medicineInfo);
        return "Remove a certain medicine from stock";*/
    }

    @HystrixCommand
    public Result notifyMedicineAvailable(final String medicineRequest) {
        try {
            HttpPost request = new HttpPost(urlNurseries+"/medicines/notifications");
            // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            StringEntity entity = new StringEntity(medicineRequest);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200 || statusCode != 201) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                return Result.success(apiOutPut);
            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }

    }
    @HystrixCommand
    public Result getJwtToken(){
        try {
            HttpPost request = new HttpPost(urlAuth+"/connect/token");
            //Headers definition
            request.setHeader("Accept","application/json");
            request.setHeader("Content-Type","application/x-www-form-urlencoded");
            request.setHeader("Cache-Control","application/x-www-form-urlencoded");
            //scopes and clientID & secret
            List<NameValuePair> tokenCredentials = new ArrayList<NameValuePair>();
            tokenCredentials.add(new BasicNameValuePair("scope","pharmacy.api personnel.api patients.api suppliers.api"));
            tokenCredentials.add(new BasicNameValuePair("grant_type","client_credentials"));
            tokenCredentials.add(new BasicNameValuePair("client_id","NurserySystem"));
            tokenCredentials.add(new BasicNameValuePair("client_secret", Auth.secretKey));
            request.setEntity(new UrlEncodedFormEntity(tokenCredentials, Consts.UTF_8));

            try(CloseableHttpResponse response = httpClient.execute(request)){
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                token = new Token(new JSONObject(apiOutPut));
                //System.out.println(auth.getBodyJWT(json.getString("access_token")));
                return Result.success(apiOutPut);
            }
        } catch (IOException e){
            logger.error(e);
            return Result.failure(e);
        }
    }

    private void tokenStatusVerification(){
        //Token Expirado
        if(token.getJwtStatus() == false || token == null) {
            this.getJwtToken();
        }
        //Token Valido
    }

    public Result getNurses(final String id) {
        try {
            HttpGet request = new HttpGet(urlNurseries+"/nurseries/"+id+"/nurses");
            // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                return Result.success(apiOutPut);

            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }
    }

    public Result getAvailableNurses() {
        try {
            HttpGet request = new HttpGet(urlNurseries+"/nurses/availablenurses");
            // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 200) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                return Result.success(apiOutPut);

            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }
    }

    public Result addNurses(final String id, final String body) {
        try {
            HttpPost request = new HttpPost(urlNurseries+"/nurseries/"+id+"/nurse");
            // passar a informação da nursery para uma stringEntity_para enviar para o serviço
            StringEntity entity = new StringEntity(body);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 201) {
                    throw new RuntimeException("Failed with HTTP error code : " + statusCode);
                }
                HttpEntity httpEntity = response.getEntity();
                String apiOutPut = EntityUtils.toString(httpEntity);
                return Result.success(apiOutPut);

            }
        } catch (IOException e) {
            logger.error(e);
            return Result.failure(e);
        }
    }
}
