package cap2100.nfcattendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectList extends AppCompatActivity implements View.OnClickListener{
    Button btnNextList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_list);

        btnNextList = (Button) findViewById(R.id.btnNextList);
        btnNextList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case (R.id.btnNextList):
                startActivity(new Intent(this, NameList.class));
                break;
        }
    }
}
