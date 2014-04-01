package com.dr8.sbicons.sgs.mod.hax;

import android.content.res.XModuleResources;
import android.widget.TextView;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

public class BatteryTextColor {

	public static void initPackageResources(final XSharedPreferences paramPrefs, XModuleResources modRes, XC_InitPackageResources.InitPackageResourcesParam resParam) {
		resParam.res.hookLayout("com.android.systemui", "layout", "super_status_bar", new XC_LayoutInflated() {
			@Override
			public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
				try {
					final int btcolor = paramPrefs.getInt("batt_text_color", 0xff35b5e5);
					TextView battext = (TextView) liparam.view.findViewById(liparam.res.getIdentifier("battery_text", "id", "com.android.systemui"));
					battext.setTextColor(btcolor);
				} catch (Throwable t) { XposedBridge.log(t); }
			}
		});   
	}
}
