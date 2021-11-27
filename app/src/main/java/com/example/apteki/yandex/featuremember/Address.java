package com.example.apteki.yandex.featuremember;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("formatted")
    @Expose
    private String formatted;

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    @Override
    public String toString() {
        return "Address{" +
                "formatted='" + formatted + '\'' +
                '}';
    }
}
