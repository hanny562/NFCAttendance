package cap2100.nfcattendance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class NameList extends AppCompatActivity implements View.OnClickListener{

    final Calendar c = Calendar.getInstance();

    TextView tvDBInfo;
    Button btnDeleteRec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_list);

        btnDeleteRec =(Button) findViewById(R.id.btnDeleteRec);
        btnDeleteRec.setOnClickListener(this);

        tvDBInfo = (TextView) findViewById(R.id.tvDBInfo);



       try {
            SQLController dbinfo = new SQLController(this);
            dbinfo.open();
            String dbdata = dbinfo.getData();
            dbinfo.close();
            tvDBInfo.setText(dbdata);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case (R.id.btnDeleteRec):
                startActivity(new Intent(this, DeleteData.class));
                break;
        }

    }

}
