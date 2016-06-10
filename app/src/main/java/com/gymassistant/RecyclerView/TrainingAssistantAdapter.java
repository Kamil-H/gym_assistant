package com.gymassistant.RecyclerView;

/**
 * Created by KamilH on 2015-10-23.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.gymassistant.Activities.ExercisesPreview;
import com.gymassistant.Models.Series;
import com.gymassistant.R;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class TrainingAssistantAdapter extends RecyclerView.Adapter<TrainingAssistantAdapter.TrainingAssistantRowViewHolder> {
    private Context context;
    private List<Series> itemsList;
    private int[] repeatsArray;
    private int[] loadsArray;

    public TrainingAssistantAdapter(Context context, List<Series> itemsList, int[] repeatsArray, int[] loadsArray) {
        this.context = context;
        this.itemsList = itemsList;
        this.repeatsArray = repeatsArray;
        this.loadsArray = loadsArray;
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
    }

    @Override
    public TrainingAssistantAdapter.TrainingAssistantRowViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_training_assistant, null);

        TrainingAssistantRowViewHolder viewHolder = new TrainingAssistantRowViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TrainingAssistantRowViewHolder rowViewHolder, final int position) {
        final Series items = itemsList.get(position);

        rowViewHolder.muscleGroupTextView.setText(items.getExercise().getCategory());
        rowViewHolder.exerciseTextView.setText(String.format("%s %s", items.getExercise().getName(), items.getExercise().getSecondName()));
        rowViewHolder.seriesTextView.setText(String.valueOf(items.getOrder()));

        rowViewHolder.repeatsEditText.setText(String.valueOf(repeatsArray[position]));
        rowViewHolder.repeatsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    NumberPickerBuilder npb = new NumberPickerBuilder()
                            .setFragmentManager(((FragmentActivity)context).getSupportFragmentManager())
                            .setStyleResId(R.style.BetterPickersDialogFragment)
                            .setPlusMinusVisibility(View.INVISIBLE)
                            .setDecimalVisibility(View.INVISIBLE)
                            .setLabelText(context.getString(R.string.repeats));
                    npb.show();
                    rowViewHolder.repeatsEditText.clearFocus();
                    npb.addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {
                        @Override
                        public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {
                            rowViewHolder.repeatsEditText.setText(String.valueOf(number));
                            repeatsArray[position] = number.intValue();
                        }
                    });
                }
            }
        });

        rowViewHolder.loadEditText.setText(String.valueOf(loadsArray[position]));
        rowViewHolder.loadEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    NumberPickerBuilder npb = new NumberPickerBuilder()
                            .setFragmentManager(((FragmentActivity)context).getSupportFragmentManager())
                            .setStyleResId(R.style.BetterPickersDialogFragment)
                            .setPlusMinusVisibility(View.INVISIBLE)
                            .setDecimalVisibility(View.INVISIBLE)
                            .setLabelText(context.getString(R.string.weight));
                    npb.show();
                    rowViewHolder.loadEditText.clearFocus();
                    npb.addNumberPickerDialogHandler(new NumberPickerDialogFragment.NumberPickerDialogHandlerV2() {
                        @Override
                        public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {
                            rowViewHolder.loadEditText.setText(String.valueOf(number));
                            loadsArray[position] = number.intValue();
                        }
                    });
                }
            }
        });

        rowViewHolder.exercisePreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToExercisesPreviewScreen(items.getId());
            }
        });
    }

    private void goToExercisesPreviewScreen(long id){
        Bundle b = new Bundle();
        b.putLong("id", id);
        Intent intent = new Intent(context.getApplicationContext(), ExercisesPreview.class);
        intent.putExtras(b);
        context.startActivity(intent);
    }

    public class TrainingAssistantRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView muscleGroupTextView, exerciseTextView, seriesTextView;
        public EditText repeatsEditText, loadEditText;
        public Button exercisePreviewButton;

        public TrainingAssistantRowViewHolder(View view) {
            super(view);
            this.muscleGroupTextView = (TextView) view.findViewById(R.id.muscleGroupTextView);
            this.exerciseTextView = (TextView) view.findViewById(R.id.exerciseNameTextView);
            this.seriesTextView = (TextView) view.findViewById(R.id.seriesTextView);
            this.repeatsEditText = (EditText) view.findViewById(R.id.repeatsEditText);
            this.loadEditText = (EditText) view.findViewById(R.id.loadEditText);

            this.exercisePreviewButton = (Button) view.findViewById(R.id.exercisePreviewButton);
        }
    }
}