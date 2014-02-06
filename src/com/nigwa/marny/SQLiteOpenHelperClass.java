package com.nigwa.marny;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteOpenHelperClass extends SQLiteOpenHelper {

	public SQLiteOpenHelperClass(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(HeroContract.SCHEMA);
		db.execSQL(MonsterContract.SCHEMA);
		db.execSQL(HelmetContract.SCHEMA);
		db.execSQL(ShieldContract.SCHEMA);
		db.execSQL(WeaponContract.SCHEMA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < newVersion) {
			db.delete(HeroContract.TABLE, null, null);
			db.delete(MonsterContract.TABLE, null, null);
			db.delete(HelmetContract.TABLE, null, null);
			db.delete(ShieldContract.TABLE, null, null);
			db.delete(WeaponContract.TABLE, null, null);
		}
	}
}
