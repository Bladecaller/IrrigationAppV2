package view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.SEP7_IrrigationApp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import model.room.entity.Account;
import viewmodel.AccountRepoViewModel;
import viewmodel.HumidityViewModel;
import viewmodel.PrecipitationViewModel;
import viewmodel.TemperatureViewModel;

public class SettingsActivity extends AppCompatActivity {
    EditText location;
    Spinner spinner,spinnerPrice,spinnerLum;
    Button buttonSet,buttonSetPrice,buttonSetLuminosity;
    Button buttonLogOut;
    Toolbar toolbar;
    private HumidityViewModel humidityViewModel;
    private TemperatureViewModel temperatureViewModel;
    private PrecipitationViewModel precipitationViewModel;
    private AccountRepoViewModel accountVM;
    private Account acc;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        buttonSet = findViewById(R.id.location_button);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference("users");
        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        precipitationViewModel = new ViewModelProvider(this).get(PrecipitationViewModel.class);
        accountVM = new ViewModelProvider(this).get(AccountRepoViewModel.class);
        buttonSetPrice = findViewById(R.id.price_button);
        buttonLogOut = findViewById(R.id.logOut_button);

        spinner = findViewById(R.id.spinnerLocation);
        ArrayList<String> arrayListLocation = new ArrayList<>();
        arrayListLocation.add("Horsens");
        arrayListLocation.add("Aarhus");
        arrayListLocation.add("Attu");
        arrayListLocation.add("Nuuk");
        arrayListLocation.add("Kitsissut");
        arrayListLocation.add("Kitsissorsuit");
        arrayListLocation.add("Qullissat");
        arrayListLocation.add("Nuussuaq");
        arrayListLocation.add("Aasiaat");
        arrayListLocation.add("Kitsissut");
        arrayListLocation.add("Sioralik");
        arrayListLocation.add("Ukiiviit");
        arrayListLocation.add("Nunarssuit");
        arrayListLocation.add("Narsarsuaq Radiosonde");
        arrayListLocation.add("Qaqortoq");
        arrayListLocation.add("Angissoq");
        arrayListLocation.add("Kap Morris Jesup");
        arrayListLocation.add("Station Nord");
        arrayListLocation.add("Henrik Krøyer Holme");
        arrayListLocation.add("Danmarkshavn");
        arrayListLocation.add("Daneborg");
        arrayListLocation.add("Ittoqqortoormiit");
        arrayListLocation.add("Aputiteeq");
        arrayListLocation.add("Tasiilaq");
        arrayListLocation.add("Ikermit");
        arrayListLocation.add("Ikermiuarssuk");
        arrayListLocation.add("Ikerasassuaq");
        arrayListLocation.add("Uggerby");
        arrayListLocation.add("Nørre Lyngby N");
        arrayListLocation.add("Lendum");
        arrayListLocation.add("Byrum");
        arrayListLocation.add("Voerså Hede");
        arrayListLocation.add("Møllegård Huse");
        arrayListLocation.add("Mørkeskov");
        arrayListLocation.add("Havnø");
        arrayListLocation.add("Hørby");
        arrayListLocation.add("Hannerup");
        arrayListLocation.add("Gatten");
        arrayListLocation.add("Aggersund");
        arrayListLocation.add("Lild Strand");
        arrayListLocation.add("Nørre Vorupør");
        arrayListLocation.add("Erslev");
        arrayListLocation.add("Junget");
        arrayListLocation.add("Grønbæk");
        arrayListLocation.add("Hald");
        arrayListLocation.add("Hevringholm");
        arrayListLocation.add("Sibirien");
        arrayListLocation.add("Tranebjerg Øst");
        arrayListLocation.add("Hov");
        arrayListLocation.add("Flensted");
        arrayListLocation.add("Gludsted Plantage Nv");
        arrayListLocation.add("Nørre Snede");
        arrayListLocation.add("Vestbirk");
        arrayListLocation.add("Juelsminde");
        arrayListLocation.add("Hesselballe");
        arrayListLocation.add("Blåhøj Kirkeby");
        arrayListLocation.add("Brande");
        arrayListLocation.add("Voulund Testfelt P");
        arrayListLocation.add("Høgild");
        arrayListLocation.add("Hvidbjerg");
        arrayListLocation.add("Semb");
        arrayListLocation.add("Trans");
        arrayListLocation.add("Øby");
        arrayListLocation.add("Grønbjerg");
        arrayListLocation.add("Skovlund");
        arrayListLocation.add("Outrup");
        arrayListLocation.add("Holsted");
        arrayListLocation.add("Ribe Renseanlæg");
        arrayListLocation.add("Bredebro");
        arrayListLocation.add("Emmerlev Klev");
        arrayListLocation.add("Kliplev");
        arrayListLocation.add("Nørreløkke");
        arrayListLocation.add("Rangstrup");
        arrayListLocation.add("Hajstrup");
        arrayListLocation.add("Båring");
        arrayListLocation.add("Tørresø");
        arrayListLocation.add("H. C. Andersen Airport");
        arrayListLocation.add("Bøjden");
        arrayListLocation.add("Ulbølle");
        arrayListLocation.add("Søndenbro");
        arrayListLocation.add("Rudkøbing");
        arrayListLocation.add("Rosilde");
        arrayListLocation.add("Juelsberg");
        arrayListLocation.add("Mulstrup");
        arrayListLocation.add("Tåstrup Huse");
        arrayListLocation.add("Rye");
        arrayListLocation.add("Havnsø");
        arrayListLocation.add("Mørkøv Syd");
        arrayListLocation.add("Kollekolle");
        arrayListLocation.add("Græsted");
        arrayListLocation.add("Botanisk Have");
        arrayListLocation.add("Livgardens Kaserne");
        arrayListLocation.add("Møllebjerggård");
        arrayListLocation.add("Orup");
        arrayListLocation.add("Køng");
        arrayListLocation.add("Nørreby");
        arrayListLocation.add("Rødbyhavn");
        arrayListLocation.add("Ny Borre");
        arrayListLocation.add("Borre");
        arrayListLocation.add("Østerlars");
        arrayListLocation.add("Silstrup");
        arrayListLocation.add("Tylstrup");
        arrayListLocation.add("Stenhøj");
        arrayListLocation.add("Skagen Fyr");
        arrayListLocation.add("Hald Vest");
        arrayListLocation.add("Vestervig");
        arrayListLocation.add("Thyborøn");
        arrayListLocation.add("Mejrup");
        arrayListLocation.add("Hvide Sande");
        arrayListLocation.add("Ålestrup");
        arrayListLocation.add("Års Syd");
        arrayListLocation.add("Isenvad");
        arrayListLocation.add("Ødum");
        arrayListLocation.add("Sletterhage Fyr");
        arrayListLocation.add("Kirstinesminde Flyveplads");
        arrayListLocation.add("Århus Syd");
        arrayListLocation.add("Anholt Fyr");
        arrayListLocation.add("Anholt By");
        arrayListLocation.add("Anholt Havn");
        arrayListLocation.add("Blåvandshuk Fyr");
        arrayListLocation.add("Borris");
        arrayListLocation.add("Nordby");
        arrayListLocation.add("Vester Vedsted");
        arrayListLocation.add("Rømø/Juvre");
        arrayListLocation.add("Store Jyndevad");
        arrayListLocation.add("Kegnæs Fyr");
        arrayListLocation.add("Tvingsbjerg Fyr");
        arrayListLocation.add("Assens/Torø");
        arrayListLocation.add("Sydfyns Flyveplads");
        arrayListLocation.add("Årslev");
        arrayListLocation.add("Flakkebjerg");
        arrayListLocation.add("Tystofte");
        arrayListLocation.add("Langø");
        arrayListLocation.add("Abed");
        arrayListLocation.add("Gedser Rev Fyrskib");
        arrayListLocation.add("Vindebæk Kyst");
        arrayListLocation.add("Gedser Fyr");
        arrayListLocation.add("Gedser Odde");
        arrayListLocation.add("Gedser");
        arrayListLocation.add("Omø Fyr");
        arrayListLocation.add("Brandelev");
        arrayListLocation.add("Holbæk");
        arrayListLocation.add("Holbæk Flyveplads");
        arrayListLocation.add("Røsnæs Fyr");
        arrayListLocation.add("Nakkehoved Fyr");
        arrayListLocation.add("Gniben");
        arrayListLocation.add("Køge");
        arrayListLocation.add("Tessebølle");
        arrayListLocation.add("Jægersborg");
        arrayListLocation.add("Drogden Fyr");
        arrayListLocation.add("Danmarks Meteorologiske Inst.");
        arrayListLocation.add("Dmi");
        arrayListLocation.add("Landbohøjskolen");
        arrayListLocation.add("Københavns Toldbod");
        arrayListLocation.add("Sjælsmark");
        arrayListLocation.add("Hammer Odde Fyr");
        arrayListLocation.add("Nexø Vest");
        arrayListLocation.add("Klarup");
        arrayListLocation.add("Brovst");
        arrayListLocation.add("Skive Lufthavn");
        arrayListLocation.add("Flyvestation Karup");
        arrayListLocation.add("Hevringsholm");
        arrayListLocation.add("Tirstrup");
        arrayListLocation.add("Hesseballe");
        arrayListLocation.add("Kolding");
        arrayListLocation.add("Haderslev");
        arrayListLocation.add("Holstebro");

        ArrayAdapter<String> arrayAdapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListLocation);
        arrayAdapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterLocation);

        spinnerPrice = findViewById(R.id.spinnerPrice);
        ArrayList<String> arrayListPrice = new ArrayList<>();
        arrayListPrice.add("West");
        arrayListPrice.add("East");
        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListPrice);
        arrayAdapterPrice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrice.setAdapter(arrayAdapterPrice);

        accountVM.getCurrentAccount();

        accountVM.getCurrentAccount().observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if(account != null){
                    acc = account;
                    System.out.println("Account value observe:"+acc);
                }
            }
        });

        buttonSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(!acc.getUsername().equals("Default")){
                    reference.child(acc.getUsername()).child("userInfo").child("location").setValue(spinner.getSelectedItem().toString());
                    System.out.println("Location set");
                    displayToast("Location is set");
                }
            }
        });

        buttonSetPrice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(!acc.getUsername().equals("Default")){
                    reference.child(acc.getUsername()).child("userInfo").child("electricityLocation").setValue(spinnerPrice.getSelectedItem().toString());
                    System.out.println("Price set");
                    displayToast("Price is set");
                }
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void displayToast(String texts){
        Toast.makeText(this.getApplicationContext(),texts,Toast.LENGTH_SHORT).show();
    }
}
