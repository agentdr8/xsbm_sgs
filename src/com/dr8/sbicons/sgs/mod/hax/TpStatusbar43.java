package com.dr8.sbicons.sgs.mod.hax;

import com.dr8.sbicons.sgs.R;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;

public class TpStatusbar43 {

	public static void initPackageResources(final XSharedPreferences paramPrefs, final XModuleResources modRes, XC_InitPackageResources.InitPackageResourcesParam resParam) {
		try {
			if (resParam.packageName.equals("com.android.systemui")) {
				String targetpkg = "com.android.systemui";
				try {
//					final Bitmap bm = BitmapFactory.decodeResource(modRes, R.drawable.status_bar_bg_tile);
					final int sbcolor = paramPrefs.getInt("tpstatus", 0x77000000);
					//					resParam.res.setReplacement(targetpkg , "drawable", "status_bar_bg_tile", new XResources.DrawableLoader() {
					//						@Override
					//						public Drawable newDrawable(XResources res, int id) throws Throwable {
					//							if (paramPrefs.getBoolean("tpstatus_enabled", false)) {
					//								return new ColorDrawable(sbcolor);
					//							} else {
					//								return new BitmapDrawable(null, bm);
					//							}
					//						}
					//					});
					resParam.res.setReplacement(targetpkg , "drawable", "status_bar_background", new XResources.DrawableLoader() {
						@Override
						public Drawable newDrawable(XResources res, int id) throws Throwable {
							return new ColorDrawable(sbcolor);
						}
					});
				} catch (Throwable t) { 
					XposedBridge.log(t);
				}
			}
			if (resParam.packageName.equals("com.sec.android.app.launcher")) {
				String targetpkg = "com.sec.android.app.launcher";
				try {
					final Bitmap bm = BitmapFactory.decodeResource(modRes, R.drawable.indicator_bg);
					resParam.res.setReplacement(targetpkg, "drawable", "indicator_bg", new XResources.DrawableLoader() {
						@Override
						public Drawable newDrawable(XResources res, int id) throws Throwable {
							final int sbcolor = paramPrefs.getInt("tpstatus", 0x77000000);
							final BitmapDrawable bd = new BitmapDrawable(null, bm);
							bd.setColorFilter(sbcolor, PorterDuff.Mode.MULTIPLY);
							return bd;
						}
					});
				} catch (Throwable t) { 
					XposedBridge.log(t);
				}
			}
		} catch (Throwable t) {
			XposedBridge.log(t);
		}
	}
}