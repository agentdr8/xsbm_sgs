package com.dr8.sbicons.sgs.mod;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import com.dr8.sbicons.sgs.mod.IconPackActivity;
import com.dr8.sbicons.sgs.mod.StatusBarModsSettings;
import com.dr8.sbicons.sgs.R;
import com.stericson.RootTools.RootTools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.widget.Toast;

@SuppressLint("WorldReadableFiles")
public class StatusBarModsSettings extends Activity {

	public static SharedPreferences prefs = null;
	public static Context contextOfApplication;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTitle(R.string.app_name);
		super.onCreate(savedInstanceState);
		
		// Display the fragment as the main content.
        if (savedInstanceState == null)
			getFragmentManager().beginTransaction().replace(android.R.id.content,
	                new PrefsFragment()).commit();
        
//
//    	int ALERT_ICON = R.drawable.update;
//    	UpdateChecker uc = new UpdateChecker(this, VERSION_URL, REMOTE_APK_URL, ALERT_ICON);
//    	uc.startUpdateChecker();
        prefs = getSharedPreferences("com.dr8.sbicons.sgs_preferences", MODE_WORLD_READABLE);
        
        if (prefs.getInt("sbh", 0) == 0) {
    		int sbheight = getStatusBarHeight();
    		prefs.edit().putInt("sbh", sbheight).commit();
    	}
        contextOfApplication = getApplicationContext();
	}
	
	public static Context getContextOfApplication(){
	    return contextOfApplication;
	}
	
	public int getStatusBarHeight() {
	      int result = 0;
	      int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
	      if (resourceId > 0) {
	          result = getResources().getDimensionPixelSize(resourceId);
	      }
	      return result;
	}
	
	public static class PrefsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// this is important because although the handler classes that read these settings
			// are in the same package, they are executed in the context of the hooked package
			getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
			addPreferencesFromResource(R.xml.preferences);
		}
	}
	
	@Override
    protected void onResume() {
        super.onResume();

        String intpath = getApplicationContext().getFilesDir().getParent() + "/xsbm/";
        File f = new File(intpath);
        if (prefs.getBoolean("firstrun", true) && !f.exists()) {
			Toast.makeText(StatusBarModsSettings.this, "This appears to be your first time\nlaunching XSBM. Setting up default iconpack..", Toast.LENGTH_LONG).show();
			Intent i = new Intent(contextOfApplication, IconPackActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			contextOfApplication.startActivity(i);
        } else {
        	prefs.edit().putBoolean("firstrun", false).commit();
        }
    }
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onBackPressed() {
	    doExit();
	}
	
	private void doExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apply changes?")
        .setIcon(R.drawable.question)
        .setCancelable(false)
        .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (RootTools.isBusyboxAvailable()) {
					if (RootTools.isRootAvailable()) {
						Toast.makeText(StatusBarModsSettings.this, "Restarting SystemUI and TouchWiz...", Toast.LENGTH_SHORT).show();
						killPackage("com.android.systemui");
						killPackage("com.sec.android.app.launcher");
						StatusBarModsSettings.this.finish();
					} else {
						Toast.makeText(StatusBarModsSettings.this, "Your phone is not rooted", Toast.LENGTH_SHORT).show();
						StatusBarModsSettings.this.finish();
					}
				} else {
					RootTools.offerBusyBox(StatusBarModsSettings.this);
				}				
			}
		})
        .setNegativeButton("Exit",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                StatusBarModsSettings.this.finish();            
            }
        });
        
        AlertDialog alert = builder.create();
        alert.show();
	}
	
	 
    // killPackage code by serajr @XDA 
    // http://forum.xda-developers.com/showthread.php?p=44176299#post44176299
    private void killPackage(String packageToKill) { 
        Process su = null; 
        // get superuser 
        try { 
            su = Runtime.getRuntime().exec("su"); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
         
        // kill given package 
        if (su != null ){ 
            try { 
                DataOutputStream os = new DataOutputStream(su.getOutputStream());  
                os.writeBytes("pkill -f " + packageToKill + "\n"); 
                os.flush(); 
                os.writeBytes("exit\n"); 
                os.flush(); 
                su.waitFor(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            } 
        } 
    }
	
}
