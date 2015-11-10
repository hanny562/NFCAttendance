package cap2100.nfcattendance;

//import java.sql.SQLException; //SQLiteException extends android.database.SQLException

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.text.SimpleDateFormat;


public class SQLController {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_STUDENTID = "student_id";
	public static final String KEY_NAME = "student_name";
    public static final String COLUMN_TIME_STAMP = "timeStamp";

	private static final String DB_NAME = "Attendance_db";
	private static final String DB_TABLE = "StudentTable";
	private static final int DB_VERSION = 1;

	private DatabaseHelper myHelper;
	private final Context myContext;
	private SQLiteDatabase myDatabase;


	private static class DatabaseHelper extends SQLiteOpenHelper {

		private static final String LOGCAT = null;

		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			// TODO Auto-generated constructor stub
			Log.d(LOGCAT, "Created");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DB_TABLE + " ("
                    + KEY_ROWID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_STUDENTID  + " TEXT NOT NULL, "
                    + KEY_NAME + " TEXT NOT NULL,"
                    + COLUMN_TIME_STAMP +" INTEGER);");
			Log.d(LOGCAT, "Student Table Created");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(db);
		}

	}

	public SQLController(Context c) {
		myContext = c;
	}

	public SQLController open() throws SQLException {
		myHelper = new DatabaseHelper(myContext);
		myDatabase = myHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		myHelper.close();
	}

	public long createEntry(String stringID, String stringName)
			throws SQLException {
		// TODO Auto-generated method stub
        SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ContentValues contentvalue = new ContentValues();

        contentvalue.put(KEY_STUDENTID, stringID);
        contentvalue.put(KEY_NAME, stringName);
        contentvalue.put(COLUMN_TIME_STAMP, dateTime.format(new Date()));

        //String selectQuery = "SELECT * from DB_TABLE";
       // Cursor cursor = myDatabase.rawQuery(selectQuery, null);

		// use insertOrThrow for exception
		return myDatabase.insertOrThrow(DB_TABLE, null, contentvalue);

	}

	public String getData() throws SQLException {
		// TODO Auto-generated method stub
		String[] dbColumns = new String[] { KEY_ROWID, KEY_STUDENTID, KEY_NAME, COLUMN_TIME_STAMP };
		Cursor cursor = myDatabase.query(DB_TABLE, dbColumns, null, null, null,
				null, null);
		String dbResult = "";
		int Row = cursor.getColumnIndex(KEY_ROWID);
		int StuID = cursor.getColumnIndex(KEY_STUDENTID);
		int StuName = cursor.getColumnIndex(KEY_NAME);
        int timeStamp = cursor.getColumnIndex(COLUMN_TIME_STAMP);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			dbResult = dbResult + "\t\t" + cursor.getString(Row) + "\t\t\t\t\t"
					+ cursor.getString(StuID) + "\t\t\t\t" + cursor.getString(StuName)
                    + "\t\t\t\t" + cursor.getString(timeStamp) + "\n";
		}
		return dbResult;
	}

	public void deleteEntry(long longrow) throws SQLException {
		// TODO Auto-generated method stub
		myDatabase.delete(DB_TABLE, KEY_ROWID + "=" + longrow, null);
	}

	public void deleteAllEntry() {
		// TODO Auto-generated method stub
		myDatabase.execSQL("DELETE FROM " + DB_TABLE);
		myDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                + DB_TABLE + "'"); // reset autoincrement to 1
	}

	public Cursor getUserInfo() {
		// TODO Auto-generated method stub
		Cursor cursor = myDatabase.query(DB_TABLE, null, null, null, null,
				null, null);
		cursor.moveToFirst();
		return cursor;

	}

	public String getId(long longrow) {
		// TODO Auto-generated method stub
		String[] dbColumns = new String[] { KEY_ROWID, KEY_STUDENTID, KEY_NAME, COLUMN_TIME_STAMP };
		Cursor cursor = myDatabase.query(DB_TABLE, dbColumns, KEY_ROWID + "="
				+ longrow, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			String id = cursor.getString(1); // use cursor point to 2nd column
			return id;
		}
		return null;
	}

	public String getName(long longrow) {
		// TODO Auto-generated method stub
		String[] dbColumns = new String[] { KEY_ROWID, KEY_STUDENTID, KEY_NAME, COLUMN_TIME_STAMP };
		Cursor cursor = myDatabase.query(DB_TABLE, dbColumns, KEY_ROWID + "="
				+ longrow, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			String id = cursor.getString(2); // use cursor point to 3rd column
			return id;
		}
		return null;
	}

    public String getTimeStamp(long longrow) {
        String[] dbColumns = new String[] { KEY_ROWID, KEY_STUDENTID, KEY_NAME, COLUMN_TIME_STAMP };
        Cursor cursor = myDatabase.query(DB_TABLE, dbColumns, KEY_ROWID + "="
                + longrow, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String id = cursor.getString(3); // use cursor point to 4th column
            return id;
        }
        return null;
    }

    public boolean checkDataExist(selectClass data)
    {
        Cursor c = myDatabase.rawQuery("SELECT * FROM " + DB_TABLE + " WHERE " + KEY_NAME + "= '" + data + "'",null);

        if (c.getCount() == 0)
        {
            return false;
        }else{
            return true;
        }
    }

}
