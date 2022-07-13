package com.app.urbanplanting;

public class PlantsModel {

    private String imageUrl,plantName,price,description,water,sunlight,temperature,key;

    public PlantsModel (){

    }

    public PlantsModel(String imageUrl, String plantName, String price, String description, String water, String sunlight, String temperature,String key) {
        this.imageUrl = imageUrl;
        this.plantName = plantName;
        this.price = price;
        this.description = description;
        this.water = water;
        this.sunlight = sunlight;
        this.temperature = temperature;
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public String getSunlight() {
        return sunlight;
    }

    public void setSunlight(String sunlight) {
        this.sunlight = sunlight;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
