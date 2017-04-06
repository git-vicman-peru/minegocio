package mobapp.vic.services;


import java.util.List;

import mobapp.vic.models.ClieBlog;
import mobapp.vic.models.ClieBlogRev;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IClieBlog {
    @POST("clieblog/savecomment")
    Call<Void> saveComment(@Body ClieBlog qblog);

    @GET("clieblog/{empid}/{prodid}")
    Call<List<ClieBlogRev>> getComments(@Path("empid") int empid, @Path("prodid") int idprod);
}
