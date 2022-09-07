package ru.nitestalker.androidkinopoisk.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import ru.nitestalker.androidkinopoisk.model.json.TrailersList;

@Data
public class TrailersResponse {

    @SerializedName("trailers")
    private TrailersList trailersList;


}
