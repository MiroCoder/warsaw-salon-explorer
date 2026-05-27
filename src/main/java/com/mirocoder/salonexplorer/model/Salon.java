package com.mirocoder.salonexplorer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Salon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String district;
    private String phone;
    private String website;
    private String services;
    private String priceRange;
    private Double rating;
    private Integer reviewCount;

    public Salon(){}

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
    public String getDistrict() {
        return district;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setServices(String services) {
        this.services = services;
    }
    public String getServices() {
        return services;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }
    public String getPriceRange() {
        return priceRange;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
    public Double getRating() {
        return rating;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }
    public Integer getReviewCount() {
        return reviewCount;
    }
}
