package com.dr8.sbicons.sgs.mod.hax;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import android.widget.ImageView;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class InvisSignal {

	public static void initHandleLoadPackage(final XSharedPreferences paramPrefs, XC_LoadPackage.LoadPackageParam lpParam) {
		findAndHookMethod("com.android.systemui.statusbar.HtcGenericSignalClusterView", lpParam.classLoader, "apply", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				ImageView iv = (ImageView) getObjectField(param.thisObject, "mPhoneSignal");
				iv.setImageResource(0);
			}
		});
	}
}
