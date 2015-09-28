package com.nasserapps.bsc.ui.HomePage;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.nasserapps.bsc.R;
import com.nasserapps.bsc.adapters.RecyclerVAdapter;
import com.nasserapps.bsc.ui.EditOrAddPage.AddActivity;
import com.nasserapps.bsc.utilities.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class RecyclerViewFragment extends Fragment{

    protected List<ParseObject> mObjectives;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TABLE_NAME);
        //query.whereEqualTo(ParseConstants.COLUMN_CATEGORY, "People");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectives, ParseException e) {
                if (e == null) {
                    mObjectives = objectives;
                }
                RecyclerVAdapter adapter = new RecyclerVAdapter(getActivity(),mObjectives);
                recyclerView.setAdapter(adapter);

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        getView().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddActivity.class);
                startActivity(intent);

            }
        });
    }

}
