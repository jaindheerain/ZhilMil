package dheerain.jain.zhilmil;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView powerButton;
    View v;
    ArrayList<frequency> freq=new ArrayList<>();
    Camera camera;
    public ticker a=new ticker();
    boolean isClicked=false;
    public int delay=100;
    private boolean isFlashOn=false;
    private Camera.Parameters parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setArrray();
        v=findViewById(android.R.id.content);
        checkFlash();
        powerButton= (ImageView) findViewById(R.id.switchButton);
        getCamera();
        switchButton();
        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFlashOn || isClicked){

                    Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                    a.cancel(true);
                    isClicked=false;
                }
                else {
                    a=new ticker();
                    a.execute();
                    isClicked=true;
                    Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void setArrray() {
        freq.add(new frequency(10,100));
        freq.add(new frequency(14,71));
        freq.add(new frequency(18,55));
        freq.add(new frequency(22,45));
        freq.add(new frequency(26,39));
        freq.add(new frequency(30,34));
        freq.add(new frequency(34,30));
        freq.add(new frequency(38,26));
        freq.add(new frequency(42,23));
        freq.add(new frequency(46,21));
        freq.add(new frequency(50,20));
        freq.add(new frequency(54,19));
        freq.add(new frequency(58,17));
        freq.add(new frequency(62,16));
        freq.add(new frequency(66,14));
        freq.add(new frequency(70,13));

    }

    void checkFlash(){
        if(!getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){

            Snackbar.make(v, "You Dont Have Flash", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            finish();

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
            finish();

        }
    }

    void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                parameters = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Error Camera",e.getMessage());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isFlashOn)
            turnOn();
    }

    @Override
    protected void onPause() {
        super.onPause();
        turnOff();
        camera.release();
    }

    void turnOn() {
        if (!isFlashOn) {
            if (camera == null || parameters == null) {
                return;
            }
            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            isFlashOn = true;
//            switchButton();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        getCamera();
    }

    void turnOff() {
        if (isFlashOn) {
            if (camera == null || parameters == null) {
                return;
            }
            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            isFlashOn = false;
  //          switchButton();
        }
    }

    void switchButton(){
        if(isFlashOn){
            powerButton.setImageResource(R.mipmap.ic_launcher);
        }else{
            powerButton.setImageResource(R.mipmap.ic_launcher_round);
        }
    }


    public void toggleFlashLight() {
        if (!isFlashOn) { // Off, turn it on
            turnOn();
        } else { // On, turn it off
            turnOff();
        }
    }

    public  class ticker extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            a=new ticker();

        }

        @Override
        protected Void doInBackground(Void... params) {
            //t.run();
            for (int i=1; i <i*2; i++) {
                if(isCancelled())break;
                toggleFlashLight();
                try {
                    Thread.sleep(13);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(isFlashOn)
                toggleFlashLight();
        }
    }
        @Override
        protected void onStop() {
            super.onStop();
                if (camera != null) {
                    camera.release();
                    camera = null;
                }
        }
}
