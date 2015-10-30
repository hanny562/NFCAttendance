package cap2100.nfcattendance;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
//import android.app.Dialog;

public class SQLView extends Activity {
	TextView textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.activity_sqlview);
		textview = (TextView) findViewById(R.id.tvDBInfo);*/
		try {
			SQLController dbinfo = new SQLController(this);
			dbinfo.open();
			String dbdata = dbinfo.getData();
			dbinfo.close();
			textview.setText(dbdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}


}