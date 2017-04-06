package mobapp.vic.services;

import mobapp.vic.models.Cliente;
import mobapp.vic.models.UserLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by VicDeveloper on 3/8/2017.
 */

public interface IUserLogin {

    @POST("client/userlogin")
    Call<Cliente> checkUser(@Body UserLogin quser);
}
