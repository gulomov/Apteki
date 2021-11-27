package com.example.apteki.yandex;

import com.example.apteki.yandex.featuremember.FeatureMember;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GeoObjectCollection {
    @SerializedName("featureMember")
    @Expose
    private List<FeatureMember> featureMember = null;

    public List<FeatureMember> getFeatureMember() {
        return featureMember;
    }

    public void setFeatureMember(List<FeatureMember> featureMember) {
        this.featureMember = featureMember;
    }

    @Override
    public String toString() {
        return "GeoObjectCollection{" +
                "featureMember=" + featureMember +
                '}';
    }
}
