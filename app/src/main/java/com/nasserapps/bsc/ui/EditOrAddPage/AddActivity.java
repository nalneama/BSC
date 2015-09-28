package com.nasserapps.bsc.ui.EditOrAddPage;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nasserapps.bsc.utilities.ParseConstants;
import com.nasserapps.bsc.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class AddActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    @InjectView(R.id.button)Button mAddButton;
    @InjectView(R.id.objective)EditText mObjective;
    @InjectView(R.id.performance)EditText mPerformance;
    @InjectView(R.id.target)EditText mTarget;
    @InjectView(R.id.measure)EditText mMeasure;
    @InjectView(R.id.unit)EditText mUnit;
    @InjectView(R.id.spinner)Spinner mSpinner;
    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.inject(this);

        mObjective.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addObjective();
            }
        });

        mMeasure.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    addObjective();
                    return true;
                }
                return false;
            }
        });

    }

    private void addObjective() {
        ParseObject stObj = new ParseObject(ParseConstants.TABLE_NAME);
        stObj.put(ParseConstants.COLUMN_OBJECTIVE, mObjective.getText().toString());
        stObj.put(ParseConstants.COLUMN_TARGET, Double.parseDouble(mTarget.getText().toString()));
        stObj.put(ParseConstants.COLUMN_PERFORMANCE, Double.parseDouble(mPerformance.getText().toString()));
        stObj.put(ParseConstants.COLUMN_CATEGORY, mCategory);
        stObj.put(ParseConstants.COLUMN_MEASURE, mMeasure.getText().toString());
        stObj.put(ParseConstants.COLUMN_UNIT, mUnit.getText().toString());

        stObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] categories=getResources().getStringArray(R.array.categories);
        mCategory=categories[position];
        //Toast.makeText(getApplication(),categories[mPosition] ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
