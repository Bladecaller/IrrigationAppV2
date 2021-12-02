package fragments;



import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sep4_android.FirebaseHelperClass;
import com.example.sep4_android.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeFragment extends Fragment {


    public TextView usernameDisplay;
    public EditText plantInfo, yardsInfo, waterPerYardsInfo, plantDateInfo, harvestInfo;
    public Button addDataBtn;
    private String username = "";
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
/*
            //NOT WORKING
            usernameDisplay = rootView.findViewById(R.id.username2);

            plantInfo = rootView.findViewById(R.id.plantText);
            yardsInfo = rootView.findViewById(R.id.);
            waterPerYardsInfo = rootView.findViewById(R.id.waterPerYardsText);
            plantDateInfo = rootView.findViewById(R.id.dateeText);
            harvestInfo = rootView.findViewById(R.id.harvestText);
            addDataBtn = rootView.findViewById(R.id.addDataButton);

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String name = prefs.getString("name", "default_name");
        usernameDisplay.setText(name);




            addDataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
                    reference = rootNode.getReference("users");

                    String plantName = plantInfo.getText().toString();
                    String harvest = harvestInfo.getText().toString();
                    String yards = yardsInfo.getText().toString();
                    String waterPerYards = waterPerYardsInfo.getText().toString();
                    String startingDate = plantDateInfo.getText().toString();


                    Query retrieveData = reference.orderByChild("username").equalTo(username);

                    retrieveData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String retrievedPlantName = dataSnapshot.child(username).child("plant").getValue(String.class);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("RETRIEVE ERROR", databaseError.getMessage());
                        }
                    });

                    FirebaseHelperClass helperClass = new FirebaseHelperClass(plantName, yards, waterPerYards, startingDate, harvest);

                    reference.push().setValue(helperClass);
                }
            });
            */
            return rootView;
        }
    }