package mobapp.vic.services;

import java.util.List;

import mobapp.vic.models.Producto;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by VicDeveloper on 2/22/2017.
 */

public interface IProducto {
    /*
    @FormUrlEncoded
    @POST("prods/specific/srch")
    Call<List<Producto>>searchBy(@Field("prodkey") String criteria, @Field("empId") int idemp);
    */
    @GET("prods/specific/list/{idemp}/{grupo}")
    Call<List<Producto>>getProdByEmpGrupo(@Path("idemp") int idemp, @Path("grupo") String grupo);

    @GET("prods/specific/list/{idemp}/{grupo}/{criterion}")
    Call<List<Producto>>getProdEmpGrupoCrit(@Path("idemp") int idemp, @Path("grupo") String grupo, @Path("criterion") String crit);
}
