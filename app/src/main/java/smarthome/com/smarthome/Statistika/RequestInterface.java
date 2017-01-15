package smarthome.com.smarthome.Statistika;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RequestInterface {

    @GET
    Call<Senzor> getJSON(@Url String url);
}
