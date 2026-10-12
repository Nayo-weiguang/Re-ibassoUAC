package com.ibasso.volume.controller;

import com.ibasso.volume.bean.Advertisement;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface AdvertisementService {
    @GET("update.json")
    Call<List<Advertisement>> getAdvertisementList();
}
