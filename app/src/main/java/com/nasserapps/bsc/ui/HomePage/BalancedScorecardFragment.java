package com.nasserapps.bsc.ui.HomePage;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.nasserapps.bsc.R;
import com.nasserapps.bsc.adapters.ObjectiveAdapter;
import com.nasserapps.bsc.ui.EditOrAddPage.AddActivity;
import com.nasserapps.bsc.ui.EditOrAddPage.EditActivity;
import com.nasserapps.bsc.utilities.Objective;
import com.nasserapps.bsc.utilities.ParseConstants;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Arrays;
import java.util.List;

public class BalancedScorecardFragment extends ListFragment{

    protected List<ParseObject> mObjectives;
    protected int mPosition;

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    final String SAVED_OBJECTIVES = "topScores";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        TextView textView = (TextView) view.findViewById(R.id.info_text);
        fab.attachToListView(listView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Call Shift Controller",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TABLE_NAME);
        //query.whereEqualTo(ParseConstants.COLUMN_CATEGORY, "People");
        /*query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectives, ParseException e) {
                if (e == null) {
                    mObjectives = objectives;
                }
                ObjectiveAdapter adapter = new ObjectiveAdapter(getListView().getContext(),mObjectives);
                setListAdapter(adapter);
            }
        });*/

        // Query for the latest objects from Parse.

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(final List<ParseObject> scoreList, ParseException e) {
                if (e != null) {
                    // There was an error or the network wasn't available.

                    query.fromLocalDatastore();
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(final List<ParseObject> scoreList, ParseException e) {
                            if (e == null) {
                                // Results were successfully found from the local datastore.
                                mObjectives=scoreList;
                            } else {
                                // There was an error.
                            }
                        }
                    });

                    return;
                }
                else {
                    // Release any objects previously pinned for this query.
                    ParseObject.unpinAllInBackground(SAVED_OBJECTIVES, scoreList, new DeleteCallback() {
                        public void done(ParseException e) {
                            if (e != null) {
                                // There was some error.
                                return;
                            }

                            // Add the latest results for this query to the cache.
                            ParseObject.pinAllInBackground(SAVED_OBJECTIVES, scoreList);
                            mObjectives = scoreList;


                        }
                    });
                }
                ObjectiveAdapter adapter = new ObjectiveAdapter(getListView().getContext(), mObjectives);
                setListAdapter(adapter);
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        /*
        mObjectives=getFavorites(getActivity());
        if (mObjectives!=null) {
            ObjectiveAdapter adapter = new ObjectiveAdapter(getListView().getContext(), mObjectives);
            setListAdapter(adapter);
        }
        */

        final ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TABLE_NAME);
        //query.whereEqualTo(ParseConstants.COLUMN_CATEGORY, "People");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objectives, ParseException e) {
                if (e == null) {
                    mObjectives = objectives;
                }
                ObjectiveAdapter adapter = new ObjectiveAdapter(getListView().getContext(),mObjectives);
                setListAdapter(adapter);
            }
        });
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long id) {
                mPosition = position;
                Objective objective = new Objective(mObjectives.get(position));
                //Toast.makeText(getActivity(), "The objective is: " + mObjectives.get(mPosition).getString(ParseConstants.COLUMN_OBJECTIVE), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),EditActivity.class);
                intent.putExtra(ParseConstants.COLUMN_OBJECTIVE, mObjectives.get(position).getObjectId());
                intent.putExtra("test", objective);
                startActivity(intent);
                return true;
            }
        });

        getView().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        //
        saveFavorites(getActivity(),mObjectives);

    }

    public void saveFavorites(Context context, List<ParseObject> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
    }

    public List<ParseObject> getFavorites(Context context) {
        SharedPreferences settings;
        List<ParseObject> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            ParseObject[] favoriteItems = gson.fromJson(jsonFavorites,ParseObject[].class);

            favorites = Arrays.asList(favoriteItems);

        } else
            return null;
        return favorites;
    }
}
