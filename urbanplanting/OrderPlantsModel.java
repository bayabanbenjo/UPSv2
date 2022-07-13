package com.app.urbanplanting;

public class OrderPlantsModel {

    private String imageUrl,plantName,price,quantPrice,description,water,sunlight,temperature;

    public OrderPlantsModel(){

    }

    public OrderPlantsModel(String imageUrl, String plantName, String price,String quantPrice, String description, String water, String sunlight, String temperature) {
        this.imageUrl = imageUrl;
        this.plantName = plantName;
        this.price = price;
        this.quantPrice = quantPrice;
        this.description = description;
        this.water = water;
        this.sunlight = sunlight;
        this.temperature = temperature;
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

    public String getQuantPrice() {
        return quantPrice;
    }

    public void setQuantPrice(String quantPrice) {
        this.quantPrice = quantPrice;
    }
}
