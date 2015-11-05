package cap2100.nfcattendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class NameList extends AppCompatActivity implements View.OnClickListener{

    final Calendar c = Calendar.getInstance();

    TextView textview;
    Button btnExport;
    String spSubject,spSection;
    String exportFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_list);

        spSubject = getIntent().getStringExtra("sp_subject");
        spSection = getIntent().getStringExtra("sp_section");

        btnExport = (Button) findViewById(R.id.btnExport);
        btnExport.setOnClickListener(this);
        try {
            SQLController dbinfo = new SQLController(this);
            dbinfo.open();
            String dbdata = dbinfo.getData();
            dbinfo.close();
            textview.setText(dbdata);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.btnExport:
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
                break;
            case R.id.btnDeleteRec:
                startActivity(new Intent(this, DeleteData.class));
                break;
        }

    }

    public void exportCV() {

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        exportFileName = spSubject + "_" + spSection + "_" + mDay + mMonth + mYear + ".csv";

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
}
