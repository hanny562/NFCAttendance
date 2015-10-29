package cap2100.nfcattendance;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ExportCSV {
	SQLController dbHandler;

	Context context;

	protected static final File DATABASE_DIRECTORY = new File(
			Environment.getExternalStorageDirectory(), "AttendanceRecord");

	public ExportCSV(Context context) {
		this.context = context;
	}

	public Boolean exportDataToCSV(String outFileName) {
		Boolean returnCode = false;
		String csvHeader = "";
		String csvValues = "";
		try {
			dbHandler = new SQLController(context);
			dbHandler.open();
			if (!DATABASE_DIRECTORY.exists()) {
				DATABASE_DIRECTORY.mkdirs();
			}
			File outFile = new File(DATABASE_DIRECTORY, outFileName);
			FileWriter fileWriter = new FileWriter(outFile);
			BufferedWriter out = new BufferedWriter(fileWriter);
			Cursor cursor = dbHandler.getUserInfo();
			csvHeader += "\"" + "Row Id" + "\",";
			csvHeader += "\"" + "Student Name" + "\",";
			csvHeader += "\"" + "Student ID" + "\",";
			csvHeader += "\n";
			if (cursor != null) {
				out.write(csvHeader);
				while (!cursor.isAfterLast()) {
					csvValues = cursor.getString(0) + ","; // row id
					csvValues += cursor.getString(1) + ","; // student ID
					csvValues += cursor.getString(2) + ",\n"; // name
					out.write(csvValues);
					cursor.moveToNext();
				}
			}
			out.close();
			cursor.close();
			returnCode = true;
		} catch (Exception e) {
			returnCode = false;
		}
		dbHandler.close();
		return returnCode;
	}
}