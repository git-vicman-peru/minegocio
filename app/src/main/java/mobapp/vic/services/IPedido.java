package mobapp.vic.services;

import java.util.List;

import mobapp.vic.models.Pedido;
import mobapp.vic.models.PedidoFix;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by VicDeveloper on 3/10/2017.
 */

public interface IPedido {
    @POST("pedidos/envio")
    Call<Boolean> hacerPedido(@Body Pedido qped);

    @GET("pedidos/seeall/{empid}/{clieid}")
    Call<List<PedidoFix>> leerTodoPedido(@Path("empid") int empId, @Path("clieid") int clieId);
}
