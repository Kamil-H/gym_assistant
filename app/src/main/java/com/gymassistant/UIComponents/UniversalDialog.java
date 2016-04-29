package com.gymassistant.UIComponents;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gymassistant.Fragments.ProfileFragment;
import com.gymassistant.R;

/**
 * Created by KamilH on 2016-03-22.
 */
public class UniversalDialog {
    private Context context;
    private ProfileFragment profileFragment;

    public UniversalDialog(Context context, ProfileFragment profileFragment){
        this.profileFragment = profileFragment;
        this.context = context;
    }

    public void showUniversalDialog(final String sizeType, String name) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.dialog_universal, null);
        dialogBuilder.setView(dialogView);

        final EditText editText = (EditText) dialogView.findViewById(R.id.editText);
        final TextView valueTypeTextView = (TextView) dialogView.findViewById(R.id.valueTypeTextView);
        final TextInputLayout input_value = (TextInputLayout) dialogView.findViewById(R.id.input_value);

        input_value.setHint(name);

        if(sizeType.matches("weight"))
            valueTypeTextView.setText(context.getString(R.string.kilograms));
        else
            valueTypeTextView.setText(context.getString(R.string.centimeters));


        dialogBuilder.setTitle(context.getString(R.string.add_size));
        //dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                double value = Double.parseDouble(editText.getText().toString());
                chooseAction(sizeType, value);
                profileFragment.closeFAM();
            }
        });
        dialogBuilder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void chooseAction(String sizeType, double value) {
        switch (sizeType) {
            case "weight":
                Log.i("WARTOŚĆ", String.format("WAGA: %f", value));
                break;
            case "chest":
                Log.i("WARTOŚĆ", String.format("KLATA: %f", value));
                break;
            case "biceps":
                Log.i("WARTOŚĆ", String.format("BICEPS: %f", value));
                break;
            case "waist":
                Log.i("WARTOŚĆ", String.format("TALIA: %f", value));
                break;
            case "fip":
                Log.i("WARTOŚĆ", String.format("BIODRA: %f", value));
                break;
            case "thigh":
                Log.i("WARTOŚĆ", String.format("UDO: %f", value));
                break;
            case "calf":
                Log.i("WARTOŚĆ", String.format("ŁYDKA: %f", value));
                break;
        }
    }
}
