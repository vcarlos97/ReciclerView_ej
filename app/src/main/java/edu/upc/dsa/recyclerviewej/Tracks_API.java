package edu.upc.dsa.recyclerviewej;
import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;



public interface Tracks_API {
    @GET("tracks")
    Call<List<Track>> getTracks();

    @PUT("tracks")
    Call<Void> updateTrack (@Body Track track);

    @POST("tracks")
    Call<Track> saveTrack(@Body Track track);

    @DELETE("tracks")
    Call<Void> deleteTrack(@Path("id")String id);
}
