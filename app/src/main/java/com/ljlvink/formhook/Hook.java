package com.ljlvink.formhook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws ClassNotFoundException {
        if(loadPackageParam.packageName.equals("android")){
            Class myClass = loadPackageParam.classLoader.loadClass("com.android.server.wm.MiuiFreeFormActivityStack");
            XposedHelpers.findAndHookMethod("com.android.server.wm.MiuiFreeFormStackDisplayStrategy", loadPackageParam.classLoader, "getMaxMiuiFreeFormStackCount", String.class,myClass, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(256);
                }
            });
            XposedHelpers.findAndHookMethod("com.android.server.wm.MiuiFreeFormStackDisplayStrategy", loadPackageParam.classLoader, "shouldStopStartFreeform", java.lang.String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(false);
                }
            });
        }
        if(loadPackageParam.packageName.equals("com.miui.home")){
            Class RunningTaskInfo=loadPackageParam.classLoader.loadClass("android.app.ActivityManager$RunningTaskInfo");
            XposedHelpers.findAndHookMethod("com.miui.home.launcher.RecentsAndFSGestureUtils", loadPackageParam.classLoader, "canTaskEnterSmallWindow", android.content.Context.class, java.lang.String.class, int.class, int.class, boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(true);
                }
            });
            XposedHelpers.findAndHookMethod("com.miui.home.launcher.RecentsAndFSGestureUtils", loadPackageParam.classLoader, "canTaskEnterMiniSmallWindow", android.content.Context.class,RunningTaskInfo, java.lang.String.class, int.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    param.setResult(true);
                }
            });

        }

    }
}
