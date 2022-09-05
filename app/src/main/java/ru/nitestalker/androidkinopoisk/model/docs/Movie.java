package ru.nitestalker.androidkinopoisk.model.docs;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Movie {

    @SerializedName("id")
    private int id;
    @SerializedName("description")
    private String description;
    @SerializedName("name")
    private String name;
    @SerializedName("year")
    private int year;
    @SerializedName("poster")
    private Poster poster;
    @SerializedName("rating")
    private Rating rating;

}
