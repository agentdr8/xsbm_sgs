package com.dr8.sbicons.sgs.mod.hax;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import static de.robv.android.xposed.XposedHelpers.getIntField;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.dr8.sbicons.sgs.mod.ZipStuff;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MobileDataSGS {

	private static int nettype = 0;

	private static final String gicon = "stat_sys_data_g_connected.png";
	private static final String eicon = "stat_sys_data_e_connected.png";
	private static final String threegicon = "stat_sys_data_3g_connected.png";
	private static final String hicon = "stat_sys_data_h_connected.png";
	private static final String fourgicon = "stat_sys_data_4g_connected.png";
	private static final String lteicon = "stat_sys_data_lte_connected.png";

	public static void initHandleLoadPackage(final XSharedPreferences paramPrefs, XC_LoadPackage.LoadPackageParam lpParam) {

		final String path = "/data/data/com.dr8.sbicons.sgs/xsbm/";
		findAndHookMethod("com.android.systemui.statusbar.policy.NetworkController", lpParam.classLoader, "updateATTDataNetType", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				try { 
					nettype = (Integer) getIntField(param.thisObject, "mDataNetType");
//					XposedBridge.log("XSBM: our nettype is " + nettype);
				} catch (Throwable t) { XposedBridge.log(t); }
			}
		});

		if (paramPrefs.getBoolean("mobile_data", false)) {
			findAndHookMethod("com.android.systemui.statusbar.SignalClusterView", lpParam.classLoader, "apply", new XC_MethodHook() {
				@Override
				protected void afterHookedMethod(MethodHookParam param) throws Throwable {
					try {
						ImageView iv = (ImageView) getObjectField(param.thisObject, "mMobileType");
						if (iv != null) {
							switch (nettype) {
							case 1:
								String gimg = "mobile/g/" + gicon;
								final Bitmap gb = ZipStuff.getBitmap(path, gimg, 240);
								if (gb != null) {
									Drawable gd = new BitmapDrawable(null, gb);
									if (paramPrefs.getBoolean("mobilecolor_enabled", false)) {
										gd.setColorFilter(paramPrefs.getInt("mobilecolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									}
									iv.setImageDrawable(gd);
								}
								break;
							case 2:
								String eimg = "mobile/e/" + eicon;
								final Bitmap eb = ZipStuff.getBitmap(path, eimg, 240);
								if (eb != null) {
									Drawable ed = new BitmapDrawable(null, eb);
									if (paramPrefs.getBoolean("mobilecolor_enabled", false)) {
										ed.setColorFilter(paramPrefs.getInt("mobilecolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									}
									iv.setImageDrawable(ed);
								}
								break;
							case 3:
								String tgimg = "mobile/3g/" + threegicon;
								final Bitmap tgb = ZipStuff.getBitmap(path, tgimg, 240);
								if (tgb != null) {
									Drawable tgd = new BitmapDrawable(null, tgb);
									if (paramPrefs.getBoolean("mobilecolor_enabled", false)) {
										tgd.setColorFilter(paramPrefs.getInt("mobilecolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									}
									iv.setImageDrawable(tgd);
								}
								break;
							case 8:
							case 10:
								String fgimg = "mobile/4g/" + fourgicon;
								final Bitmap fgb = ZipStuff.getBitmap(path, fgimg, 240);
								if (fgb != null) {
									Drawable fgd = new BitmapDrawable(null, fgb);
									if (paramPrefs.getBoolean("mobilecolor_enabled", false)) {
										fgd.setColorFilter(paramPrefs.getInt("mobilecolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									}
									iv.setImageDrawable(fgd);
								}
								break;
							case 13:
								String limg = "mobile/lte/" + lteicon;
								final Bitmap lb = ZipStuff.getBitmap(path, limg, 240);
								if (lb != null) {
									Drawable ld = new BitmapDrawable(null, lb);
									if (paramPrefs.getBoolean("mobilecolor_enabled", false)) {
										ld.setColorFilter(paramPrefs.getInt("mobilecolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									}
									iv.setImageDrawable(ld);
								}
								break;
							case 15:
								String himg = "mobile/h/" + hicon;
								final Bitmap hb = ZipStuff.getBitmap(path, himg, 240);
								if (hb != null) {
									Drawable hd = new BitmapDrawable(null, hb);
									if (paramPrefs.getBoolean("mobilecolor_enabled", false)) {
										hd.setColorFilter(paramPrefs.getInt("mobilecolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									}
									iv.setImageDrawable(hd);
								}
								break;
							}
						} else {
							return;
						}
					} catch (Throwable t) { XposedBridge.log(t); }
				}
			});	
		}	
	}
}
