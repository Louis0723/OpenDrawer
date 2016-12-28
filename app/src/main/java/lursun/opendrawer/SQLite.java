package lursun.opendrawer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 2016/9/5.
 */
public class SQLite extends SQLiteOpenHelper {
    public SQLiteDatabase db=null;
    private final static int _DBVersion = 2;
    private final static String _DBName = "SampleList.db";
    private final static String _TableName = "MySample";
    public SQLite(Context context) {
        super(context, _DBName, null, _DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "CREATE TABLE IF NOT EXISTS XY( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "X INTEGER, " +
                "Y INTEGER" +
                ");";
        db.execSQL(SQL);
        SQL = "INSERT INTO XY(X,Y)" +
                "VALUES(100,100);";
        db.execSQL(SQL);
        SQL = "CREATE TABLE IF NOT EXISTS IP( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " ip TEXT " +
                ");";
        db.execSQL(SQL);
        SQL = "INSERT INTO IP(ip)" +
                "VALUES('192.168.123.1');";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL = "DROP TABLE XY";
        db.execSQL(SQL);
        onCreate(db);
    }


}
