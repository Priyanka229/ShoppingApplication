package com.example.priyanka.shoppingapplication.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShoppingItem {

    @SerializedName("id")
    @Expose
    private String productId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("exchange_text")
    @Expose
    private String exchangeText;
    @SerializedName("img_url")
    @Expose
    private String imgUrl;
    @SerializedName("offer_list")
    @Expose
    private List<String> offerList = null;
    @SerializedName("details_list")
    @Expose
    private List<String> detailsList = null;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getExchangeText() {
        return exchangeText;
    }

    public void setExchangeText(String exchangeText) {
        this.exchangeText = exchangeText;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<String> getOfferList() {
        return offerList;
    }

    public void setOfferList(List<String> offerList) {
        this.offerList = offerList;
    }

    public List<String> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<String> detailsList) {
        this.detailsList = detailsList;
    }

}