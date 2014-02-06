package com.nigwa.marny;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class GoldRoomActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goldroom);
		
		Button btn_next = (Button) findViewById(R.id.goldroom_btn_next);
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int myRandom = Tools.random(20);
				if(myRandom >= 0 && myRandom <= 12) {
					startActivity(new Intent(GoldRoomActivity.this, MonsterActivity.class));
				} else if(myRandom >= 13 && myRandom <= 16 ) {
					startActivity(new Intent(GoldRoomActivity.this, GoldRoomActivity.class));
				} else if(myRandom >= 17 && myRandom <= 18 ) {
					startActivity(new Intent(GoldRoomActivity.this, ShopRoomActivity.class));
				} else {
					startActivity(new Intent(GoldRoomActivity.this, RestRoomActivity.class));
				}
			}
		});
	}


}
