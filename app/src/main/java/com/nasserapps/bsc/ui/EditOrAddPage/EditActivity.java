package com.nasserapps.bsc.ui.EditOrAddPage;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nasserapps.bsc.R;
import com.nasserapps.bsc.utilities.Objective;
import com.nasserapps.bsc.utilities.ParseConstants;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class EditActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {


    @InjectView(R.id.button)Button mAddButton;
    @InjectView(R.id.delete)Button mDeleteButton;
    @InjectView(R.id.objective)EditText objective;
    @InjectView(R.id.performance)EditText mPerformance;
    @InjectView(R.id.target)EditText mTarget;
    @InjectView(R.id.measure)EditText mMeasure;
    @InjectView(R.id.unit)EditText mUnit;
    @InjectView(R.id.spinner)Spinner mSpinner;
    String[] categories;
    private String mCategory;
    private ParseObject mObjective;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.inject(this);

        mDeleteButton.setVisibility(View.VISIBLE);
        mAddButton.setText("Update");
        mAddButton.setBackgroundColor(getResources().getColor(R.color.green));
        categories=getResources().getStringArray(R.array.categories);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        final String objectID = getIntent().getStringExtra(ParseConstants.COLUMN_OBJECTIVE);
        Parcelable parcelable = getIntent().getParcelableExtra("test");
        Objective object = (Objective)parcelable;
        updateUI(object);
        //Toast.makeText(this, "The objective is: " + objectID , Toast.LENGTH_SHORT).show();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.TABLE_NAME);
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    mObjective = object;

                } else {
                    // something went wrong
                }
            }
        });

        mMeasure.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    updateObjective();
                    return true;
                }
                return false;
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateObjective();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mObjective.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        finish();
                    }
                });
            }
        });
    }

    private void updateUI(Objective object) {
        objective.setText(object.getObjective());
        mPerformance.setText(object.getPerformance());
        mTarget.setText(object.getTarget());
        mUnit.setText(object.getUnit());
        mMeasure.setText(object.getMeasure());
        mCategory = object.getCategory();
        int pos=0;
        for(int i=0;i<categories.length;i++){
            if (categories[i].equals(mCategory)){
                pos =i;
            }
        }
        mSpinner.setSelection(pos);
    }

    private void updateObjective() {
        mObjective.put(ParseConstants.COLUMN_OBJECTIVE, objective.getText().toString());
        mObjective.put(ParseConstants.COLUMN_TARGET, Double.parseDouble(mTarget.getText().toString()));
        mObjective.put(ParseConstants.COLUMN_PERFORMANCE, Double.parseDouble(mPerformance.getText().toString()));
        mObjective.put(ParseConstants.COLUMN_MEASURE, mMeasure.getText().toString());
        mObjective.put(ParseConstants.COLUMN_CATEGORY, mCategory);
        mObjective.put(ParseConstants.COLUMN_UNIT, mUnit.getText().toString());
        mObjective.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mCategory=categories[position];
        //Toast.makeText(getApplication(),categories[mPosition] ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
