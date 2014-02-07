package com.nigwa.marny;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ShopRoomActivity extends SherlockActivity {

	ListView listViewShopRoom;
	List<Weapon> myShop = new ArrayList<Weapon>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoproom);
		
		//Creation ListView
		listViewShopRoom = (ListView)findViewById(R.id.listViewShopRoom);

		String[] listeStrings = {"Armes","Boucliers","Casques", "Potions'"};

		listViewShopRoom.setAdapter(new ArrayAdapter<String>
		(this, android.R.layout.simple_list_item_1,listeStrings));
		
		Button btn_next = (Button) findViewById(R.id.shoproom_btn_next);
		
		
		listViewShopRoom.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(ShopRoomActivity.this, "ListView Selected : "+ 
					arg1.toString()+ " arg2:"+arg2+" arg3:",
						Toast.LENGTH_LONG).show();
				if(arg2 == 0) {
					//Affichage Armes
					startActivity(new Intent(ShopRoomActivity.this, 
							ShopWeaponActivity.class));
				} else if(arg2 == 1) {
					//Affichage Boucliers
					startActivity(new Intent(ShopRoomActivity.this, 
							MonsterActivity.class));
				} else if(arg2 == 2) {
					//Affichage Casques
					startActivity(new Intent(ShopRoomActivity.this, 
							MonsterActivity.class));
				} else {
					//Affichage Potions
					startActivity(new Intent(ShopRoomActivity.this, 
							MonsterActivity.class));
				}
				
			}
		});
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int myRandom = Tools.random(20);
				if(myRandom >= 0 && myRandom <= 12) {
					startActivity(new Intent(ShopRoomActivity.this, 
							MonsterActivity.class));
				} else if(myRandom >= 13 && myRandom <= 16 ) {
					startActivity(new Intent(ShopRoomActivity.this, 
							GoldRoomActivity.class));
				} else if(myRandom >= 17 && myRandom <= 18 ) {
					startActivity(new Intent(ShopRoomActivity.this, 
							ShopRoomActivity.class));
				} else {
					startActivity(new Intent(ShopRoomActivity.this, 
							RestRoomActivity.class));
				}
			}
		});
	}
	private void RemplirMonShop() {

		/*myShop.clear();

		myShop.add(new Weapon("", "William-C Dietz"));

		myShop.add(new Weapon("L'art du développement Android", "Mark Murphy"));

		myShop.add(new Weapon("Le seuil des ténèbres", "Karen Chance"));*/

	}

}
