package com.gymassistant.UIComponents;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gymassistant.R;

import java.io.Serializable;

/**
 * Created by KamilH on 2016-06-20.
 */
public class NumberDialog extends DialogFragment {
    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9, fullStopButton, deleteButton, backButton, nextButton;
    private TextView numberTextView, titleTextView;
    private String text = "";
    private NumberSetListener numberSetListener;

    public interface NumberSetListener extends Serializable {
        void onNumberSet(String text);
    }

    public static NumberDialog newInstance(String title, boolean isDouble, NumberSetListener numberSetListener) {
        NumberDialog numberDialog = new NumberDialog();

        Bundle args = new Bundle();
        args.putBoolean("isDouble", isDouble);
        args.putString("title", title);
        args.putSerializable("interface", numberSetListener);

        numberDialog.setArguments(args);

        numberDialog.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.myDialog);

        return numberDialog;
    }

    public NumberDialog(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_number, container, false);

        numberTextView = (TextView) view.findViewById(R.id.numberTextView);
        titleTextView = (TextView) view.findViewById(R.id.titleTextView);

        String title = getArguments().getString("title");
        titleTextView.setText(title);

        this.numberSetListener = (NumberSetListener) getArguments().getSerializable("interface");

        backButton = (Button) view.findViewById(R.id.backButton);
        nextButton = (Button) view.findViewById(R.id.nextButton);
        button0 = (Button) view.findViewById(R.id.button0);
        button1 = (Button) view.findViewById(R.id.button1);
        button2 = (Button) view.findViewById(R.id.button2);
        button3 = (Button) view.findViewById(R.id.button3);
        button4 = (Button) view.findViewById(R.id.button4);
        button5 = (Button) view.findViewById(R.id.button5);
        button6 = (Button) view.findViewById(R.id.button6);
        button7 = (Button) view.findViewById(R.id.button7);
        button8 = (Button) view.findViewById(R.id.button8);
        button9 = (Button) view.findViewById(R.id.button9);
        fullStopButton = (Button) view.findViewById(R.id.fullStopButton);
        deleteButton = (Button) view.findViewById(R.id.deleteButton);

        backButton.setOnClickListener(clickListener);
        nextButton.setOnClickListener(clickListener);
        button0.setOnClickListener(clickListener);
        button1.setOnClickListener(clickListener);
        button2.setOnClickListener(clickListener);
        button3.setOnClickListener(clickListener);
        button4.setOnClickListener(clickListener);
        button5.setOnClickListener(clickListener);
        button6.setOnClickListener(clickListener);
        button7.setOnClickListener(clickListener);
        button8.setOnClickListener(clickListener);
        button9.setOnClickListener(clickListener);
        fullStopButton.setOnClickListener(clickListener);
        deleteButton.setOnClickListener(clickListener);

        if(!getArguments().getBoolean("isDouble")){
            fullStopButton.setVisibility(View.INVISIBLE);
        }

        return view;
    }

    private void notifyValueChanged(){
        if(!text.isEmpty()) {
            if(text.charAt(0) == '0' && !text.contains(".") && text.charAt(text.length() - 1) != '.'){
                return;
            }
            numberSetListener.onNumberSet(text);
        }
    }

    private void closeDialog(){
        this.dismiss();
    }

    public String getValue(){
        return this.text;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backButton:
                    closeDialog();
                    break;
                case R.id.nextButton:
                    notifyValueChanged();
                    closeDialog();
                    break;
                case R.id.button0:
                    text = text + "0";
                    break;
                case R.id.button1:
                    text = text + "1";
                    break;
                case R.id.button2:
                    text = text + "2";
                    break;
                case R.id.button3:
                    text = text + "3";
                    break;
                case R.id.button4:
                    text = text + "4";
                    break;
                case R.id.button5:
                    text = text + "5";
                    break;
                case R.id.button6:
                    text = text + "6";
                    break;
                case R.id.button7:
                    text = text + "7";
                    break;
                case R.id.button8:
                    text = text + "8";
                    break;
                case R.id.button9:
                    text = text + "9";
                    break;
                case R.id.fullStopButton:
                    if(!text.isEmpty() && !text.contains(".")){
                        text = text + ".";
                    }
                    break;
                case R.id.deleteButton:
                    text = removeLastCharacter(text);
                    break;
            }
            numberTextView.setText(text);
        }
    };

    public String removeLastCharacter(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }
}
