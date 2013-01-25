package com.example.kcm_stylus_draw.service;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;

public class CommServiceHelper {
    private static final int REQ_ID = 42;

    public static interface OnDoSomethingResultHandler {
        void onFail(int code);
        void onResult(String message);
    }
    private static final CommServiceHelper instance = new CommServiceHelper();

    public static CommServiceHelper getInstance() { return instance; }

    public void doSomething(Activity ctxt, String arg1, String argn) {
        Intent intent = new Intent(ctxt, CommService.class);
        intent.putExtra(CommService.ARG1, arg1);
        // add other arguments
        // e.g., intent.putExtra(complexParcelable);
        intent.putExtra(CommService.ARGN, argn);
        intent.putExtra(
        		CommService.CALLBACK,
            ctxt.createPendingResult(
                REQ_ID,
                new Intent(), // empty default response
                PendingIntent.FLAG_ONE_SHOT));
        ctxt.startService(intent);
    }

    public boolean onDoSomethingResult(
        int reqCode,
        int resCode,
        Intent resp,
        OnDoSomethingResultHandler hdlr)
    {
        if (REQ_ID != reqCode) { return false; }

        if (Activity.RESULT_OK != resCode) { hdlr.onFail(resCode); }
        else {
            hdlr.onResult(
                // could be something more complex than a String...
                resp.getExtras().getString(CommService.RESULT));
        }

        return true;
    }

}
