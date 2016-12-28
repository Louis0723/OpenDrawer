package lursun.opendrawer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLite sql=new SQLite(this);
        final SQLiteDatabase db=sql.getReadableDatabase();
        Cursor c=db.rawQuery("select * from IP",null);
        c.moveToFirst();
        ((EditText)findViewById(R.id.ip)).setText(c.getString(1));
        try {
            Intent intent = new Intent(MainActivity.this, BootService.class);
            this.startService(intent);
        }
        catch (Exception e){
            e=e;
        }
    }
    public void openMoneyDrawer(View view){
        SQLite sql=new SQLite(this);
        final SQLiteDatabase db=sql.getReadableDatabase();
        EditText ip=(EditText)findViewById(R.id.ip);
        db.execSQL("UPDATE IP SET ip = \'"+ip.getText().toString()+"\' WHERE _id=1;");
        new TCP(ip.getText().toString()).start();
    }
}
