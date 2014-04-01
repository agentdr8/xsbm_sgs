package com.dr8.sbicons.sgs.mod.hax;

import com.dr8.sbicons.sgs.R;
import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;

public class TpNotif43 {

	
	public static void initPackageResources(final XSharedPreferences paramPrefs, XModuleResources modRes, XC_InitPackageResources.InitPackageResourcesParam resParam) {
		try {
			if (resParam.packageName.equals("com.android.systemui")) {
					String targetpkg = "com.android.systemui";
					final Bitmap bm = BitmapFactory.decodeResource(modRes, R.drawable.notification_header_bg);
					final int sbcolor = paramPrefs.getInt("tpnotif", 0x77000000);
					resParam.res.setReplacement(targetpkg , "drawable", "notification_panel_bg", new XResources.DrawableLoader() {
						@Override
						public Drawable newDrawable(XResources res, int id) throws Throwable {
							if (paramPrefs.getBoolean("tpnotif_enabled", false)) {
								return new ColorDrawable(sbcolor);
							} else {
								return new BitmapDrawable(null, bm);
							}
						}
					});
					final Bitmap bm2 = BitmapFactory.decodeResource(modRes, R.drawable.notification_header_bg);
					final int sbcolor2 = paramPrefs.getInt("tpnotif", 0x77000000);
					resParam.res.setReplacement(targetpkg , "drawable", "notification_header_bg", new XResources.DrawableLoader() {
						@Override
						public Drawable newDrawable(XResources res, int id) throws Throwable {
							if (paramPrefs.getBoolean("tpnotif_enabled", false)) {
								return new ColorDrawable(sbcolor2);
							} else {
								return new BitmapDrawable(null, bm2);
							}
						}
					});
//					resParam.res.setReplacement(targetpkg , "drawable", "notification_panel_bg", modRes.fwd(R.drawable.notification_header_bg));
//					resParam.res.setReplacement(targetpkg , "drawable", "notification_header_bg", modRes.fwd(R.drawable.notification_header_bg));
			} 
		} catch (Throwable t) { 
			XposedBridge.log(t);
		}
	}
}
