package mobapp.vic.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by VicDeveloper on 3/4/2017.
 */

public interface ICatGrupo {
    @GET("prods/specific/grupos/{idemp}")
    Call<List<String>> getGrupo(@Path("idemp") int idemp);

}
