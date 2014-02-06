package com.nigwa.marny;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends SherlockActivity {
	/** Durée d'affichage du SplashScreen */
   protected int _splashTime = 2000;
   private ActionBar _actionBar;

   private Thread splashTread;

   /** Chargement de l'Activity */
   @Override
   public void onCreate(Bundle savedInstanceState) 
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.splash);

      _actionBar = getSupportActionBar();
      _actionBar.hide();
      
      final SplashScreen sPlashScreen = this; 

      /** Thread pour l'affichage du SplashScreen */
      splashTread = new Thread() 
      {
         @Override
         public void run() 
         {
            try 
            {
                 synchronized(this)
                 {
                    wait(_splashTime);
                 }
             } catch(InterruptedException e) {} 
             finally 
             {
                finish();
                Intent i = new Intent();
                i.setClass(sPlashScreen, MainActivity.class);
                startActivity(i);
             }
          }
       };

       splashTread.start();
    }
}
