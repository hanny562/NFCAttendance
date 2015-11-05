package cap2100.nfcattendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class selectClass extends AppCompatActivity implements View.OnClickListener{

    Spinner ssSubject,ssSection;
    Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);

        addItemOnSpinner();
        addItemOnSpinner2();

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        ssSubject = (Spinner) findViewById(R.id.ssSubject);
        ssSection = (Spinner) findViewById(R.id.ssSection);

        String spinnerSubject = ssSubject.getSelectedItem().toString();
        String spinnerSection = ssSection.getSelectedItem().toString();

        Intent passSpinner = new Intent(getApplicationContext(), NameList.class);
        passSpinner.putExtra ("sp_subject", spinnerSubject);
        passSpinner.putExtra ("sp_section", spinnerSection);
    }

    public void onClick(View v) {
        switch(v.getId())
        {
            case (R.id.btnNext):

                startActivity(new Intent(this, ScanTag.class));
                break;
        }

    }

    public void addItemOnSpinner() {
        ssSubject = (Spinner) findViewById(R.id.ssSubject);
        // Create an ArrayAdapter using the string array and a default spinner
        // layout

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.ssSubject_array,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        ssSubject.setAdapter(adapter);
    }

    public void addItemOnSpinner2() {
        ssSection = (Spinner) findViewById(R.id.ssSection);
        // Create an ArrayAdapter using the string array and a default spinner
        // layout

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.ssSection_array,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        ssSection.setAdapter(adapter);
    }

}
