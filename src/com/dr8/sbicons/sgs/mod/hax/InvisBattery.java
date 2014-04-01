package com.dr8.sbicons.sgs.mod.hax;

import android.content.res.XModuleResources;
import android.widget.ImageView;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

public class InvisBattery {

	public static void initPackageResources(final XSharedPreferences paramPrefs, XModuleResources modRes, XC_InitPackageResources.InitPackageResourcesParam resParam) {
		resParam.res.hookLayout("com.android.systemui", "layout", "super_status_bar", new XC_LayoutInflated() {
			@Override
			public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
				try {
					ImageView batt = (ImageView) liparam.view.findViewById(liparam.res.getIdentifier("battery", "id", "com.android.systemui"));
					batt.setVisibility(8);
				} catch (Throwable t) { XposedBridge.log(t); }
			}
		}); 
	}
}
