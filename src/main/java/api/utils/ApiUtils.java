package api.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import utils.LoggerWrapper;

import java.util.Map;

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
            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            logger.info(String.format("Returning %s from json to map",map));
            return map;
        } catch (Exception e){
            logger.error(String.format("Failed to convert Json to Map %s ",e.getMessage()));
            throw new RuntimeException("Failed to convert Json to Map",e);
        }
    }


}
