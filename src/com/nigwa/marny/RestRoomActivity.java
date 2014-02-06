package com.nigwa.marny;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class RestRoomActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restroom);
		
		Button btn_next = (Button) findViewById(R.id.restroom_btn_next);
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int myRandom = Tools.random(20);
				if(myRandom >= 0 && myRandom <= 12) {
					startActivity(new Intent(RestRoomActivity.this, MonsterActivity.class));
				} else if(myRandom >= 13 && myRandom <= 16 ) {
					startActivity(new Intent(RestRoomActivity.this, GoldRoomActivity.class));
				} else if(myRandom >= 17 && myRandom <= 18 ) {
					startActivity(new Intent(RestRoomActivity.this, ShopRoomActivity.class));
				} else {
					startActivity(new Intent(RestRoomActivity.this, RestRoomActivity.class));
				}
			}
		});
	}

}
