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
import com.gymassistant.Database.TrainingPlanDB;
import com.gymassistant.DateConverter;
import com.gymassistant.Models.StartedTrainingPlan;
import com.gymassistant.Models.TrainingPlan;
import com.gymassistant.R;
import com.gymassistant.UIComponents.ImageViewRotater;
import com.kyo.expandablelayout.ExpandableLayout;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.List;

/**
 * Created by KamilH on 2016-06-09.
 */
public class TrainingManagementAdapter extends RecyclerView.Adapter<TrainingManagementAdapter.ViewHolder> {
    private Context context;
    private List<TrainingPlan> trainingPlanList;

    public TrainingManagementAdapter(Context context, List<TrainingPlan> trainingPlanList) {
        this.context = context;
        this.trainingPlanList = trainingPlanList;
    }

    @Override
    public int getItemCount() {
        return trainingPlanList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int position) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_training_management, null);
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
    public void onBindViewHolder(ViewHolder rowViewHolder, int position) {
        final TrainingPlan trainingPlan = trainingPlanList.get(position);
        rowViewHolder.nameTextView.setText(trainingPlan.getName());
        rowViewHolder.descriptionEditText.setText(trainingPlan.getDescription());
        rowViewHolder.numOfTrainingDaysTextView.setText(String.valueOf(trainingPlan.getDays()));
        rowViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(trainingPlan);
            }
        });
        rowViewHolder.activateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActivateDialog(trainingPlan);
            }
        });
    }

    private void remove(final TrainingPlan trainingPlan){
        deleteTrainingPlan(trainingPlan.getId());
        trainingPlanList.remove(trainingPlan);
        notifyDataSetChanged();
    }

    public void deleteTrainingPlan(long id){
        TrainingPlanDB trainingPlanDB = new TrainingPlanDB(context);
        trainingPlanDB.deleteTrainingPlan(id);
    }

    private void activate(StartedTrainingPlan startedTrainingPlan){
        StartedTrainingPlanDB startedTrainingPlanDB = new StartedTrainingPlanDB(context);
        startedTrainingPlanDB.addStartedTrainingPlan(startedTrainingPlan);
    }

    private void showActivateDialog(final TrainingPlan trainingPlan){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_activate_plan, null);
        dialogBuilder.setView(dialogView);

        final EditText trainingNameEditText = (EditText) dialogView.findViewById(R.id.traningNameEditText);
        final EditText trainingDescriptionEditText = (EditText) dialogView.findViewById(R.id.trainingDescriptionEditText);
        final DiscreteSeekBar trainingPlanLengthSeekBar = (DiscreteSeekBar) dialogView.findViewById(R.id.trainingPlanLengthSeekBar);

        dialogBuilder.setPositiveButton(context.getString(R.string.activate), new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int whichButton) {}});
        dialogBuilder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int whichButton) {}});

        final AlertDialog b = dialogBuilder.create();
        b.show();
        Button positiveButton = b.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trainingName = trainingNameEditText.getText().toString();
                String trainingDescription = trainingDescriptionEditText.getText().toString();
                int days = trainingPlanLengthSeekBar.getProgress();
                if(trainingName.isEmpty()){
                    trainingNameEditText.setError(context.getString(R.string.please_set_traning_name));
                } else {
                    if(trainingName.length() > 20){
                        trainingNameEditText.setError(context.getString(R.string.text_lenght));
                    } else {
                        activate(new StartedTrainingPlan(trainingName, trainingDescription, trainingPlan.getId(), DateConverter.today(), DateConverter.addDays(days)));
                        b.dismiss();
                    }
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        private ImageView arrowImageView;
        public TextView descriptionEditText, numOfTrainingDaysTextView;
        public Button activateButton, deleteButton;
        public ExpandableLayout expandableLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            descriptionEditText = (TextView) itemView.findViewById(R.id.descriptionTextView);
            numOfTrainingDaysTextView = (TextView) itemView.findViewById(R.id.numOfTrainingDaysTextView);
            activateButton = (Button) itemView.findViewById(R.id.activateButton);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButton);

            nameTextView = (TextView) itemView.findViewById(R.id.exerciseNameTextView);
            arrowImageView = (ImageView) itemView.findViewById(R.id.arrowImageView);

            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandlayout);
        }
    }
}
