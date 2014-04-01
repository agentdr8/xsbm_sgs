package com.dr8.sbicons.sgs.mod.hax;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getObjectField;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.dr8.sbicons.sgs.mod.ZipStuff;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class BatteryIcons {

	public static void initHandleLoadPackage(final XSharedPreferences paramPrefs, XC_LoadPackage.LoadPackageParam lpParam) {
		
		final String path = "/data/data/com.dr8.sbicons.sgs/xsbm/";
		final String[] battarray = {
				"stat_sys_battery_0.png",
				"stat_sys_battery_1.png",
				"stat_sys_battery_2.png",
				"stat_sys_battery_3.png",
				"stat_sys_battery_4.png",
				"stat_sys_battery_5.png",
				"stat_sys_battery_6.png",
				"stat_sys_battery_7.png",
				"stat_sys_battery_8.png",
				"stat_sys_battery_9.png",
				"stat_sys_battery_10.png",
				"stat_sys_battery_11.png",
				"stat_sys_battery_12.png",
				"stat_sys_battery_13.png",
				"stat_sys_battery_14.png",
				"stat_sys_battery_15.png",
				"stat_sys_battery_16.png",
				"stat_sys_battery_17.png",
				"stat_sys_battery_18.png",
				"stat_sys_battery_19.png",
				"stat_sys_battery_20.png",
				"stat_sys_battery_21.png",
				"stat_sys_battery_22.png",
				"stat_sys_battery_23.png",
				"stat_sys_battery_24.png",
				"stat_sys_battery_25.png",
				"stat_sys_battery_26.png",
				"stat_sys_battery_27.png",
				"stat_sys_battery_28.png",
				"stat_sys_battery_29.png",
				"stat_sys_battery_30.png",
				"stat_sys_battery_31.png",
				"stat_sys_battery_32.png",
				"stat_sys_battery_33.png",
				"stat_sys_battery_34.png",
				"stat_sys_battery_35.png",
				"stat_sys_battery_36.png",
				"stat_sys_battery_37.png",
				"stat_sys_battery_38.png",
				"stat_sys_battery_39.png",
				"stat_sys_battery_40.png",
				"stat_sys_battery_41.png",
				"stat_sys_battery_42.png",
				"stat_sys_battery_43.png",
				"stat_sys_battery_44.png",
				"stat_sys_battery_45.png",
				"stat_sys_battery_46.png",
				"stat_sys_battery_47.png",
				"stat_sys_battery_48.png",
				"stat_sys_battery_49.png",
				"stat_sys_battery_50.png",
				"stat_sys_battery_51.png",
				"stat_sys_battery_52.png",
				"stat_sys_battery_53.png",
				"stat_sys_battery_54.png",
				"stat_sys_battery_55.png",
				"stat_sys_battery_56.png",
				"stat_sys_battery_57.png",
				"stat_sys_battery_58.png",
				"stat_sys_battery_59.png",
				"stat_sys_battery_60.png",
				"stat_sys_battery_61.png",
				"stat_sys_battery_62.png",
				"stat_sys_battery_63.png",
				"stat_sys_battery_64.png",
				"stat_sys_battery_65.png",
				"stat_sys_battery_66.png",
				"stat_sys_battery_67.png",
				"stat_sys_battery_68.png",
				"stat_sys_battery_69.png",
				"stat_sys_battery_70.png",
				"stat_sys_battery_71.png",
				"stat_sys_battery_72.png",
				"stat_sys_battery_73.png",
				"stat_sys_battery_74.png",
				"stat_sys_battery_75.png",
				"stat_sys_battery_76.png",
				"stat_sys_battery_77.png",
				"stat_sys_battery_78.png",
				"stat_sys_battery_79.png",
				"stat_sys_battery_80.png",
				"stat_sys_battery_81.png",
				"stat_sys_battery_82.png",
				"stat_sys_battery_83.png",
				"stat_sys_battery_84.png",
				"stat_sys_battery_85.png",
				"stat_sys_battery_86.png",
				"stat_sys_battery_87.png",
				"stat_sys_battery_88.png",
				"stat_sys_battery_89.png",
				"stat_sys_battery_90.png",
				"stat_sys_battery_91.png",
				"stat_sys_battery_92.png",
				"stat_sys_battery_93.png",
				"stat_sys_battery_94.png",
				"stat_sys_battery_95.png",
				"stat_sys_battery_96.png",
				"stat_sys_battery_97.png",
				"stat_sys_battery_98.png",
				"stat_sys_battery_99.png",
				"stat_sys_battery_100.png"
		};
		
		final ArrayList<String> filearray = new ArrayList<String>();
		
		String cpath = path + "battery/charge/";
		File cf = new File(cpath);
		final File charge[] = cf.listFiles();
		for (int i=0; i < charge.length; i++) {
			if (charge[i].getName().toLowerCase().endsWith(".png")) {
				filearray.add(charge[i].getName());
			}
		}
		Collections.sort(filearray, new Comparator<String>() {

	        @Override
	        public int compare(String o1, String o2) {
	            try{
	            	String minusext1 = o1.substring(0, (o1.length() - 4));
	            	String minusext2 = o2.substring(0, (o2.length() - 4));
	            	String justnum1 = minusext1.replaceAll("stat_sys_battery_charge_anim", "");
	            	String justnum2 = minusext2.replaceAll("stat_sys_battery_charge_anim", "");
	            	Integer integer1 = Integer.valueOf(justnum1);
	            	Integer integer2 = Integer.valueOf(justnum2);
	            	return integer1.compareTo(integer2);
	            }catch (java.lang.NumberFormatException e) {
	                return o1.compareTo(o2);
	            }
	        }
	    });
		
		if (paramPrefs.getBoolean("battery", false)) {
			findAndHookMethod("com.android.systemui.statusbar.policy.BatteryController", lpParam.classLoader, "onReceive", Context.class, Intent.class, new XC_MethodHook() {
				@Override
				protected void afterHookedMethod(MethodHookParam param) throws Throwable {
					try {
						Intent pi = (Intent) param.args[1];
						int status = pi.getIntExtra("status", 1);
						int blevel = pi.getIntExtra("level", 0);
						@SuppressWarnings("unchecked")
						int j = ((ArrayList<ImageView>) getObjectField(param.thisObject, "mIconViews")).size();
						for (int k = 0; k < j; k++) {
							@SuppressWarnings("unchecked")
							ImageView iv = ((ArrayList<ImageView>) getObjectField(param.thisObject, "mIconViews")).get(k);
							if (status == 2 && blevel < 100) {
								AnimationDrawable animation = new AnimationDrawable();
								for (int i = 0; i < filearray.size(); i++) {
									String cbimg = "battery/charge/" + filearray.get(i);
//									Log.d("XSBM", "Req charge img: " + cbimg);
									final Bitmap cb = ZipStuff.getBitmap(path, cbimg, 240);
									Drawable cd = new BitmapDrawable(null, cb);
									animation.addFrame(cd, paramPrefs.getInt("charge_delay", 350));
								}
								if (paramPrefs.getBoolean("altcharge", false)) {
									String bimg = "battery/" + battarray[blevel];
									final Bitmap b = ZipStuff.getBitmap(path, bimg, 240);
									Drawable bd = new BitmapDrawable(null, b);
									animation.addFrame(bd, 500);
								} 
								animation.setOneShot(false);
								iv.setImageDrawable(animation);
								animation.start();
							} else if (status == 2 && blevel >= 100) {
								String bimg = "battery/" + battarray[100];
								final Bitmap b = ZipStuff.getBitmap(path, bimg, 240);
								Drawable d = new BitmapDrawable(null, b);
								iv.setImageDrawable(d);
							} else { 
								String bimg = "battery/" + battarray[blevel];
								final Bitmap b = ZipStuff.getBitmap(path, bimg, 240);
								Drawable d = new BitmapDrawable(null, b);
								iv.setImageDrawable(d);
							}
						}
					} catch (Throwable t) { XposedBridge.log(t); }
				}
	
			});	
		}
	}
}
