/***
  Copyright (c) 2012-2015 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Android Development_
    https://commonsware.com/Android
 */

package com.example.admin.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.commonsware.cwac.wakeful.WakefulIntentService;

public class PollReceiver extends BroadcastReceiver {
  private static final int PERIOD=15000; // 15 seconds

  @Override
  public void onReceive(Context ctxt, Intent i) {
    if (i.getAction()==null) {
      WakefulIntentService.sendWakefulWork(ctxt, ScheduledService.class);
    }

    scheduleAlarms(ctxt);
  }

  static void scheduleAlarms(Context ctxt) {
    AlarmManager mgr=
      (AlarmManager)ctxt.getSystemService(Context.ALARM_SERVICE);
    Intent i=new Intent(ctxt, PollReceiver.class);
    PendingIntent pi=PendingIntent.getBroadcast(ctxt, 0, i, 0);
    Intent i2=new Intent(ctxt, EventDemoActivity.class);
    PendingIntent pi2=PendingIntent.getActivity(ctxt, 0, i2, 0);

    AlarmManager.AlarmClockInfo ac=
      new AlarmManager.AlarmClockInfo(System.currentTimeMillis()+PERIOD,
        pi2);

    mgr.setAlarmClock(ac, pi);
  }
}
