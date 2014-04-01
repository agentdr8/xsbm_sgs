package com.dr8.sbicons.sgs.mod.hax;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import android.view.View;
import android.widget.TextView;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class InvisClock {

	public static void initHandleLoadPackage(final XSharedPreferences paramPrefs, XC_LoadPackage.LoadPackageParam lpParam) {
		findAndHookMethod("com.android.systemui.statusbar.policy.Clock", lpParam.classLoader, "updateClock", new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				TextView clock = (TextView) param.thisObject;
				clock.setText(null);
				clock.setVisibility(View.GONE);
			}
		});
	}
}
