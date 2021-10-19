package api;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface InterfaceAPI {

    String key = "api-key=14cc5e73-90a5-463b-a221-68e503b2a396";
    //THOSE ARE EXAMPLES
    @GET
    Call<String> getTemp(@Url String url);
    /*
            @Path(value = "location", encoded = true) String location,
            @Path(value = "period", encoded = true) String period
    );

     */

}
