package com.gymassistant.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gymassistant.Database.StartedTrainingPlanDB;
import com.gymassistant.DateConverter;
import com.gymassistant.Models.StartedTrainingPlan;
import com.gymassistant.R;
import com.gymassistant.UIComponents.ImageViewRotater;
import com.kyo.expandablelayout.ExpandableLayout;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.List;

/**
 * Created by KamilH on 2016-05-24.
 */
public class TrainingStartedExpandableAdapter extends RecyclerView.Adapter<TrainingStartedExpandableAdapter.ViewHolder> {
    private Context context;
    private List<StartedTrainingPlan> startedTrainingPlanList;

    public TrainingStartedExpandableAdapter(Context context, List<StartedTrainingPlan> startedTrainingPlanList) {
        this.context = context;
        this.startedTrainingPlanList = startedTrainingPlanList;
    }

    @Override
    public int getItemCount() {
        return startedTrainingPlanList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_training_plan_started, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewRotater.rotateImageView(viewHolder.expandableLayout.isExpanded(), viewHolder.arrowImageView);
                viewHolder.expandableLayout.toggleExpansion();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder rowViewHolder, final int position) {
        final StartedTrainingPlan item = startedTrainingPlanList.get(position);

        rowViewHolder.nameTextView.setText(item.getName());
        rowViewHolder.trainingNameTextView.setText(item.getName());
        rowViewHolder.trainingPlanNameTextView.setText(item.getTrainingPlan().getName());
        rowViewHolder.descriptionTextView.setText(item.getDescription());
        rowViewHolder.startDateTextView.setText(item.getStartDate());
        rowViewHolder.expectedEndDateTextView.setText(item.getExpectedEndDate());
        rowViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(item);
            }
        });
        rowViewHolder.finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishTraining(item);
                removeRow(item);
            }
        });
    }

    private void finishTraining(StartedTrainingPlan startedTrainingPlan){
        startedTrainingPlan.setEndDate(DateConverter.today());
        updateTraining(startedTrainingPlan);
    }

    private void updateTraining(StartedTrainingPlan startedTrainingPlan){
        StartedTrainingPlanDB startedTrainingPlanDB = new StartedTrainingPlanDB(context);
        startedTrainingPlanDB.updateStartedTrainingPlan(startedTrainingPlan);
    }

    private void removeRow(StartedTrainingPlan startedTrainingPlan){
        startedTrainingPlanList.remove(startedTrainingPlan);
        notifyDataSetChanged();
    }

    private void showEditDialog(final StartedTrainingPlan startedTrainingPlan){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_activate_plan, null);
        dialogBuilder.setView(dialogView);

        final EditText traningNameEditText = (EditText) dialogView.findViewById(R.id.traningNameEditText);
        final EditText trainingDescriptionEditText = (EditText) dialogView.findViewById(R.id.trainingDescriptionEditText);
        final DiscreteSeekBar trainingPlanLengthSeekBar = (DiscreteSeekBar) dialogView.findViewById(R.id.trainingPlanLengthSeekBar);

        dialogBuilder.setPositiveButton(context.getString(R.string.save_button), new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int whichButton) {}});
        dialogBuilder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int whichButton) {}});

        final AlertDialog b = dialogBuilder.create();
        b.show();
        Button positiveButton = b.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trainingName = traningNameEditText.getText().toString();
                String trainingDescription = trainingDescriptionEditText.getText().toString();
                int days = trainingPlanLengthSeekBar.getProgress();
                if(trainingName.isEmpty()){
                    traningNameEditText.setError(context.getString(R.string.please_set_traning_name));
                } else {
                    if(trainingName.length() > 20){
                        traningNameEditText.setError(context.getString(R.string.text_lenght));
                    } else {
                        startedTrainingPlan.setName(trainingName);
                        startedTrainingPlan.setDescription(trainingDescription);
                        startedTrainingPlan.setExpectedEndDate(DateConverter.addDays(days));
                        updateTraining(startedTrainingPlan);
                        notifyDataSetChanged();
                        b.dismiss();
                    }
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trainingNameTextView, trainingPlanNameTextView, descriptionTextView, startDateTextView, expectedEndDateTextView;
        public Button finishButton, editButton;
        public TextView nameTextView;
        private ImageView arrowImageView;
        public ExpandableLayout expandableLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            trainingNameTextView = (TextView) itemView.findViewById(R.id.trainingNameTextView);
            trainingPlanNameTextView = (TextView) itemView.findViewById(R.id.trainingPlanNameTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            startDateTextView = (TextView) itemView.findViewById(R.id.startDateTextView);
            expectedEndDateTextView = (TextView) itemView.findViewById(R.id.endDateTextView);
            editButton = (Button) itemView.findViewById(R.id.editButton);
            finishButton = (Button) itemView.findViewById(R.id.finishButton);

            nameTextView = (TextView) itemView.findViewById(R.id.startedTrainingPlanNameTextView);
            arrowImageView = (ImageView) itemView.findViewById(R.id.arrowImageView);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandlayout);
        }
    }
}