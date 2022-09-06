package ru.nitestalker.androidkinopoisk.model.docs;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class Poster implements Serializable {

    @SerializedName("url")
    private String url;

}
