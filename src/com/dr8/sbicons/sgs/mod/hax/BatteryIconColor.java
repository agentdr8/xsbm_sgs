package com.dr8.sbicons.sgs.mod.hax;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getObjectField;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.widget.ImageView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class BatteryIconColor {

	public static void initHandleLoadPackage(final XSharedPreferences paramPrefs, XC_LoadPackage.LoadPackageParam lpParam) {
		if (paramPrefs.getBoolean("batt_text_color_enabled", false)) {
			findAndHookMethod("com.android.systemui.statusbar.policy.BatteryController", lpParam.classLoader, "onReceive", Context.class, Intent.class, new XC_MethodHook() {
				@Override
				protected void afterHookedMethod(MethodHookParam param) throws Throwable {
					try {
						@SuppressWarnings("unchecked")
						int j = ((ArrayList<ImageView>) getObjectField(param.thisObject, "mIconViews")).size();
						for (int k = 0; k < j; k++) {
							@SuppressWarnings("unchecked")
							ImageView iv = ((ArrayList<ImageView>) getObjectField(param.thisObject, "mIconViews")).get(k);
							final int btcolor = paramPrefs.getInt("batt_text_color", 0xff35b5e5);
							iv.setColorFilter(btcolor, Mode.MULTIPLY);
						}
					} catch (Throwable t) { XposedBridge.log(t); }
				}
	
			});
		}
	}
}
