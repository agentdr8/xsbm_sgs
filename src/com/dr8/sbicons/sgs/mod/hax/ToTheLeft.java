package com.dr8.sbicons.sgs.mod.hax;

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

public class ToTheLeft {

	public static void initPackageResources(final XSharedPreferences paramPrefs, XModuleResources modRes, XC_InitPackageResources.InitPackageResourcesParam resParam) {
		try {
			
			String path = "/data/data/com.dr8.sbicons.sgs/xsbm/";
			
			String[] phonearray = {
					"stat_notify_call_mute",
					"stat_sys_phone_call",
					"stat_sys_phone_call_forward",
					"stat_sys_phone_call_on_hold",
					
			};
			
			if (resParam.packageName.equals("com.android.phone")) {
				for (int i = 0; i < phonearray.length; i++) {
					String pimg = "phone/" + phonearray[i] + ".png";
					final Bitmap b = ZipStuff.getBitmap(path, pimg, 0);
					if (b != null) {
						resParam.res.setReplacement("com.android.phone", "drawable", phonearray[i], new XResources.DrawableLoader() {
							@Override
							public Drawable newDrawable(XResources res, int id) throws Throwable {
								if (paramPrefs.getBoolean("misccolor_enabled", false)) {
									BitmapDrawable bd = new BitmapDrawable(null, b);
									bd.setColorFilter(paramPrefs.getInt("misccolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									return bd;
								} else {
									return new BitmapDrawable(null, b);
								}
							}
						});
					}
				}
			}
			
			String[] miscarray = {
					"stat_sys_alarm",
					"stat_notify_image",
					"stat_notify_image_error",
					"stat_sys_no_sim",
					"stat_sys_tty_mode",
					"stat_sys_sync",
					"stat_sys_sync_error"
			};
			
			
			if (resParam.packageName.equals("com.android.systemui")) {
				for (int i = 0; i < miscarray.length; i++) {
					String mimg = "misc/" + miscarray[i] + ".png";
					final Bitmap b = ZipStuff.getBitmap(path, mimg, 240);
					if (b != null) {
						resParam.res.setReplacement("com.android.systemui", "drawable", miscarray[i], new XResources.DrawableLoader() {
							@Override
							public Drawable newDrawable(XResources res, int id) throws Throwable {
								if (paramPrefs.getBoolean("misccolor_enabled", false)) {
									BitmapDrawable bd = new BitmapDrawable(null, b);
									bd.setColorFilter(paramPrefs.getInt("misccolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									return bd;
								} else {
									return new BitmapDrawable(null, b);
								}
							}
						});
					}
					
				
				}
			}
			
		} catch (Throwable t) { XposedBridge.log(t); }
	}
}
