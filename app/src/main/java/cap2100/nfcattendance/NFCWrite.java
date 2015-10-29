package cap2100.nfcattendance;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.Format;

public class NFCWrite extends AppCompatActivity {

    String stuName, stuID;
    TextView tvStuNameDisp, tvStuIDDisp;

    /*Bundle extras = getIntent().getExtras();*/
    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcwrite);

        stuName = getIntent().getStringExtra("NAME_MESSAGE");
        stuID = getIntent().getStringExtra("ID_MESSAGE");

        tvStuNameDisp = (TextView) findViewById(R.id.tvStuNameDisp);
        tvStuNameDisp.setTextSize(15);
        tvStuNameDisp.setText("Student Name : " + stuName);

        tvStuIDDisp = (TextView) findViewById(R.id.tvStuIDDisp);
        tvStuIDDisp.setTextSize(15);
        tvStuIDDisp.setText("Student ID : " + stuID);

       nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter!=null && nfcAdapter.isEnabled())
        {
            Toast.makeText(this, "NFC is enabled!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "NFC is not enabled!",Toast.LENGTH_LONG).show();
        }
    }

}
