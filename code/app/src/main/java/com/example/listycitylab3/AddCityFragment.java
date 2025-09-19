package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addOrEditCity(City city, int position); // Add position for editing
    }

    private AddCityDialogListener listener;
    private City cityToEdit; // If adding then null
    private int editPosition = -1;

    // Constructor or setter for editing
    public void setCityToEdit(City city, int position){
        this.cityToEdit = city;
        this.editPosition = position;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + "must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById((R.id.edit_text_province_text));

        // Pre-fills fields if editing
        if (cityToEdit != null) {
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/Edit City")
                //.setTitle("Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> { // Orginally "Add"
                    String cityName = editCityName.getText().toString().trim();
                    String provinceName = editProvinceName.getText().toString().trim();

                    if (!cityName.isEmpty() && !provinceName.isEmpty()) {
                        listener.addOrEditCity(new City(cityName, provinceName), editPosition);
                    }
                })
                .create();
    }
}
