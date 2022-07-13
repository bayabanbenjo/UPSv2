package com.app.urbanplanting;

public class OrderModel {

    private String imageUrl,plantName,price,totalPrice,nameOfCust,address,phone,orderId;

    public OrderModel(){

    }

    public OrderModel(String imageUrl, String plantName, String price,String totalPrice, String nameOfCust, String address, String phone,String orderId) {
        this.imageUrl = imageUrl;
        this.plantName = plantName;
        this.price = price;
        this.totalPrice = totalPrice;
        this.nameOfCust = nameOfCust;
        this.address = address;
        this.phone = phone;
        this.orderId = orderId;
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

    public String getNameOfCust() {
        return nameOfCust;
    }

    public void setNameOfCust(String nameOfCust) {
        this.nameOfCust = nameOfCust;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
