package com.example.apteki.yandex.featuremember;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoObject {
    @SerializedName("metaDataProperty")
    @Expose
    private MetaDataProperty_ metaDataProperty;

    public MetaDataProperty_ getMetaDataProperty() {
        return metaDataProperty;
    }

    public void setMetaDataProperty(MetaDataProperty_ metaDataProperty) {
        this.metaDataProperty = metaDataProperty;
    }

    @Override
    public String toString() {
        return "GeoObject{" +
                "metaDataProperty=" + metaDataProperty +
                '}';
    }
}
