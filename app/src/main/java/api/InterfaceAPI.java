package api;



import java.util.List;

import model.non_room_classes.Recommendations;
import model.room.entity.apiDataModelClimate.Root;
import model.room.entity.apiDataModelElectricity.RootElectricity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface InterfaceAPI {

    String key = "api-key=14cc5e73-90a5-463b-a221-68e503b2a396";
    //THOSE ARE EXAMPLES
    @GET
    Call<Root> getClimate(@Url String url);
    @GET
    Call<RootElectricity> getElectricity(@Url String url);
    @GET
    Call<List<Recommendations>> getRecommendations(@Url String url);
}
