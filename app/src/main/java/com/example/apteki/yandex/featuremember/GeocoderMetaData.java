package com.example.apteki.yandex.featuremember;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeocoderMetaData {
    @SerializedName("Address")
    @Expose
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "GeocoderMetaData{" +
                "address=" + address +
                '}';
    }
}
