package model.room.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import api.MyRetrofit;
import model.room.dao.TemperatureDao;
import model.room.entity.Humidity;
import model.room.entity.Temperature;
import model.room.entity.apiDataModelClimate.Root;
import model.room.roomdatabase.MyRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemperatureRepo {
    private MyRetrofit retrofit;
    private TemperatureDao Dao;


    public TemperatureRepo(Application application){
        retrofit = new MyRetrofit();
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        Dao = db.temperatureDao();
    }

    public void getTemperature(String location){
        String locationCode = "0";
       switch (location){
           case "Horsens":
               locationCode = "06102";
               break;
           case "Aarhus":
               locationCode = "06074";
               break;
           case "Kitsissut":
               locationCode = "04203";
               break;
           case "Kitsissorsuit":
               locationCode = "04208";
               break;
           case "Nuussuaq/Qullisat":
               locationCode = "04214";
               break;
           case "Aasiaat":
               locationCode = "04220";
               break;
           case "Attu":
               locationCode = "04228";
               break;
           case "Sioralik":
               locationCode = "04242";
               break;
           case "Nuuk":
               locationCode = "04250";
               break;
           case "Ukiiviit":
               locationCode = "04253";
               break;
           case "Nunarssuit":
               locationCode = "04266";
               break;
           case "Narsarsuaq/Radiosonde":
               locationCode = "04271";
               break;
           case "Qaqortoq":
               locationCode = "04285";
               break;
           case "Angissoq":
               locationCode = "04253";
               break;
           case "Kap Morris Jesup":
               locationCode = "04301";
               break;
           case "Station Nord":
               locationCode = "04312";
               break;
           case "Henrik Krøyer Holme":
               locationCode = "04313";
               break;
           case "Danmarkshavn":
               locationCode = "04320";
               break;
           case "Daneborg":
               locationCode = "04330";
               break;
           case "Ittoqqortoormiit":
               locationCode = "04339";
               break;
           case "Aputiteeq":
               locationCode = "04351";
               break;
           case "Tasiilaq":
               locationCode = "04360";
               break;
           case "Ikermit":
               locationCode = "04373";
               break;
           case "Ikermiuarssuk":
               locationCode = "04382";
               break;
           case "Ikerasassuaq":
               locationCode = "04390";
               break;
           case "Uggerby":
               locationCode = "05005";
               break;
           case "Nørre Lyngby N":
               locationCode = "05009";
               break;
           case "Lendum":
               locationCode = "05015";
               break;
           case "Voerså Hede":
               locationCode = "05035";
               break;
           case "Møllegård Huse":
               locationCode = "05042";
               break;
           case "Mørkeskov":
               locationCode = "05065";
               break;
           case "Havnø":
               locationCode = "05070";
               break;
           case "Hannerup/Hørby":
               locationCode = "05075";
               break;
           case "Gatten":
               locationCode = "05081";
               break;
           case "Lild Strand":
               locationCode = "05089";
               break;
           case "Nørre Vorupør":
               locationCode = "05095";
               break;
           case "Erslev":
               locationCode = "05105";
               break;
           case "Byrum":
               locationCode = "05031";
               break;
           case "Junget" :
               locationCode= "05109";
               break;
           case "Grønbæk" :
               locationCode= "05135";
               break;
           case "Hald" :
               locationCode= "05140";
               break;
           case "Hevringholm" :
               locationCode= "05150";
               break;
           case "Sibirien" :
               locationCode= "05160";
               break;
           case "Tranebjerg Øst" :
               locationCode= "05165";
               break;
           case "Hov" :
               locationCode= "05169";
               break;
           case "Flensted" :
               locationCode= "05185";
               break;
           case "Gludsted Plantage Nv" :
               locationCode= "05199";
               break;
           case "Nørre Snede" :
               locationCode= "05202";
               break;
           case "Vestbirk" :
               locationCode= "05205";
               break;
           case "Juelsminde" :
               locationCode= "05220";
               break;
           case "Hesselballe" :
               locationCode= "05225";
               break;
           case "Blåhøj Kirkeby" :
               locationCode= "05269";
               break;
           case "Brande" :
               locationCode= "05272";
               break;
           case "Voulund Testfelt P" :
               locationCode= "05276";
               break;
           case "Høgild" :
               locationCode= "05277";
               break;
           case "Hvidbjerg" :
               locationCode= "05290";
               break;
           case "Semb" :
               locationCode= "05296";
               break;
           case "Trans" :
               locationCode= "05300";
               break;
           case "Øby" :
               locationCode= "05305";
               break;
           case "Grønbjerg" :
               locationCode= "05320";
               break;
           case "Skovlund" :
               locationCode= "05329";
               break;
           case "Outrup" :
               locationCode= "05343";
               break;
           case "Holsted" :
               locationCode= "05345";
               break;
           case "Ribe Renseanlæg" :
               locationCode= "05350";
               break;
           case "Bredebro" :
               locationCode= "05355";
               break;
           case "Emmerlev Klev" :
               locationCode= "05365";
               break;
           case "Kliplev" :
               locationCode= "05375";
               break;
           case "Nørreløkke" :
               locationCode= "05381";
               break;
           case "Rangstrup" :
               locationCode= "05395";
               break;
           case "Hajstrup" :
               locationCode= "05400";
               break;
           case "Båring" :
               locationCode= "05406";
               break;
           case "Tørresø" :
               locationCode= "05408";
               break;
           case "H. C. Andersen Airport" :
               locationCode= "05435";
               break;
           case "Bøjden" :
               locationCode= "05440";
               break;
           case "Ulbølle" :
               locationCode= "05450";
               break;
           case "Søndenbro" :
               locationCode= "05455";
               break;
           case "Rudkøbing" :
               locationCode= "05469";
               break;
           case "Rosilde" :
               locationCode= "05499";
               break;
           case "Juelsberg" :
               locationCode= "05505";
               break;
           case "Mulstrup" :
               locationCode= "05510";
               break;
           case "Tåstrup Huse" :
               locationCode= "05529";
               break;
           case "Rye" :
               locationCode= "05537";
               break;
           case "Havnsø" :
               locationCode= "05545";
               break;
           case "Mørkøv Syd" :
               locationCode= "05575";
               break;
           case "Kollekolle" :
               locationCode= "05735";
               break;
           case "Græsted" :
               locationCode= "05880";
               break;
           case "Botanisk Have" :
               locationCode= "05889";
               break;
           case "Livgardens Kaserne" :
               locationCode= "05935";
               break;
           case "Møllebjerggård" :
               locationCode= "05945";
               break;
           case "Orup" :
               locationCode= "05970";
               break;
           case "Køng" :
               locationCode= "05986";
               break;
           case "Nørreby" :
               locationCode= "05994";
               break;
           case "Rødbyhavn" :
               locationCode= "06019";
               break;
           case "Ny Borre" :
               locationCode= "06031";
               break;
           case "Borre" :
               locationCode= "06032";
               break;
           case "Østerlars" :
               locationCode= "06041";
               break;
           case "Silstrup" :
               locationCode= "06049";
               break;
           case "Tylstrup" :
               locationCode= "06051";
               break;
           case "Stenhøj" :
               locationCode= "06052";
               break;
           case "Skagen Fyr" :
               locationCode= "06056";
               break;
           case "Hald Vest" :
               locationCode= "06058";
               break;
           case "Vestervig" :
               locationCode= "06065";
               break;
           case "Thyborøn" :
               locationCode= "06068";
               break;
           case "Mejrup" :
               locationCode= "06072";
               break;
           case "Hvide Sande" :
               locationCode= "06073";
               break;
           case "Ålestrup" :
               locationCode= "06074";
               break;
           case "Års Syd" :
               locationCode= "06079";
               break;
           case "Isenvad" :
               locationCode= "06081";
               break;
           case "Ødum" :
               locationCode= "06082";
               break;
           case "Sletterhage Fyr" :
               locationCode= "06088";
               break;
           case "Kirstinesminde Flyveplads" :
               locationCode= "06093";
               break;
           case "Århus Syd" :
               locationCode= "06096";
               break;
           case "Anholt Fyr" :
               locationCode= "06102";
               break;
           case "Anholt By" :
               locationCode= "06116";
               break;
           case "Anholt Havn" :
               locationCode= "06119";
               break;
           case "Blåvandshuk Fyr" :
               locationCode= "06123";
               break;
           case "Borris" :
               locationCode= "06124";
               break;
           case "Nordby" :
               locationCode= "06126";
               break;
           case "Vester Vedsted" :
               locationCode= "06132";
               break;
           case "Rømø/Juvre" :
               locationCode= "06135";
               break;
           case "Horsens/Bygholm" :
               locationCode= "06136";
               break;
           case "Store Jyndevad" :
               locationCode= "06138";
               break;
           case "Kegnæs Fyr" :
               locationCode= "06141";
               break;
           case "Tvingsbjerg Fyr" :
               locationCode= "06147";
               break;
           case "Assens/Torø" :
               locationCode= "06149";
               break;
           case "Sydfyns Flyveplads" :
               locationCode= "06151";
               break;
           case "Årslev" :
               locationCode= "06154";
               break;
           case "Flakkebjerg" :
               locationCode= "06156";
               break;
           case "Tystofte" :
               locationCode= "06159";
               break;
           case "Langø" :
               locationCode= "06168";
               break;
           case "Abed" :
               locationCode= "06169";
               break;
           case "Gedser Rev Fyrskib" :
               locationCode= "06174";
               break;
           case "Vindebæk Kyst" :
               locationCode= "06181";
               break;
           case "Gedser Fyr" :
               locationCode= "06183";
               break;
           case "Gedser Odde" :
               locationCode= "06184";
               break;
           case "Gedser" :
               locationCode= "06186";
               break;
           case "Omø Fyr" :
               locationCode= "06187";
               break;
           case "Brandelev" :
               locationCode= "06188";
               break;
           case "Holbæk" :
               locationCode= "06193";
               break;
           case "Holbæk Flyveplads" :
               locationCode= "06197";
               break;
           case "Røsnæs Fyr" :
               locationCode= "20000";
               break;
           case "Nakkehoved Fyr" :
               locationCode= "20030";
               break;
           case "Gniben" :
               locationCode= "20055";
               break;
           case "Køge" :
               locationCode= "20085";
               break;
           case "Tessebølle" :
               locationCode= "20228";
               break;
           case "Jægersborg" :
               locationCode= "20279";
               break;
           case "Drogden Fyr" :
               locationCode= "20315";
               break;
           case "Danmarks Meteorologiske Inst." :
               locationCode= "20375";
               break;
           case "Dmi" :
               locationCode= "20400";
               break;
           case "Landbohøjskolen" :
               locationCode= "20552";
               break;
           case "Københavns Toldbod" :
               locationCode= "20561";
               break;
           case "Sjælsmark" :
               locationCode= "20600";
               break;
           case "Hammer Odde Fyr" :
               locationCode= "20670";
               break;
           case "Nexø Vest" :
               locationCode= "21020";
               break;
           case "Klarup" :
               locationCode= "21368";
               break;
           case "Brovst" :
               locationCode= "21430";
               break;
           case "Aggersund" :
               locationCode= "22020";
               break;
           case "Hannerup" :
               locationCode= "22080";
               break;
           case "Skive Lufthavn" :
               locationCode= "23133";
               break;
           case "Flyvestation Karup" :
               locationCode= "23160";
               break;
           case "Hevringsholm" :
               locationCode= "23327";
               break;
           case "Tirstrup" :
               locationCode= "23360";
               break;
           case "Hesseballe" :
               locationCode= "24043";
               break;
           case "Kolding" :
               locationCode= "24102";
               break;
           case "Haderslev" :
               locationCode= "24142";
               break;
           case "Holstebro" :
               locationCode= "24171";
               break;
           case "Tarm" :
               locationCode= "24380";
               break;
           case "Oksvang Andrup" :
               locationCode= "24430";
               break;
           case "Askov" :
               locationCode= "24490";
               break;
           case "Roerslev" :
               locationCode= "25161";
               break;
           case "Højby" :
               locationCode= "25270";
               break;
           case "Kettinge" :
               locationCode= "25339";
               break;
           case "Meteorologisk Institut" :
               locationCode= "26210";
               break;
           case "Roskilde" :
               locationCode= "26340";
               break;
           case "Neble" :
               locationCode= "26358";
               break;
           case "Tjennemarke" :
               locationCode= "26450";
               break;
           case "Nakskov" :
               locationCode= "27008";
               break;
           case "Systofte Skovby Nord" :
               locationCode= "27082";
               break;
           case "Bjørup" :
               locationCode= "28032";
               break;
           case "Vestermarie" :
               locationCode= "28110";
               break;
           case "Narsarsuaq" :
               locationCode= "28240";
               break;
        }
        //Temperature----------------------------------------------------------------------------------------------------------------------

        Call<Root> call = retrofit.api.getClimate("metObs/collections/observation/items?stationId="+locationCode+"&parameterId=temp_dry&period=latest&api-key=14cc5e73-90a5-463b-a221-68e503b2a396");
        call.enqueue(new Callback<Root>(){
            @Override
            public void onResponse (Call <Root> call, Response<Root> response){
                if(!response.body().features.isEmpty()){
                    insert(new Temperature(0,response.body().features.get(0).properties.value));
                }else{
                    insert(new Temperature(0,9999.9));
                }
            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void insert(Temperature obj) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            Dao.insertTemperature(obj);
        });
    }

    //delete all Temperature
    public void emptyTemperatureRepo(){
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            Dao.deleteAll();
        });

    }

    // return a list of all Temperature to the viewmodel
    public LiveData<List<Temperature>> getTemperatureList(){
        return Dao.getTemperatureList();
    }

}
