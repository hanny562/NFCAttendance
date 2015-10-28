package cap2100.nfcattendance;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class NFCWrite extends AppCompatActivity {

    String stuName, stuID;
    TextView tvStuNameDisp, tvStuIDDisp;

    /**Bundle extras = getIntent().getExtras();*/
    /**NfcAdapter nfcAdapter;**/

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

       /** nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter!=null && nfcAdapter.isEnabled())
        {
            Toast.makeText(this, "NFC is enabled!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "NFC is not enabled!",Toast.LENGTH_LONG).show();
        }**/
    }

   /** protected void onNewIntent(Intent intent)
    {
        Toast.makeText(this, "NFC Tag Detected!", Toast.LENGTH_LONG).show();

        if(intent.hasExtra(NfcAdapter.EXTRA_TAG))
        {
            Toast.makeText(this, "NFC INTENT!", Toast.LENGTH_LONG).show();
        }

        super.onNewIntent(intent);
    }

    protected void onResume()
    {
        enableForegroundDispatchSystem();

        super.onResume();

    }

    protected void onPause()
    {
        disableForegroundDispatchSystem();

        super.onPause();
    }

    private void enableForegroundDispatchSystem(){
        Intent intent = new Intent(this, NFCWrite.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);

    }

    private void disableForegroundDispatchSystem(){
        nfcAdapter.disableForegroundDispatch(this);

    }

    private void formatTag(Tag tag, NdefMessage ndefMessage) {
        NdefFormatable ndefFormatable = NdefFormatable.get(tag);

        try {
            if (ndefFormatable == null) {
                Toast.makeText(this, "Tag is not ndef formatable", Toast.LENGTH_SHORT).show();
            }
            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();
        }

        catch(Exception e) {
            Log.e("formatTag", e.getMessage());
        }
    }

    private void writeDataIntoTag(Tag tag)
    {
        Ndef ndefTag = Ndef.get(tag);
    }**/


}
