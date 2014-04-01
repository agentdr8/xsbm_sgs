package com.dr8.sbicons.sgs.mod.hax;

/*
 *  Excellent center clock mod code by C3C076 @ XDA
 *  Part of GravityBox xposed mod for the MTK6589
 *  http://forum.xda-developers.com/showthread.php?t=2316070
 *  
 */

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CenterClock43 {
    public static final String PACKAGE_NAME = "com.android.systemui";
//    private static final String TAG = "XSBM-CenterClock";
    private static final String CLASS_PHONE_STATUSBAR = "com.android.systemui.statusbar.phone.PhoneStatusBar";
    private static final String CLASS_TICKER = "com.android.systemui.statusbar.phone.PhoneStatusBar$MyTicker";

    private static ViewGroup mIconArea;
    private static ViewGroup mRootView;
    private static LinearLayout mLayoutClock;
    private static TextView mClock;
    private static Object mPhoneStatusBar;
    private static Context mContext;
    private static int mAnimPushUpOut;
    private static int mAnimPushDownIn;
    private static int mAnimFadeIn;
    private static boolean mClockCentered = false;
    private static int mClockOriginalPaddingLeft;

//    private static void log(String message) {
//        XposedBridge.log(TAG + ": " + message);
//    }

    public static void initPackageResources(final XSharedPreferences prefs, final InitPackageResourcesParam resparam) {
        try {
            resparam.res.hookLayout(PACKAGE_NAME, "layout", "super_status_bar", new XC_LayoutInflated() {

                @Override
                public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {

                    mIconArea = (ViewGroup) liparam.view.findViewById(
                            liparam.res.getIdentifier("system_icon_area", "id", PACKAGE_NAME));
                    if (mIconArea == null) {
//                    	log("Right iconarea not hooked");
                    	return;
                    }

                    mRootView = (ViewGroup) mIconArea.getParent().getParent();
                    if (mRootView == null) {
//                    	log("Didn't find parent of right_icons");
                    	return;
                    }

                    mClock = (TextView) mIconArea.findViewById(
                            liparam.res.getIdentifier("clock", "id", PACKAGE_NAME));
                    if (mClock == null) {
//                    	log("Didn't hook the clock view");
                    	return;
                    }
                    mClockOriginalPaddingLeft = mClock.getPaddingLeft();

                    // inject new clock layout
                    mLayoutClock = new LinearLayout(liparam.view.getContext());
                    mLayoutClock.setLayoutParams(new LinearLayout.LayoutParams(
                                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    mLayoutClock.setGravity(Gravity.CENTER);
                    mLayoutClock.setVisibility(View.GONE);
                    mRootView.addView(mLayoutClock);
//                    log("mLayoutClock injected");

                    prefs.reload();
                    setClockPosition(prefs.getBoolean("centerclock", false));
                    
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    public static void initHandleLoadPackage(final XSharedPreferences prefs, final ClassLoader classLoader) {
        try {
            final Class<?> phoneStatusBarClass =
                    XposedHelpers.findClass(CLASS_PHONE_STATUSBAR, classLoader);
            final Class<?> tickerClass =
                    XposedHelpers.findClass(CLASS_TICKER, classLoader);

            final Class<?>[] loadAnimParamArgs = new Class<?>[2];
            loadAnimParamArgs[0] = int.class;
            loadAnimParamArgs[1] = Animation.AnimationListener.class;

            XposedHelpers.findAndHookMethod(phoneStatusBarClass, "makeStatusBarView", new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    mPhoneStatusBar = param.thisObject;
                    mContext = (Context) XposedHelpers.getObjectField(mPhoneStatusBar, "mContext");
                    Resources res = mContext.getResources();
                    mAnimPushUpOut = res.getIdentifier("push_up_out", "anim", "android");
                    mAnimPushDownIn = res.getIdentifier("push_down_in", "anim", "android");
                    mAnimFadeIn = res.getIdentifier("fade_in", "anim", "android");
                   
                }
            });

            XposedHelpers.findAndHookMethod(tickerClass, "tickerStarting", new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    if (mLayoutClock == null || !mClockCentered) return;

                    mLayoutClock.setVisibility(View.GONE);
                    Animation anim = (Animation) XposedHelpers.callMethod(
                            mPhoneStatusBar, "loadAnim", loadAnimParamArgs, mAnimPushUpOut, null);
                    mLayoutClock.startAnimation(anim);
                }
            });

            XposedHelpers.findAndHookMethod(tickerClass, "tickerDone", new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    if (mLayoutClock == null || !mClockCentered) return;

                    mLayoutClock.setVisibility(View.VISIBLE);
                    Animation anim = (Animation) XposedHelpers.callMethod(
                            mPhoneStatusBar, "loadAnim", loadAnimParamArgs, mAnimPushDownIn, null);
                    mLayoutClock.startAnimation(anim);
                }
            });

            XposedHelpers.findAndHookMethod(tickerClass, "tickerHalting", new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    if (mLayoutClock == null || !mClockCentered) return;

                    mLayoutClock.setVisibility(View.VISIBLE);
                    Animation anim = (Animation) XposedHelpers.callMethod(
                            mPhoneStatusBar, "loadAnim", loadAnimParamArgs, mAnimFadeIn, null);
                    mLayoutClock.startAnimation(anim);
                }
            });
        }
        catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    private static void setClockPosition(boolean center) {
        if (mClockCentered == center || mClock == null || 
                mIconArea == null || mLayoutClock == null) {
            return;
        }

        if (center) {
            mClock.setGravity(Gravity.CENTER);
            mClock.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            mClock.setPadding(0, 0, 0, 0);
            mIconArea.removeView(mClock);
            mLayoutClock.addView(mClock);
            mLayoutClock.setVisibility(View.VISIBLE);
//            log("Clock set to center position");
        } else {
            mClock.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            mClock.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
            mClock.setPadding(mClockOriginalPaddingLeft, 0, 0, 0);
            mLayoutClock.removeView(mClock);
            mIconArea.addView(mClock);
            mLayoutClock.setVisibility(View.GONE);
//            log("Clock set to normal position");
        }

        mClockCentered = center;
    }
}