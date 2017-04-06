package mobapp.vic.services;

import mobapp.vic.models.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by VicDeveloper on 3/7/2017.
 */

public interface ICliente {
    @POST("client/keepclient")
    Call<Cliente> saveCliente(@Body Cliente qclient);

    @POST("client/editclient")
    Call<Boolean> updateCliente(@Body Cliente qclient);
}
