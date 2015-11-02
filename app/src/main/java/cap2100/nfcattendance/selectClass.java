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

    String nfcData;
    String stringName;
    String stringID;
    String exportFileName;
    Spinner spinner;
    EditText et;
    String spinnerData;
    Button btnAdd, btnDelEntry, btnExport, btnView;
    Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_class);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId())
        {
            case (R.id.btnNext):
                startActivity(new Intent(this, ScanTag.class));
                break;
        }

    }
}
