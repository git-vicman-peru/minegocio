package mobapp.vic.services;

import mobapp.vic.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by VicDeveloper on 2/3/2017.
 */

public interface IUsuario {

    @FormUrlEncoded
    @POST("logincheckfem")
    Call<Usuario> getValidUserA(@Field("usuario") String username, @Field("clave") String pass);

    @POST("logincheck")
    Call<Usuario>getValidUserB(@Body Usuario quser);
    //void getUsuario(Callback<List<Usuario>> callback);

    /*
    @Multipart
    @PUT("user/photo")
    Call<User> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);

    @Headers("Cache-Control: max-age=640000")
    @GET("widget/list")
    Call<List<Widget>> widgetList();

    @Headers({
    "Accept: application/vnd.github.v3.full+json",
    "User-Agent: Retrofit-Sample-App"
    })
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("user")
    Call<User> getUser(@Header("Authorization") String authorization)
    */
}
