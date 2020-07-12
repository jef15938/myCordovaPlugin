package pongpong.helloworld;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


/**
 * This class echoes a string called from JavaScript.
 */
public class HelloWorld extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("saySomething")) {
            String message = args.getString(0);
            this.saySomething(message, callbackContext);
            return true;
        }
        else if (action.equals("getBattery")) {
            this.getBettery(callbackContext);
            return true;
        }
        // else if (action.equals("openCamera")) {
        //     this.getCameraInstance(callbackContext);
        //     return true;
        // }
        return false;
    }

    private void saySomething(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void getBettery(CallbackContext callbackContext) {
      IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
      Intent batteryStatus = webView.getContext().registerReceiver(null, ifilter);
      callbackContext.success(this.getBatteryInfo(batteryStatus));
    }

    private JSONObject getBatteryInfo(Intent batteryIntent) {
        JSONObject obj = new JSONObject();

        try {

          obj.put("level", batteryIntent.getIntExtra(android.os.BatteryManager.EXTRA_LEVEL, 0));

          // Are we charging / charged?
          int status = batteryIntent.getIntExtra(android.os.BatteryManager.EXTRA_STATUS, -1);
          boolean isCharging = status == android.os.BatteryManager.BATTERY_STATUS_CHARGING ||
                              status == android.os.BatteryManager.BATTERY_STATUS_FULL;
          obj.put("isCharging", isCharging);

          // How are we charging?
          int chargePlug = batteryIntent.getIntExtra(android.os.BatteryManager.EXTRA_PLUGGED, -1);
          boolean usbCharge = chargePlug == android.os.BatteryManager.BATTERY_PLUGGED_USB;
          boolean acCharge = chargePlug == android.os.BatteryManager.BATTERY_PLUGGED_AC;
          String chargingWay = usbCharge ? "usb" : acCharge ? "ac" : "";
          obj.put("chargingWay", chargingWay);
        } catch (JSONException e) {
            LOG.e("getBatteryInfo", e.getMessage(), e);
        }
        return obj;
    }

    // private static Camera getCameraInstance(CallbackContext callbackContext){
    // Camera c = null;
    //   try {
    //       c = Camera.open(); // attempt to get a Camera instance
    //       callbackContext.success("Success");
    //   }
    //   catch (Exception e){
    //       callbackContext.error("Can not open camera");
    //       // Camera is not available (in use or does not exist)
    //   }
    // return c; // returns null if camera is unavailable
    // }



}
