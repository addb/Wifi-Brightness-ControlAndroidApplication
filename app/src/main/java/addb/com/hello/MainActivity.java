package addb.com.hello;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.net.wifi.WifiManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import static addb.com.hello.R.id.brightnessLevel;
import static addb.com.hello.R.id.seekBrightness;
import static android.os.Build.*;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    TextView WifiStatus, brightLevel;
    Button TurnWifioff,TurnWifion;
    WifiManager wifi;
    private SeekBar p;
    float cBV;int c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkWifiStatus();

        checkBrightness();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);



        return true;
    }
    public void checkWifiStatus()
    {
        TurnWifioff=(Button)findViewById(R.id.dwifi);
        TurnWifion=(Button)findViewById(R.id.ewifi);

        WifiStatus = (TextView)findViewById(R.id.status);



        wifi= (WifiManager) getSystemService(this.WIFI_SERVICE);
        if(wifi.isWifiEnabled()) {
            WifiStatus.setText("Currently Wifi is Enabled");
            TurnWifioff.setEnabled(true);

            TurnWifion.setEnabled(false);

        }
        else {
            WifiStatus.setText("Currently Wifi is Disabled");
            TurnWifioff.setEnabled(false);
            TurnWifion.setEnabled(true);
        }



    }
    public void switchWifiOn(View view)
    {

        TurnWifioff=(Button)findViewById(R.id.dwifi);
        TurnWifion=(Button)findViewById(R.id.ewifi);

        wifi= (WifiManager) getSystemService(this.WIFI_SERVICE);

        wifi.setWifiEnabled(true);
        TurnWifioff.setEnabled(true);
        TurnWifion.setEnabled(false);

        WifiStatus.setText("Currently Wifi is Enabled");
    } public void switchWifiOff(View view)
    {

        TurnWifioff=(Button)findViewById(R.id.dwifi);
        TurnWifion=(Button)findViewById(R.id.ewifi);
        wifi= (WifiManager) getSystemService(this.WIFI_SERVICE);

        wifi.setWifiEnabled(false);
        TurnWifioff.setEnabled(false);
        TurnWifion.setEnabled(true);

        WifiStatus.setText("Currently Wifi is Disabled");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void checkBrightness()
    {
        try {
            cBV=android.provider.Settings.System.getInt(
                    getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        c=(int)((cBV/255)*100);
        brightLevel=(TextView) findViewById(brightnessLevel);
        brightLevel.setText("Brightness: " + c + "%");

        p=(SeekBar) findViewById(seekBrightness);
        p.setProgress(c);
        p.setOnSeekBarChangeListener(this);
        p.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeBrightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





    }
    public void changeBrightness(int p)
    {

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = p / 100.0f;
        getWindow().setAttributes(lp);
        brightLevel=(TextView) findViewById(brightnessLevel);
        brightLevel.setText("Brightness: " + p + "%");


    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        progress+=1;
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onResume()
    {
        super.onResume();
        checkWifiStatus();

        checkBrightness();
    }

}

