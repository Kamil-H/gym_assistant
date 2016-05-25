package com.gymassistant.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.gymassistant.Database.StartedTrainingPlanDB;
import com.gymassistant.DateConverter;
import com.gymassistant.Models.StartedTrainingPlan;
import com.gymassistant.R;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.List;

/**
 * Created by KamilH on 2016-05-24.
 */
public class TraningStartedExpandableAdapter extends ExpandableRecyclerAdapter<TraningStartedExpandableAdapter.PlanParentViewHolder,
        TraningStartedExpandableAdapter.PlanChildViewHolder> {
    private LayoutInflater mInflator;
    private Context context;
    private List<? extends ParentListItem> parentItemList;

    public TraningStartedExpandableAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        this.context = context;
        this.mInflator = LayoutInflater.from(context);
        this.parentItemList = parentItemList;
    }

    @Override
    public PlanParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View parentView = mInflator.inflate(R.layout.item_parent, parentViewGroup, false);
        return new PlanParentViewHolder(parentView);
    }

    @Override
    public PlanChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View childView = mInflator.inflate(R.layout.item_training_plan_started, childViewGroup, false);
        return new PlanChildViewHolder(childView);
    }

    @Override
    public void onBindParentViewHolder(PlanParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        StartedTrainingPlan item = (StartedTrainingPlan) parentListItem;
        parentViewHolder.nameTextView.setText(item.getName());
    }

    @Override
    public void onBindChildViewHolder(PlanChildViewHolder childViewHolder, final int position, final Object childListItem) {
        final StartedTrainingPlan item = (StartedTrainingPlan) childListItem;

        childViewHolder.traningNameTextView.setText(item.getName());
        childViewHolder.traningPlanNameTextView.setText(item.getTrainingPlan().getName());
        childViewHolder.descriptionTextView.setText(item.getDescription());
        childViewHolder.startDateTextView.setText(item.getStartDate());
        childViewHolder.expectedEndDateTextView.setText(item.getExpectedEndDate());
        childViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(item);
            }
        });
        childViewHolder.finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishTraining(item);
                removeRow(item);
            }
        });
    }

    private void finishTraining(StartedTrainingPlan startedTrainingPlan){
        startedTrainingPlan.setEndDate(DateConverter.today());
        updateTraning(startedTrainingPlan);
    }

    private void updateTraning(StartedTrainingPlan startedTrainingPlan){
        StartedTrainingPlanDB startedTrainingPlanDB = new StartedTrainingPlanDB(context);
        startedTrainingPlanDB.updateStartedTraningPlan(startedTrainingPlan);
    }

    private void removeRow(StartedTrainingPlan startedTrainingPlan){
        notifyParentItemRemoved(parentItemList.indexOf(startedTrainingPlan));
        parentItemList.remove(startedTrainingPlan);
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
                        notifyChildItemChanged(parentItemList.indexOf(startedTrainingPlan), 0);
                        startedTrainingPlan.setName(trainingName);
                        startedTrainingPlan.setDescription(trainingDescription);
                        startedTrainingPlan.setExpectedEndDate(DateConverter.addDays(days));
                        updateTraning(startedTrainingPlan);
                        b.dismiss();
                    }
                }
            }
        });
    }

    public class PlanChildViewHolder extends ChildViewHolder {
        public TextView traningNameTextView, traningPlanNameTextView, descriptionTextView, startDateTextView, expectedEndDateTextView;
        public Button finishButton, editButton;

        public PlanChildViewHolder(View itemView) {
            super(itemView);
            traningNameTextView = (TextView) itemView.findViewById(R.id.traningNameTextView);
            traningPlanNameTextView = (TextView) itemView.findViewById(R.id.trainingPlanNameTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            startDateTextView = (TextView) itemView.findViewById(R.id.startDateTextView);
            expectedEndDateTextView = (TextView) itemView.findViewById(R.id.endDateTextView);
            editButton = (Button) itemView.findViewById(R.id.editButton);
            finishButton = (Button) itemView.findViewById(R.id.finishButton);
        }
    }

    public class PlanParentViewHolder extends ParentViewHolder {
        public TextView nameTextView;
        private ImageView arrowImageView;

        private static final float INITIAL_POSITION = 0.0f;
        private static final float ROTATED_POSITION = 180f;

        public PlanParentViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.traningNameTextView);
            arrowImageView = (ImageView) itemView.findViewById(R.id.arrowImageView);
        }

        @SuppressLint("NewApi")
        @Override
        public void setExpanded(boolean expanded) {
            super.setExpanded(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (expanded) {
                    arrowImageView.setRotation(ROTATED_POSITION);
                } else {
                    arrowImageView.setRotation(INITIAL_POSITION);
                }
            }
        }

        @Override
        public void onExpansionToggled(boolean expanded) {
            super.onExpansionToggled(expanded);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                RotateAnimation rotateAnimation;
                if (expanded) { // rotate clockwise
                    rotateAnimation = new RotateAnimation(ROTATED_POSITION,
                            INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                } else { // rotate counterclockwise
                    rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION,
                            INITIAL_POSITION,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                }

                rotateAnimation.setDuration(200);
                rotateAnimation.setFillAfter(true);
                arrowImageView.startAnimation(rotateAnimation);
            }
        }
    }
}