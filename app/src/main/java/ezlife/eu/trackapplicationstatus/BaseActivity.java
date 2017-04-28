package ezlife.eu.trackapplicationstatus;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ajo on 28.04.17.
 * This class handles tracking when the app is going to
 * background or foreground.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static final String TAG = BaseActivity.class.getName();

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static boolean isAppWentToBg = false;
    public static boolean isWindowFocused = false;
    public static boolean isBackPressed = false;

    @Override
    protected void onStart() {
        applicationWillEnterForeground();
        super.onStart();
    }

    private void applicationWillEnterForeground() {
        if (isAppWentToBg) {
            isAppWentToBg = false;
            Toast.makeText(getApplicationContext(), "App is in Foreground",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        applicationDidEnterBackground();
    }

    public void applicationDidEnterBackground() {
        if (!isWindowFocused) {
            isAppWentToBg = true;
            Toast.makeText(getApplicationContext(), "App is in Background",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (this instanceof MainActivity) {
        } else {
            isBackPressed = true;
        }
        Log.d(TAG, "onBackPressed " + isBackPressed + "" + this.getLocalClassName());
        super.onBackPressed();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        isWindowFocused = hasFocus;
        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }
        super.onWindowFocusChanged(hasFocus);
    }

}