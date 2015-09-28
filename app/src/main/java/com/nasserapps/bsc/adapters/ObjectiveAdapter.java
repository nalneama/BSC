package com.nasserapps.bsc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nasserapps.bsc.utilities.ParseConstants;
import com.nasserapps.bsc.R;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Nasser on 1/15/15.
 */
public class ObjectiveAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mObjectives;

    public ObjectiveAdapter(Context context,List<ParseObject> objectives){
        super(context, R.layout.bsc_list,objectives);
        mContext=context;
        mObjectives=objectives;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bsc_list, null);
            holder = new ViewHolder();
            holder.mMeasureLabel = (TextView) convertView.findViewById(R.id.description);
            holder.mObjectiveLabel = (TextView) convertView.findViewById(R.id.objective);
            holder.mPerformanceLabel = (TextView) convertView.findViewById(R.id.performance);
            holder.mTargetLabel = (TextView) convertView.findViewById(R.id.target);
            convertView.setTag(holder);
        }
        else{

            holder = (ViewHolder)convertView.getTag();
        }

        ParseObject objective = mObjectives.get(position);
        double performance = Double.parseDouble(objective.get(ParseConstants.COLUMN_PERFORMANCE).toString());
        double target = Double.parseDouble(objective.get(ParseConstants.COLUMN_TARGET).toString());
        holder.mObjectiveLabel.setText(objective.get(ParseConstants.COLUMN_OBJECTIVE).toString());
        holder.mMeasureLabel.setText(objective.get(ParseConstants.COLUMN_MEASURE).toString());
        holder.mPerformanceLabel.setText(objective.get(ParseConstants.COLUMN_PERFORMANCE).toString());
        holder.mTargetLabel.setText(objective.get(ParseConstants.COLUMN_TARGET).toString());
        holder.mPerformanceLabel.setBackgroundColor(mContext.getResources().getColor(R.color.green));
        if ( performance<target )
        {
            holder.mPerformanceLabel.setBackgroundColor(mContext.getResources().getColor(R.color.red));
            if ( performance>=(int)(0.9*target) )
            {
                holder.mPerformanceLabel.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
            };
        };

        return convertView;
    }

    private static class ViewHolder{
        TextView mObjectiveLabel;
        TextView mMeasureLabel;
        TextView mPerformanceLabel;
        TextView mTargetLabel;
    }

}
