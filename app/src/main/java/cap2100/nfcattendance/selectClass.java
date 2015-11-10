package cap2100.nfcattendance;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class selectClass extends Activity{

    Spinner ssSubject,ssSection;
    Button btnNew, btnViewList, btnCreatetag;
    String nfcData,stringID,stringName;
    final Calendar c = Calendar.getInstance();
    String exportFileName;
    String sSubject, sSection;

    SQLController s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);

        addItemOnSpinner();
        addItemOnSpinner2();

        btnNew = (Button) findViewById(R.id.btnNew);
        btnViewList = (Button) findViewById(R.id.btnViewList);
        btnCreatetag = (Button) findViewById(R.id.btnCreateTag);
        ssSubject = (Spinner) findViewById(R.id.ssSubject);
        ssSection = (Spinner) findViewById(R.id.ssSection);

        String spinnerSubject = ssSubject.getSelectedItem().toString();
        String spinnerSection = ssSection.getSelectedItem().toString();

        Intent passSpinner = new Intent(getApplicationContext(), NameList.class);
        passSpinner.putExtra ("sp_subject", spinnerSubject);
        passSpinner.putExtra ("sp_section", spinnerSection);

        Intent intent = getIntent();

        if (intent.getType() != null && intent.getType().equals("application/" + getPackageName())) {
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
    }
    public void addNewEntry(View v)
    {
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

    public void viewList(View v)
    {
        startActivity(new Intent(this,NameList.class));
    }

    public void writeTag(View v)
    {
        startActivity(new Intent(this,Createtag.class));
    }

    public void exportCSV(View v)
    {
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

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateTime = new SimpleDateFormat("yyyyMMdd");

        sSubject = ssSubject.getSelectedItem().toString();
        sSection = ssSection.getSelectedItem().toString();

        exportFileName = sSubject + "_" + sSection + "_" + dateTime.format(new Date()) + ".csv";

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

    public void AboutUs(View v)
    {
        startActivity(new Intent(this, About.class));
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
