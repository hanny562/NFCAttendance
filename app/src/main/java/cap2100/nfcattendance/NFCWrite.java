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
import java.text.Normalizer;

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

    public void onNewIntent(Intent intent)
    {
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()))
        {
            Tag discoveredTag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            try{
                writeDataToTag(discoveredTag);
            }catch (IOException e){
                e.printStackTrace();
            }catch (FormatException e){
                e.printStackTrace();
            }
        }
    }

    private void writeDataToTag(Tag tag) throws IOException, FormatException {

        Ndef ndefTag = Ndef.get(tag);
        String nfcMessage = stuID + "," + stuName;

        // Chunk the record of MINE-type data and student name/ID
        NdefRecord Record = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                new String("application/" + getPackageName()).getBytes(), null, nfcMessage.getBytes());

        // Construct NDEF message with the record
        NdefMessage message = new NdefMessage(Record);

        try {
            ndefTag.connect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //write to nfc tag
        ndefTag.writeNdefMessage(message);
        ndefTag.close();
        Toast.makeText(getApplicationContext(), "Student ID : " + stuID + "\nStudent Name : " + stuName + "\nSuccessfully written!",
                Toast.LENGTH_LONG).show();
    }

    public void onResume(){
        enableNfcWrite();
        super.onResume();
    }

    public void onPause(){
        disableNfcWrite();
        super.onPause();
    }

    private PendingIntent getPendingIntent() {
        return PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    private void enableNfcWrite() {
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        IntentFilter tagDetected = new IntentFilter(
                NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] writeTagFilters = new IntentFilter[] { tagDetected };
        adapter.enableForegroundDispatch(this, getPendingIntent(),
                writeTagFilters, null);
    }

    private void disableNfcWrite() {
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        adapter.disableForegroundDispatch(this);
    }


}
