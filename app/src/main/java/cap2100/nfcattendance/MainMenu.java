package cap2100.nfcattendance;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button btnTakeAtt, btnStuList,btnCreatetag;
    String nfcData;
    String stringID, stringName;
    NfcAdapter nfcAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);



        btnTakeAtt = (Button) findViewById(R.id.btnTakeAtt);
        btnTakeAtt.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnTakeAtt:
                startActivity(new Intent(this, selectClass.class));
                break;

            case R.id.btnCreateTag:
                startActivity(new Intent(this, Createtag.class));
                break;
        }

    }
}
