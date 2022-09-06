package ru.nitestalker.androidkinopoisk.model.docs;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class Rating implements Serializable {

    @SerializedName("kp")
    private Double kp;

}
