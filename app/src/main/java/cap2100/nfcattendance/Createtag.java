package cap2100.nfcattendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Createtag extends AppCompatActivity{

    EditText etStuName, etStuID;
    Button btnWriteToTag;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createtag);

        etStuName = (EditText) findViewById(R.id.etStuName);
        etStuID = (EditText) findViewById(R.id.etStuID);

        btnWriteToTag =(Button) findViewById(R.id.btnWriteToTag);
        btnWriteToTag.setBackgroundColor(Color.parseColor("#c0deff"));
        btnWriteToTag.setEnabled(false);

        etStuName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enable();
            }
        });

        etStuID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enable();

            }
        });

    }

    public void enable()
    {
        boolean isReadyName = etStuName.getText().toString().length() > 1;
        boolean isReadyID = etStuID.getText().toString().length() > 1;
        if (isReadyName && isReadyID)
        {
            btnWriteToTag.setEnabled(true);
        }
        else
        {
            btnWriteToTag.setEnabled(false);
        }
    }

    /* call when user click on button */
    public void pushMessage(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Write to NFC tag");

        // set dialog message
        alertDialogBuilder
                .setMessage("Confirm?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d1, int id) {
                                write();
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

    public void write()
    {
        String StuText = etStuName.getText().toString();
        String IDText = etStuID.getText().toString();

        intent = new Intent(getApplicationContext(), NFCWrite.class);

        intent.putExtra("NAME_MESSAGE", StuText);
        intent.putExtra("ID_MESSAGE", IDText);

        startActivity(intent);
    }

}
