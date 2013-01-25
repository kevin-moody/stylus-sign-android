package com.example.kcm_stylus_draw.service;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.util.Log;

public class CommService extends IntentService {
	public static final String CALLBACK = "CALLBACK";
	public static final String ARG1 = "ARG1";
	public static final String ARGN = "ARGN";
	public static final String RESULT = "RESULT";
	private static final String TAG = "EnterpriseService";

	public CommService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Intent resp = new Intent();
		resp.putExtra(RESULT, doSomething(intent.getExtras().getString(ARG1),
		// other arguments...
		// e.g., intent.getParcelableExtra(...)
				intent.getExtras().getString(ARGN)));

		try {
			((PendingIntent) intent.getParcelableExtra(CALLBACK)).send(this,
					Activity.RESULT_OK, resp);
		} catch (CanceledException e) {
			Log.w(TAG, "Cancelled!", e);
		}
	}

	// implement the business logic here....
	private String doSomething(String arg1, String argn) {
		return arg1 + "..." + argn;
	}

}
