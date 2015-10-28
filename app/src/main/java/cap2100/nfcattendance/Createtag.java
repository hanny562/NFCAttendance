package cap2100.nfcattendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Createtag extends AppCompatActivity implements View.OnClickListener {

    EditText etStuName, etStuID;
    Button btnWriteToTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtag);

        etStuName = (EditText) findViewById(R.id.etStuName);
        etStuID = (EditText) findViewById(R.id.etStuID);

        btnWriteToTag =(Button) findViewById(R.id.btnWriteToTag);
        btnWriteToTag.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case (R.id.btnWriteToTag):
                startActivity(new Intent(this, NFCWrite.class));
                break;
        }

    }
}
