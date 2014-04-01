package com.dr8.sbicons.sgs.mod.hax;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.widget.TextView;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class ClockColor {

	public static void initHandleLoadPackage(final XSharedPreferences paramPrefs, XC_LoadPackage.LoadPackageParam lpParam) {

		findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpParam.classLoader, "updateClock", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				try { 
					TextView cl = (TextView) param.thisObject;
					cl.setTextColor(paramPrefs.getInt("clock_text_color", 0xff35b5e5));
				} catch (Throwable t) { XposedBridge.log(t); }
			}
		});
	}
}
