package ru.nitestalker.androidkinopoisk.model.docs;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Movie implements Serializable { // Serializable теперь система может превратить этот класс в поток байт

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
