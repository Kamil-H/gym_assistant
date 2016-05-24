package com.gymassistant.RecyclerView;

/**
 * Created by KamilH on 2015-10-23.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gymassistant.Models.Series;
import com.gymassistant.R;

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

        TrainingAssistantRowViewHolder viewHolder = new TrainingAssistantRowViewHolder(view, new MyCustomEditTextListener(), new MyCustomEditTextListener());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrainingAssistantRowViewHolder rowViewHolder, final int position) {
        Series items = itemsList.get(position);

        rowViewHolder.muscleGroupTextView.setText(items.getExercise().getCategory());
        rowViewHolder.exerciseTextView.setText(items.getExercise().getName() + " " + items.getExercise().getSecondName());
        rowViewHolder.seriesTextView.setText("3");

        rowViewHolder.repeatsEditTextListener.updatePosition(position);
        rowViewHolder.repeatsEditTextListener.setArray(repeatsArray);
        rowViewHolder.repeatsEditText.setText(String.valueOf(repeatsArray[position]));

        rowViewHolder.loadsEditTextListener.updatePosition(position);
        rowViewHolder.loadsEditTextListener.setArray(loadsArray);
        rowViewHolder.loadEditText.setText(String.valueOf(loadsArray[position]));
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;
        private int[] array;

        public void updatePosition(int position) {
            this.position = position;
        }

        public void setArray(int[] array){
            this.array = array;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @SuppressLint("NewApi")
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if(!charSequence.toString().matches(""))
                array[position] = Integer.parseInt(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    public class TrainingAssistantRowViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView muscleGroupTextView, exerciseTextView, seriesTextView;
        public EditText repeatsEditText, loadEditText;
        public MyCustomEditTextListener repeatsEditTextListener, loadsEditTextListener;

        public TrainingAssistantRowViewHolder(View view, MyCustomEditTextListener repeatsEditTextListener, MyCustomEditTextListener loadsEditTextListener) {
            super(view);
            this.muscleGroupTextView = (TextView) view.findViewById(R.id.dayNameTextView);
            this.exerciseTextView = (TextView) view.findViewById(R.id.traningNameTextView);
            this.seriesTextView = (TextView) view.findViewById(R.id.seriesTextView);
            this.repeatsEditText = (EditText) view.findViewById(R.id.repeatsEditText);
            this.loadEditText = (EditText) view.findViewById(R.id.loadEditText);

            this.repeatsEditTextListener = repeatsEditTextListener;
            this.repeatsEditText.addTextChangedListener(repeatsEditTextListener);

            this.loadsEditTextListener = loadsEditTextListener;
            this.loadEditText.addTextChangedListener(loadsEditTextListener);
        }
    }
}