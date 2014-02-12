package com.nigwa.marny;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends SherlockActivity {
	
	/** Durée d'affichage du SplashScreen */
	protected int splashTime = 2000;
   private ActionBar actionBar;

   private Thread splashTread;

   /**
    * A la création de l'activity
    */
   @Override
   public void onCreate(Bundle savedInstanceState) 
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.splash);

      actionBar = getSupportActionBar();
      actionBar.hide();
      
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
                    wait(splashTime);
                 }
             } catch(InterruptedException e) {} 
             finally 
             {
                finish();
                Intent myIntent = new Intent();
                myIntent.setClass(SplashScreen.this, MainActivity.class);
                startActivity(myIntent);
             }
          }
       };

       splashTread.start();
    }
}
