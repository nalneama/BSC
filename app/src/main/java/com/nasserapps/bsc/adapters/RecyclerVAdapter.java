package com.nasserapps.bsc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nasserapps.bsc.R;
import com.nasserapps.bsc.ui.EditOrAddPage.EditActivity;
import com.nasserapps.bsc.utilities.Objective;
import com.nasserapps.bsc.utilities.ParseConstants;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Nasser on 1/15/15.
 */
public class RecyclerVAdapter extends RecyclerView.Adapter<RecyclerVAdapter.ViewHolder> {

    protected Context mContext;
    protected List<ParseObject> mObjectives;
    protected int mPosition;

    public RecyclerVAdapter(Context context,List<ParseObject> objectives){
        mContext=context;
        mObjectives=objectives;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.bsc_list,viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bindObjective(mObjectives.get(i));
    }

    @Override
    public int getItemCount() {
        return mObjectives.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        TextView mObjectiveLabel;
        TextView mMeasureLabel;
        TextView mPerformanceLabel;
        TextView mTargetLabel;

        public ViewHolder(View itemView) {
            super(itemView);

            mMeasureLabel = (TextView) itemView.findViewById(R.id.description);
            mObjectiveLabel = (TextView) itemView.findViewById(R.id.objective);
            mPerformanceLabel = (TextView) itemView.findViewById(R.id.performance);
            mTargetLabel = (TextView) itemView.findViewById(R.id.target);
            itemView.setOnLongClickListener(this);
        }

        public void bindObjective(ParseObject objective) {

            double performance = Double.parseDouble(objective.get(ParseConstants.COLUMN_PERFORMANCE).toString());
            double target = Double.parseDouble(objective.get(ParseConstants.COLUMN_TARGET).toString());
            mObjectiveLabel.setText(objective.get(ParseConstants.COLUMN_OBJECTIVE).toString());
            mMeasureLabel.setText(objective.get(ParseConstants.COLUMN_MEASURE).toString());
            mPerformanceLabel.setText(objective.get(ParseConstants.COLUMN_PERFORMANCE).toString());
            mTargetLabel.setText(objective.get(ParseConstants.COLUMN_TARGET).toString());
            mPerformanceLabel.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            if (performance < target) {
                mPerformanceLabel.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                if (performance >= (int) (0.9 * target)) {
                    mPerformanceLabel.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            mPosition=getPosition();

            Objective objective = new Objective(mObjectives.get(mPosition));

            //Toast.makeText(mContext, "The objective is: " + mObjectives.get(mPosition).getString(ParseConstants.COLUMN_OBJECTIVE), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext,EditActivity.class);
            intent.putExtra(ParseConstants.COLUMN_OBJECTIVE, mObjectives.get(mPosition).getObjectId());
            intent.putExtra("test", objective);
            mContext.startActivity(intent);
            return true;
        }
    }

}
