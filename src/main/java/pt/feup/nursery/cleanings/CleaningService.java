package pt.feup.nursery.cleanings;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import pt.feup.nursery.results.Result;

import java.io.IOException;

@Component
public class CleaningService implements InitializingBean {

    //para fazer log dos erros
    private static final Logger logger = LogManager.getLogger(CleaningService.class);
    //LINKS PARA OS SERVICOS
    private final String urlCleaning = "http://cleaning-env.agsbps375n.eu-west-2.elasticbeanstalk.com";
    private final String urlPersonnel ="http://www.mocky.io/v2/5df9ff873600009183bd6909";// mockup !
    //Pedidos Http
    private final static CloseableHttpClient httpClient = HttpClients.createDefault();



    @Override
    public void afterPropertiesSet() throws Exception {

    }


    public Result getNurseriesCleanings() {
        try {
            HttpGet request = new HttpGet(urlCleaning+"/cleanings");
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

    public Result getCleaning(final String id) {

        try {
            HttpGet request = new HttpGet(urlCleaning+"/cleanings/"+id);
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
// VERIFICAR DAQUI PARA BAIXO
    public Result deleteCleaning(final String id) {
        try {
            HttpDelete request = new HttpDelete(urlCleaning+"/cleanings/"+id);
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

    public Result addCleaning(final String cleaninginfo) {
        try {
            HttpPost request = new HttpPost(urlCleaning+"/cleanings");
            StringEntity entity = new StringEntity(cleaninginfo);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 201 && statusCode!= 200) {
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

    public Result updateCleaning(final String id, final String cleaninginfo) {
        try {
            HttpPut request = new HttpPut(urlCleaning+"/cleanings/"+id);
            StringEntity entity = new StringEntity(cleaninginfo);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 201 && statusCode!= 200) {
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

    public Result getWorkersFromCleaning(final String id) {
        try {
            HttpGet request = new HttpGet(urlCleaning+"/cleanings/"+id+"/workers");
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

    public Result getAvailableWorkers() {
        try {
            HttpGet request = new HttpGet(urlCleaning+"/cleanings/availableworkers");
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

    /*public Result createCleaningWorker(final String workerinfo) {
        try {
            HttpPost request = new HttpPost(urlCleaning+"/workers");
            StringEntity entity = new StringEntity(workerinfo);
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
    }*/

    public Result addWorkerToCleaning(final String id, final String body ) {
        try {
            HttpPost request = new HttpPost(urlCleaning+"/cleanings/"+id+"/workers");
            StringEntity entity = new StringEntity(body);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 201 && statusCode!= 200) {
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

    public Result getStock(final String id) {
        try {
            HttpGet request = new HttpGet(urlCleaning+"/cleanings/"+id+"/products");
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

    public Result requestStock(final String id, final String requeststock) {
        try {
            HttpPost request = new HttpPost(urlCleaning+"/cleanings/"+id+"/products");
            StringEntity entity = new StringEntity(requeststock);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 201 && statusCode!= 200) {
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

    public Result consumeStock(final String id, final String body) {
        try {
            HttpPut request = new HttpPut(urlCleaning+"/cleanings/"+id);
            StringEntity entity = new StringEntity(body);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 201 && statusCode!= 200) {
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

    public Result notifications(final String body) {
        try {
            HttpPost request = new HttpPost(urlCleaning+"/products/notifications");
            StringEntity entity = new StringEntity(body);
            request.setEntity(entity);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode != 201 && statusCode!= 200) {
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

    public Result getEmployees() {
        try {
            HttpGet request = new HttpGet(urlPersonnel+"/employees");
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
}
