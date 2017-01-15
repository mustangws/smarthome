package smarthome.com.smarthome.Main;


import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface SenzoriInterface {
    @GET("sensors/")
    Call<List<Senzori>> getAllSenzori();
}
