package ch.ost.group20.speedcamerareminder.network;

import ch.ost.group20.speedcamerareminder.entity.SpeedCamera;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("/")
    Call<List<SpeedCamera>> getSpeedCameras();

}
