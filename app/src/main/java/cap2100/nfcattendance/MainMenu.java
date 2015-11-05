package cap2100.nfcattendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button btnTakeAtt, btnStuList,btnCreatetag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnTakeAtt = (Button) findViewById(R.id.btnTakeAtt);
        btnTakeAtt.setOnClickListener(this);

        btnStuList = (Button) findViewById(R.id.btnStuList);
        btnStuList.setOnClickListener(this);

        btnCreatetag = (Button) findViewById(R.id.btnCreateTag);
        btnCreatetag.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnTakeAtt:
                startActivity(new Intent(this, selectClass.class));
                break;
            case R.id.btnStuList:
                startActivity(new Intent(this, NameList.class));
                break;
            case R.id.btnCreateTag:
                startActivity(new Intent(this, Createtag.class));
                break;
        }

    }
}
