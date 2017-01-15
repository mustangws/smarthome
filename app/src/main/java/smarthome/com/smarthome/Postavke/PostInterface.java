package smarthome.com.smarthome.Postavke;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostInterface {
    @FormUrlEncoded
    @POST("/send")
    Call<Response> postTime(@Field("interval=") String time);
}

