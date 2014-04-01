package com.dr8.sbicons.sgs.mod;

import com.dr8.sbicons.sgs.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class AboutActivity extends Activity
{
    private Context mCtx;
    private String curVersion;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		try {
			mCtx = this.getApplicationContext();
			curVersion = mCtx.getPackageManager().getPackageInfo(mCtx.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_shortname) + " - v" + 
        		curVersion + "\n   by " + getResources().getString(R.string.author))
        .setIcon(R.drawable.about)
        .setMessage(getResources().getString(R.string.credits))
        .setCancelable(false)
        .setNegativeButton("Close",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                AboutActivity.this.finish();
            }
        });
        
        AlertDialog alert = builder.create();
        alert.show();
    }
        
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
        AboutActivity.this.finish();
    }
}
