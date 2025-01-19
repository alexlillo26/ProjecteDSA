package edu.upc.dsa.models;

public class levels {
    private String ID;
    private String levelData; // Stored as a JSON string

    // ObjectMapper instance for JSON validation
    //@CustomAnnotation("id_exclude")
    //private static final ObjectMapper objectMapper = new ObjectMapper();

    // Constructor
    public levels(String levelName, String levelData) throws IllegalArgumentException {
        this.ID = levelName;
        setLevelData(levelData); // Validate and set levelData
    }
    public levels() {
        this.ID = "";
        this.levelData = "";
    }

    // Getter and Setter for levelName
    public String getID() {
        return ID;
    }

    public void setID(String levelName) {
        this.ID = levelName;
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
                "levelName='" + ID + '\'' +
        ", levelData='" + levelData + '\'' +
        '}';
    }

}
