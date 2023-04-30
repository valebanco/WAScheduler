package it.bancon.wascheduler.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.core.app.NavUtils;

public class ActivityNavigationUtils {

   public static void goUpToTopActivity(Activity currentActivity) {
      Intent intent = NavUtils.getParentActivityIntent(currentActivity);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
      NavUtils.navigateUpTo(currentActivity, intent);
   }
}
