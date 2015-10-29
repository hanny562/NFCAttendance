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

public class selectClass extends AppCompatActivity {

    String nfcData;
    String stringName;
    String stringID;
    String exportFileName;
    Spinner spinner;
    EditText et;
    String spinnerData;
    Button btnAdd, btnDelEntry, btnExport, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.etSubCode);
        addItemOnSpinner();
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelEntry = (Button) findViewById(R.id.btnDelEntry);
        btnExport = (Button) findViewById(R.id.btnExport);
        btnView = (Button) findViewById(R.id.btnView);

        btnAdd.setBackgroundColor(Color.parseColor("#c0deff"));
        btnDelEntry.setBackgroundColor(Color.parseColor("#ab2727"));
        btnExport.setBackgroundColor(Color.parseColor("#c0deff"));
        btnView.setBackgroundColor(Color.parseColor("#c0deff"));
        btnExport.setEnabled(false);

        // ///////////////////////////////////////////////////////////////////////
        Intent intent = getIntent();
        if (intent.getType() != null
                && intent.getType().equals("application/" + getPackageName())) {
            // Read the first record which contains the NFC data
            Parcelable[] rawMsgs = intent
                    .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefRecord Record = ((NdefMessage) rawMsgs[0]).getRecords()[0];
            nfcData = new String(Record.getPayload());

            // split with ","
            String[] stringParts = nfcData.split(",");
            stringID = stringParts[0];
            stringName = stringParts[1];

            // Display the data on the tag
            Toast.makeText(
                    this,
                    "Student ID: " + stringID + "\n" + "Student Name: "
                            + stringName, Toast.LENGTH_SHORT).show();
        }

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enable();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(this, About.class));
                return true;
            case R.id.help:
                startActivity(new Intent(this, Help.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void enable() {
        boolean isReady = et.getText().toString().length() > 1;
        if (isReady) {
            btnExport.setEnabled(true);
        } else {
            btnExport.setEnabled(false);
        }
    }

    public void addItem(View v) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle("New student entry");
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to add the new entry?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d1, int id) {
                                addEntry();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    public void addEntry() {
        boolean check = true;
        try {
            SQLController newEntry = new SQLController(selectClass.this);
            newEntry.open();
            newEntry.createEntry(stringID, stringName);

            newEntry.close();
        } catch (Exception e) {
            check = false;
            Toast.makeText(this,
                    "Error: Unable to add new entry into database.",
                    Toast.LENGTH_LONG).show();
        } finally {
            if (check) {

                Toast.makeText(
                        this,
                        "New entry: " + "\n" + "Student ID: " + stringID + "\n"
                                + "Student Name: " + stringName,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void viewItem(View v) {
        Intent intent = new Intent(".SQLVIEW");
        startActivity(intent);

    }

    public void deleteEntry(View v) {
        Intent intent = new Intent(".SQLDELETE");
        startActivity(intent);
    }

    public void ExportCSV(View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Export to SD card");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to export the records to SD card?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d1, int id) {
                                exportCV();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void exportCV() {
        String temp = et.getText().toString();
        spinnerData = spinner.getSelectedItem().toString();
        exportFileName = temp + "__" + spinnerData + ".csv";

        try {
            ExportCSV csv = new ExportCSV(this);
            csv.exportDataToCSV(exportFileName);
            Toast.makeText(this, "File exported successfully!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error: Unable to export", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void addItemOnSpinner() {
        spinner = (Spinner) findViewById(R.id.spinnerSession);
        // Create an ArrayAdapter using the string array and a default spinner
        // layout

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.spinnerSession_array,
                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
}
