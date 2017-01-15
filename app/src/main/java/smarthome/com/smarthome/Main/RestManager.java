package smarthome.com.smarthome.Main;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private SenzoriInterface mSenzoriService;

    public SenzoriInterface getSenzoriService() {
        if(mSenzoriService==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MainActivity.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mSenzoriService = retrofit.create(SenzoriInterface.class);

        }

        return mSenzoriService;

    }
}
