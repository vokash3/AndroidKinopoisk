package ru.nitestalker.androidkinopoisk.model.docs;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Poster {

    @SerializedName("url")
    private String url;

}
