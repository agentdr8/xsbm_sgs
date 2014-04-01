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

public class Ringer {

	public static void initPackageResources(final XSharedPreferences paramPrefs, XModuleResources modRes, XC_InitPackageResources.InitPackageResourcesParam resParam) {
		try {

			String path = "/data/data/com.dr8.sbicons.sgs/xsbm/";
			final int density = paramPrefs.getInt("density", 0);

			String[] ringarr = {
					"stat_sys_ringer_silent",
					"stat_sys_ringer_vibrate"
			};

			if (resParam.packageName.equals("com.android.systemui")) {
				for (int i = 0; i < ringarr.length; i++) {
					String mimg = "misc/" + ringarr[i] + ".png";
					final Bitmap b = ZipStuff.getBitmap(path, mimg, density);
					if (b != null) {
						resParam.res.setReplacement("com.android.systemui", "drawable", ringarr[i], new XResources.DrawableLoader() {
							@Override
							public Drawable newDrawable(XResources res, int id) throws Throwable {
								if (paramPrefs.getBoolean("ringcolor_enabled", false)) {
									BitmapDrawable bd = new BitmapDrawable(null, b);
									bd.setColorFilter(paramPrefs.getInt("ringcolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
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