package smarthome.com.smarthome.Main;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface SenzoriInterface {
    @GET("sensors/")
    Call<List<Senzori>> getAllSenzori();
}
