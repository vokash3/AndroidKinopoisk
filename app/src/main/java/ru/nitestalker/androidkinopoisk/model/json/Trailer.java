package ru.nitestalker.androidkinopoisk.model.json;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Trailer {

    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
}
