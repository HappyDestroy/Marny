package com.nigwa.marny;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class CompassRoomActivity extends SherlockActivity 
	implements SensorEventListener {

	//L'image de la boussole
    private ImageView image;

    //L'angle de la boussole
    private float currentDegree = 0f;

    //Le sensor manager
    private SensorManager mSensorManager;
    
    //La vibration
    private Vibrator vibe;

    private TextView txt_info;
    private Button btn_next;
    private Hero myHero;
	private int nb_room;
	private int health_left;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compassroom);
		
		vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		
		//On récupère la textView pour afficher les messages
		txt_info = (TextView) findViewById(
				R.id.compassroom_txtview_info);
		
		btn_next = (Button) findViewById(R.id.compassroom_btn_next);
		btn_next.setEnabled(false);
		
		//On récupère l'image de la boussole
        image = (ImageView) findViewById(R.id.compassroom_imgview_compass);
        
        //On initialise le sensor manager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        //On les intents
		myHero = (Hero) getIntent().getSerializableExtra("hero");
	      		
		health_left = getIntent().getIntExtra("health_left", 1); 
      		
		nb_room = getIntent().getIntExtra("nb_room", 1);
		nb_room++;
        
        btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Tools.getNextRoom(CompassRoomActivity.this, myHero, nb_room, 
						health_left);
			}
		});
		
	}


    @SuppressWarnings("deprecation")
	@Override
    protected void onResume() {
    	super.onResume();
    	mSensorManager.registerListener(this, mSensorManager
        		.getDefaultSensor(Sensor.TYPE_ORIENTATION),
        			SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
    	super.onPause();
        
    	//Arrête le sensor manager
    	mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //On récupère la valeur de l'orientation de la boussole
        float degree = Math.round(event.values[0]);

        int myRandom = Tools.random(360);
        
        if(degree == myRandom) {
        	vibe.vibrate(200);
        	txt_info.setText(getApplicationContext().getString(
        			R.string.exitfound));
        	btn_next.setEnabled(true);
        }
        
        //Animation pour la boussole
        RotateAnimation ra = new RotateAnimation(
                currentDegree, 
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f, 
                Animation.RELATIVE_TO_SELF,
                0.5f);

        //La durée de l'animation
        ra.setDuration(210);

        
        ra.setFillAfter(true);

        //On lance l'animation
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        
    }
    
    /**
	 * Sur l'evènement du bouton "Back" (Pour le desactiver)
	 */
	@Override
	public void onBackPressed() {
		Toast.makeText(this, getApplication().getString(R.string.btn_back), 
				Toast.LENGTH_LONG).show();
		return;
	}

}
