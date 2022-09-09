package ru.nitestalker.androidkinopoisk.model.docs;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
@Entity(tableName = "favourite_movies")
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
