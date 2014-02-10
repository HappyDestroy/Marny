package com.nigwa.marny;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockActivity;

public class ShopPotionActivity extends Activity {
private Hero myHero;
	//int i = 1;
	/*protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoppotion);
		
		
		myHero = (Hero) getIntent().getSerializableExtra("hero");
		
	}*/
	ImageView imageView;

	 Integer[] image = { R.drawable.coin_1, R.drawable.coin_2,R.drawable.coin_3,R.drawable.coin_3,R.drawable.coin_3,R.drawable.coin_3,R.drawable.coin_3,R.drawable.coin_3 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_shoppotion);

	   ImageView img_coin= (ImageView) findViewById(R.id.imageCoin);

	   for(int i = 1;i<8;i++){
		   img_coin.setImageResource(image[i]);
	        }
	     }
	   
	}

