package com.example.game_ing;

public class ImageUploadInfo {


    public String description;

    public String price;

    public String address;

    public String phone;


    public String imageURL;

    public ImageUploadInfo() {

    }

    public ImageUploadInfo(String Description, String Price, String Address, String Phone, String ImageURL) {

        description = Description;
        price = Price;
        address = Address;
        phone = Phone;
        imageURL = ImageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        description = Description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String Price) {
        price = Price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String Address) {
        address = Address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String Phone) {
        phone = Phone;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String ImageURL) {
        imageURL = ImageURL;
    }
}
