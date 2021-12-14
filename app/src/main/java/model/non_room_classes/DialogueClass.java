package model.non_room_classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import view.activities.HomeActivity;

public class DialogueClass extends AppCompatDialogFragment {
    private DialogListener listener;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Removing a plant").setMessage("Are you sure you want to remove this plant from the Schedule?\nYou can't undo this later").
                setNegativeButton("Yes", (dialogInterface, i) -> {
                    listener.onYesClicked();

                }).setPositiveButton("No", (dialogInterface, i) -> {
                });
        return builder.create();
    }

    public interface DialogListener{
        void onYesClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            listener = (DialogListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
            +" must implement DialogListener");
        }
    }
}
