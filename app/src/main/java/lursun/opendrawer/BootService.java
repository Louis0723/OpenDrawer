package lursun.opendrawer;


import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.HashMap;


public class BootService extends Service {
	public static Context context;
	public boolean lock;
	public WindowManager.LayoutParams sticky;
	public WindowManager windowManager;
	public RelativeLayout onoffView;

	@Override
	public void onCreate() {
		super.onCreate();
	}
	Handler hsetxy=new Handler(){
		@Override
		public void handleMessage(Message msg) {

			HashMap<String,Float> value=(HashMap<String,Float>)(msg.obj);
			sticky.x= value.get("X").intValue();
			sticky.y= value.get("Y").intValue();
			windowManager.updateViewLayout(onoffView,sticky);
		}
	};
	void init_View(){
		SQLite sql=new SQLite(this);
		final SQLiteDatabase db=sql.getReadableDatabase();
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		onoffView =(RelativeLayout)inflater.inflate(R.layout.onoff,null);
		sticky = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSLUCENT);
		sticky.gravity= Gravity.TOP | Gravity.LEFT;
		Cursor c=db.rawQuery("select * from XY",null);
		c.moveToFirst();
		sticky.x=c.getInt(1);;sticky.y=c.getInt(2);
		windowManager.addView(onoffView,sticky);
		onoffView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(!lock) {
					Cursor c=db.rawQuery("select * from IP",null);
					c.moveToFirst();
					new TCP(c.getString(1)).start();

				}
			}
		});
		onoffView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				lock=true;
				return false;
			}
		});
		onoffView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (lock) {
					if (event.getAction() == MotionEvent.ACTION_MOVE) {
						Message msg = new Message();
						HashMap<String, Float> value = new HashMap<String, Float>();
						value.put("X", event.getRawX() - 45);
						value.put("Y", event.getRawY() - 45);
						msg.obj = value;
						hsetxy.sendMessage(msg);
					}
					if (event.getAction() == MotionEvent.ACTION_UP) {
						lock = false;
						SQLite sql = new SQLite(context);
						SQLiteDatabase db = sql.getReadableDatabase();
						ContentValues values = new ContentValues();
						values.put("X", event.getRawX() - 45);
						values.put("Y", event.getRawY() - 45);
						db.update("XY", values, null, null);
					}
				}
				return false;
			}
		});

	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		context=this;
		init_View();
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
