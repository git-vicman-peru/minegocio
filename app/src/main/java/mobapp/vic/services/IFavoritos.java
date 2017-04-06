package mobapp.vic.services;

import java.util.List;

import mobapp.vic.models.Producto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by VicDeveloper on 3/14/2017.
 */

public interface IFavoritos {
    @GET("prods/specific/fav/{empid}/{prodids}")
    Call<List<Producto>> getFavoritosList(@Path("empid") int empid,@Path("prodids") String ssempIds);

}
