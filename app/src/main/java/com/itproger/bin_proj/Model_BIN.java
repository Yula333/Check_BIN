package com.itproger.bin_proj;

public class Model_BIN {
    private String check_BIN, scheme, type, brand, country_alpha2, country_name, bank_name, bank_url, bank_phone, bank_city, time;

    public Model_BIN() {
    }

    public Model_BIN(String check_BIN, String scheme, String type, String brand, String country_alpha2, String country_name, String bank_name, String bank_url, String bank_phone, String bank_city, String time) {
        this.check_BIN = check_BIN;
        this.scheme = scheme;
        this.type = type;
        this.brand = brand;
        this.country_alpha2 = country_alpha2;
        this.country_name = country_name;
        this.bank_name = bank_name;
        this.bank_url = bank_url;
        this.bank_phone = bank_phone;
        this.bank_city = bank_city;
        this.time = time;
    }

    public String getCheck_BIN() {
        return check_BIN;
    }

    public void setCheck_BIN(String check_BIN) {
        this.check_BIN = check_BIN;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCountry_alpha2() {
        return country_alpha2;
    }

    public void setCountry_alpha2(String country_alpha2) {
        this.country_alpha2 = country_alpha2;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_url() {
        return bank_url;
    }

    public void setBank_url(String bank_url) {
        this.bank_url = bank_url;
    }

    public String getBank_phone() {
        return bank_phone;
    }

    public void setBank_phone(String bank_phone) {
        this.bank_phone = bank_phone;
    }

    public String getBank_city() {
        return bank_city;
    }

    public void setBank_city(String bank_city) {
        this.bank_city = bank_city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
