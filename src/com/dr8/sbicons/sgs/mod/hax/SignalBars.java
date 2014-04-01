package com.dr8.sbicons.sgs.mod.hax;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.dr8.sbicons.sgs.mod.ZipStuff;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;

public class SignalBars {

	public static void initPackageResources(final XSharedPreferences paramPrefs, XModuleResources modRes, XC_InitPackageResources.InitPackageResourcesParam resParam) {
		try {
			String targetpkg = "com.android.systemui";
			String path = "/data/data/com.dr8.sbicons.sgs/xsbm/";

			String[] sixsignalarray = {
					"tw_stat_sys_6_level_signal_0_fully",
					"tw_stat_sys_6_level_signal_0",
					"tw_stat_sys_6_level_signal_1_fully",
					"tw_stat_sys_6_level_signal_1",
					"tw_stat_sys_6_level_signal_2_fully",
					"tw_stat_sys_6_level_signal_2",
					"tw_stat_sys_6_level_signal_3_fully",
					"tw_stat_sys_6_level_signal_3",
					"tw_stat_sys_6_level_signal_4_fully",
					"tw_stat_sys_6_level_signal_4",
					"tw_stat_sys_6_level_signal_5_fully",
					"tw_stat_sys_6_level_signal_5",
					"tw_stat_sys_6_level_signal_6_fully",
					"tw_stat_sys_6_level_signal_6",
					"tw_stat_sys_signal_null",
					"stat_sys_signal_flightmode"
			};
			
			String[] fivesignalarray = {
					"tw_stat_sys_5_level_signal_0_fully",
					"tw_stat_sys_5_level_signal_0",
					"tw_stat_sys_5_level_signal_1_fully",
					"tw_stat_sys_5_level_signal_1",
					"tw_stat_sys_5_level_signal_2_fully",
					"tw_stat_sys_5_level_signal_2",
					"tw_stat_sys_5_level_signal_3_fully",
					"tw_stat_sys_5_level_signal_3",
					"tw_stat_sys_5_level_signal_4_fully",
					"tw_stat_sys_5_level_signal_4",
					"tw_stat_sys_5_level_signal_5_fully",
					"tw_stat_sys_5_level_signal_5",
					"tw_stat_sys_signal_null",
					"stat_sys_signal_flightmode"
			};

			String[] foursignalarray = {
					"stat_sys_signal_0_fully",
					"stat_sys_signal_0",
					"stat_sys_signal_1_fully",
					"stat_sys_signal_1",
					"stat_sys_signal_2_fully",
					"stat_sys_signal_2",
					"stat_sys_signal_3_fully",
					"stat_sys_signal_3",
					"stat_sys_signal_4_fully",
					"stat_sys_signal_4",
					"stat_sys_signal_null",
					"stat_sys_signal_flightmode"
			};

			//			String[] roamarray = {
			//					"stat_sys_r_5signal_0.png",
			//					"stat_sys_r_5signal_1.png",
			//					"stat_sys_r_5signal_2.png",
			//					"stat_sys_r_5signal_3.png",
			//					"stat_sys_r_5signal_4.png",
			//					"stat_sys_r_5signal_5.png"
			//					};
			switch(paramPrefs.getInt("signal_bars", 5)) {
			case 5:
				for (int i = 0; i < fivesignalarray.length; i++) {
					String simg = "signal/" + fivesignalarray[i] + ".png";
					final Bitmap s = ZipStuff.getBitmap(path, simg, 240);
					final BitmapDrawable sd = new BitmapDrawable(null, s);
					if (s != null) {
						resParam.res.setReplacement(targetpkg, "drawable", fivesignalarray[i], new XResources.DrawableLoader() {
							@Override
							public Drawable newDrawable(XResources res, int id) throws Throwable {
								if (paramPrefs.getBoolean("signalcolor_enabled", false)) {
									sd.setColorFilter(paramPrefs.getInt("signalcolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									return sd;
								} else {
									return new BitmapDrawable(null, s);
								}
							}
						});
					} else {
						return;
					}
				}
				break;
			case 4:
				for (int i = 0; i < foursignalarray.length; i++) {
					String simg = "signal/" + foursignalarray[i] + ".png";
					final Bitmap s = ZipStuff.getBitmap(path, simg, 240);
					final BitmapDrawable sd = new BitmapDrawable(null, s);
					if (s != null) {
						resParam.res.setReplacement(targetpkg, "drawable", foursignalarray[i], new XResources.DrawableLoader() {
							@Override
							public Drawable newDrawable(XResources res, int id) throws Throwable {
								if (paramPrefs.getBoolean("signalcolor_enabled", false)) {
									sd.setColorFilter(paramPrefs.getInt("signalcolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									return sd;
								} else {
									return new BitmapDrawable(null, s);
								}
							}
						});
					} else {
						return;
					}
				}
				break;
			case 6:
				for (int i = 0; i < sixsignalarray.length; i++) {
					String simg = "signal/" + sixsignalarray[i] + ".png";
					final Bitmap s = ZipStuff.getBitmap(path, simg, 240);
					final BitmapDrawable sd = new BitmapDrawable(null, s);
					if (s != null) {
						resParam.res.setReplacement(targetpkg, "drawable", sixsignalarray[i], new XResources.DrawableLoader() {
							@Override
							public Drawable newDrawable(XResources res, int id) throws Throwable {
								if (paramPrefs.getBoolean("signalcolor_enabled", false)) {
									sd.setColorFilter(paramPrefs.getInt("signalcolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
									return sd;
								} else {
									return new BitmapDrawable(null, s);
								}
							}
						});
					} else {
						return;
					}
				}
				break;
			}

			//			for (int i = 0; i < roamarray.length; i++) {
			//				String rimg = "signal/roam/" + roamarray[i];
			//				final Bitmap r = ZipStuff.getBitmap(path, rimg);
			//				if (r != null) {
			//						resParam.res.setReplacement(targetpkg, "drawable", "stat_sys_r_5signal_" + i, new XResources.DrawableLoader() {
			//						@Override
			//						public Drawable newDrawable(XResources res, int id) throws Throwable {
			//							if (paramPrefs.getBoolean("signalcolor_enabled", false)) {
			//								BitmapDrawable rd = new BitmapDrawable(null, r);
			//								rd.setColorFilter(paramPrefs.getInt("signalcolor", 0xffffffff), PorterDuff.Mode.MULTIPLY);
			//								return rd;
			//							} else {
			//								return new BitmapDrawable(null, r);
			//							}
			//						}
			//					});
			//				}
			//			}
		} catch (Throwable t) { XposedBridge.log(t); }
	}

	public static void initHandleLoadPackage(final XSharedPreferences paramPrefs, XC_LoadPackage.LoadPackageParam lpParam) {

		findAndHookMethod("com.android.systemui.statusbar.Feature", lpParam.classLoader,"getMaxLevelOfSignalStrengthIndicator", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				try {
					param.setResult(paramPrefs.getInt("signal_bars", 5));
				} catch (Throwable t) {
					XposedBridge.log(t);
				}
			}
		});

		findAndHookMethod("com.android.systemui.statusbar.SignalClusterView", lpParam.classLoader, "apply", new XC_MethodHook(XCallback.PRIORITY_HIGHEST) {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				try {
					View iv = (View) getObjectField(param.thisObject, "mSpacer");
					if (iv != null) {
						iv.setVisibility(8);
					}
				} catch (Throwable t) {
					XposedBridge.log(t); 
				}
			}
		});
	}
}
