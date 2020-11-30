package com.example.plan

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViewsService

class listService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return ListViewsFactory(this.applicationContext)
    }
}