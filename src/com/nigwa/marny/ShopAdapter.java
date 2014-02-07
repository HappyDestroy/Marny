package com.nigwa.marny;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ShopAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * Interface pour écouter les évènements sur le shop
	 */
	public interface ShopAdapterListener {
	    public void onClickNom(Weapon item, int position);
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
