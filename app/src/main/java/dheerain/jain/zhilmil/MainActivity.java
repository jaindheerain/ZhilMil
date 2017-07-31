package dheerain.jain.zhilmil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MainActivity extends AppCompatActivity implements Communicator{

    ImageView powerButton;
    View v;
    ArrayList<frequency> freq=new ArrayList<>();
    Camera camera;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public ticker a=new ticker();
    boolean isClicked=false;
    public static int delay=100;
    private boolean isFlashOn=false;
    private Camera.Parameters parameters;
    Switch aSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        aSwitch= (Switch) findViewById(R.id.aSwitch);
        v=findViewById(android.R.id.content);
        powerButton= (ImageView) findViewById(R.id.switchButton);
        checkFlash();
        setArrray();
        getCamera();
        powerButton.setImageResource(R.drawable.energyofff);
        sharedPreferences=getSharedPreferences("enable", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        aSwitch.setChecked(sharedPreferences.getBoolean("key",false));
        recyclerView.setVisibility(View.INVISIBLE);
        if(sharedPreferences.getBoolean("run",true)){
            intro();

        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
        }
        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFlashOn || isClicked){

                    switchButton();
                    Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
                    a.cancel(true);
                    isClicked=false;
                }
                else {
                    switchButton();
                    a=new ticker();
                    a.execute();
                    isClicked=true;
                    Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();

                }
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("key",aSwitch.isChecked());
                editor.apply();
                if(!aSwitch.isChecked()){
                    if(!a.isCancelled()){
                        a.cancel(true);
                        powerButton.setImageResource(R.drawable.energyofff);
                        isClicked=false;

                    }
                }

            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        FrequencyAdapter frequencyAdapter = new FrequencyAdapter(freq,this,sharedPreferences);
        recyclerView.setAdapter(frequencyAdapter);

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
        if(isClicked){
            powerButton.setImageResource(R.drawable.energyofff);
        }else{
            powerButton.setImageResource(R.drawable.energyonn);
        }
    }


    public void toggleFlashLight() {
        if (!isFlashOn) { // Off, turn it on
            turnOn();
        } else { // On, turn it off
            turnOff();
        }
    }

    @Override
    public void doTask() {
            powerButton.setImageResource(R.drawable.energyonn);
            a.cancel(true);
            a=new ticker();
            a.execute();
        isClicked=true;
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
                    Thread.sleep(delay);
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
            if(!a.isCancelled())
                a.cancel(true);

            if (camera != null) {
                    isClicked=false;
                    camera.release();
                    camera = null;
                }
        }

    @Override
    protected void onStart() {
        super.onStart();
        getCamera();
    }

    private void intro() {
        new MaterialTapTargetPrompt.Builder(MainActivity.this)
                .setTarget(findViewById(R.id.aSwitch))
                .setPrimaryText("Enable Live Preview")
                .setSecondaryText("Tap to enable the preview option for frequency you choose")
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        {
                            editor.putBoolean("key",aSwitch.isChecked());
                            editor.apply();
                            // User has pressed the prompt target

                            recyclerView.setVisibility(View.VISIBLE);

                        }
                        if(state==MaterialTapTargetPrompt.STATE_DISMISSED)
                        {
                            recyclerView.setVisibility(View.VISIBLE);

                        }
                    }
                })
                .show();

        editor.putBoolean("run",false);
        editor.apply();
    }
}
