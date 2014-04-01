package com.dr8.sbicons.sgs.mod.hax;

import android.annotation.SuppressLint;
import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.dr8.sbicons.sgs.mod.ZipStuff;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;

@SuppressLint("SdCardPath")
public class AppIcons {

	public static void initPackageResources(final XSharedPreferences paramPrefs, XC_InitPackageResources.InitPackageResourcesParam resParam, String rep, XModuleResources modres) {
		try {
			String target = resParam.packageName;
			String path = "/data/data/com.dr8.sbicons.sgs/xsbm/";
			
	    	String repl = "apps/" + target + "-" + rep;
//	    	Log.d("XSBM", "getting image from zip: " + repl);
			final Bitmap a = ZipStuff.getBitmap(path, repl, 0);	        	
    		if (a != null) {
    			String noext = rep.substring(0, rep.length() - 4);
//    			Log.d("XSBM", " our rep minus extension " + noext);
    			resParam.res.setReplacement(target, "drawable", noext, new XResources.DrawableLoader() {
				@Override
				public Drawable newDrawable(XResources res, int id) throws Throwable {
					if (paramPrefs.getBoolean("appscolor_enabled", false)) {
						BitmapDrawable bd = new BitmapDrawable(null, a);
						bd.setColorFilter(paramPrefs.getInt("appscolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
						return bd;
					} else {
						return new BitmapDrawable(null, a);
					}
				}
    			});
        	}
	    } catch (Throwable t) { XposedBridge.log(t); }
	}
}
