package com.nigwa.marny;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class ShopPotionActivity extends SherlockActivity {
	
	ImageView imageView;

	 Integer[] image = { R.drawable.coin_1, R.drawable.coin_2, 
			 R.drawable.coin_3, 
			 R.drawable.coin_4, 
			 R.drawable.coin_5, 
			 R.drawable.coin_6, 
			 R.drawable.coin_7, 
			 R.drawable.coin_8 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_shoppotion);

	   ImageView img_coin= (ImageView) findViewById(R.id.imageCoin);

	   for(int i = 1;i<8;i++) {
		   img_coin.setImageResource(image[i]);
        }
	}	   
}