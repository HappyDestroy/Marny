package com.nigwa.marny;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class ShakeRoomActivity extends SherlockActivity  {

	private Hero myHero;
	private int nb_room;
	private int health_left;
	
	private ShakeListener mShaker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shakeroom);
		
		final TextView txt_info = (TextView) findViewById
				(R.id.shakeroom_txt_info);
		final Button btn_next = (Button) findViewById(
				R.id.shakeroom_btn_next);
		btn_next.setEnabled(false);
		
		//On les intents
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
		health_left = getIntent().getIntExtra("health_left", 1); 
		
		nb_room = getIntent().getIntExtra("nb_room", 1);
		nb_room++;
		
		
		final Vibrator vibe = (Vibrator)getSystemService(
				Context.VIBRATOR_SERVICE);

		mShaker = new ShakeListener(this);
		
		/**
		 * Listener pour le "secouage" du téléphone
		 */
		mShaker.setOnShakeListener(new ShakeListener.OnShakeListener () {
			public void onShake() {
				vibe.vibrate(200);
				btn_next.setEnabled(true);
				txt_info.setText(getApplicationContext().getString(
						R.string.door_open));
			}
		});
		
		
		/**
		 * Listener du click sur le bouton pour changer de salle
		 */
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Tools.getNextRoom(
						ShakeRoomActivity.this, myHero, nb_room, health_left);
			}
		});
	}
	
	@Override
	public void onResume()
	{
		mShaker.resume();
		super.onResume();
	}
	  
	  
	@Override
	public void onPause()
	{
		mShaker.pause();
		super.onPause();
	}
}
