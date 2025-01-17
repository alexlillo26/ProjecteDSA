package edu.upc.dsa.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import edu.upc.dsa.annotations.CustomAnnotation;

public class levels {
    private String levelName;
    private String levelData; // Stored as a JSON string

    // ObjectMapper instance for JSON validation
    //@CustomAnnotation("id_exclude")
    //private static final ObjectMapper objectMapper = new ObjectMapper();

    // Constructor
    public levels(String levelName, String levelData) throws IllegalArgumentException {
        this.levelName = levelName;
        setLevelData(levelData); // Validate and set levelData
    }

    // Getter and Setter for levelName
    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    // Getter and Setter for levelData
    public String getLevelData() {
        return levelData;
    }

    public void setLevelData(String levelData) throws IllegalArgumentException {
        //if (isValidJson(levelData)) {
            this.levelData = levelData;
        //} else {
        //    throw new IllegalArgumentException("Invalid JSON format for levelData.");
        //}
    }

//    // Method to validate JSON
//    private boolean isValidJson(String jsonString) {
//        try {
//            JsonNode jsonNode = objectMapper.readTree(jsonString);
//            return jsonNode != null;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    @Override
    public String toString() {
        return "Level{" +
                "levelName='" + levelName + '\'' +
                ", levelData='" + levelData + '\'' +
                '}';
    }

}