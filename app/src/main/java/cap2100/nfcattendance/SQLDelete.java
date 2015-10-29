package cap2100.nfcattendance;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SQLDelete extends Activity {
	EditText et;
	String stringrow;
	String rId;
	String rName;
	Button delEntry;
	Button delAll;

	/* Button button; */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sqldelete);
		et = (EditText) findViewById(R.id.etRowID);
		delEntry = (Button) findViewById(R.id.btnDeleteEntry);
		delAll = (Button) findViewById(R.id.btmDeleteAll);
		delEntry.setBackgroundColor(Color.parseColor("#936c90"));
		delAll.setBackgroundColor(Color.parseColor("#ab2727"));
		delEntry.setEnabled(false);
		et.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
				enable();
				delEntry.setBackgroundColor(Color.parseColor("#ab2727"));
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});
	}

	public void enable() {
		boolean isReady = et.getText().toString().length() > 0;
		if (isReady) {
			delEntry.setEnabled(true);
		} else {
			delEntry.setEnabled(false);
		}
	}

	public void deleteRow(View v) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Delete entry");
		alertDialogBuilder
				.setMessage("Do you want to delete this entry?")
				.setCancelable(false)
				.setPositiveButton("Confirm",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface d1, int id) {
								delete();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void deleteAllRow(View v) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Remove attendance records");
		alertDialogBuilder
				.setMessage("Do you want to remove all the records?")
				.setCancelable(false)
				.setPositiveButton("Confirm",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface d1, int id) {
								deleteall();
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	public void delete() {
		boolean check = true;
		try {
			stringrow = et.getText().toString();
			long longrow = Long.parseLong(stringrow);
			SQLController delentry = new SQLController(this);
			delentry.open();
			rName = delentry.getName(longrow); 
			rId = delentry.getId(longrow);
			delentry.deleteEntry(longrow); // delete the record based on column index ID
			delentry.close();
		} catch (Exception e) {
			Toast.makeText(this, "Error: Invalid row ID.",
					Toast.LENGTH_LONG).show();
		} finally {
			if (check) {
				Toast.makeText(
						this,
						"[Row ID: " + stringrow + "] Student " + rName
								+ " with " + rId + " has been deleted.",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public void deleteall() {
		boolean check = true;
		try {
			SQLController delentry = new SQLController(this);
			delentry.open();
			delentry.deleteAllEntry();
			delentry.close();
		} catch (Exception e) {
			Toast.makeText(this, "Error: Unable to drop the database table.",
					Toast.LENGTH_LONG).show();
		} finally {
			if (check) {
				Toast.makeText(this, "Successfully deleted all entries!",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
