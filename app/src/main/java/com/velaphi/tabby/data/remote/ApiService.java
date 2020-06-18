package com.velaphi.tabby.data.remote;

import com.velaphi.tabby.data.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("images/get?format=json&results_per_page=100&size=small&type=png")
    Call<List<Image>> getListOfImages();
}
