package api.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import utils.LoggerWrapper;

import java.util.Map;
import java.util.function.Supplier;

public class ApiUtils {

    LoggerWrapper logger;

    public ApiUtils(Class<?> callerClass){
        logger = new LoggerWrapper(callerClass);
    }

    public String mapToJson(Map<String, Object> map){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String json =  objectMapper.writeValueAsString(map);
            logger.info(String.format("Returning %s from map to json",json));
            return json;
        } catch (Exception e){
            logger.error(String.format("Failed to convert Map to Json %s ",e.getMessage()));
            throw new RuntimeException("Failed to convert Map to Json", e);
        }
    }

    public Map<String, Object> jsonToMap(String json){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
            Map<String, Object> map = objectMapper.readValue(json, typeRef);
            logger.info(String.format("Returning %s from json to map",map));
            return map;
        } catch (Exception e){
            logger.error(String.format("Failed to convert Json to Map %s ",e.getMessage()));
            throw new RuntimeException("Failed to convert Json to Map",e);
        }
    }

    public <T> T responseToClass(Response response, Class<T> clazz)  {
        try{
            logger.info(String.format("Converting response JSON to class: %s", clazz.getSimpleName()));
            ObjectMapper objectMapper = new ObjectMapper();
            T obj = objectMapper.readValue(response.asString(),clazz);
            logger.info(String.format("Successfully converted JSON to %s", clazz.getSimpleName()));
            return obj;
        } catch (JsonProcessingException e){
            logger.error(String.format("Failed to convert JSON to %s %s", clazz.getSimpleName(), e));
            throw new RuntimeException("Failed to convert JSON to " + clazz.getSimpleName(), e);
        }
    }

    public <T> T responseToClass(Response response, TypeReference<T> typeRef) {
        try {
            logger.info("Converting response JSON to generic type");
            ObjectMapper objectMapper = new ObjectMapper();
            T obj = objectMapper.readValue(response.asString(), typeRef);
            logger.info("Successfully converted JSON to generic type");
            return obj;
        } catch (JsonProcessingException e) {
            logger.error(String.format("Failed to convert JSON to generic type: " + e.getMessage(), e));
            throw new RuntimeException("Failed to convert JSON to generic type", e);
        }
    }

    public Response executeWithTokenRefresh(Supplier<Response> apiCall, Runnable tokenRefresher){
        Response response = apiCall.get();
        if(response.getStatusCode() == 401){
            tokenRefresher.run();
            response = apiCall.get();
        }
        return response;
    }

}
