package com.example.plan

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import android.widget.Toast


/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {
    val TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION"
    val EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM"
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {



            val serviceIntent = Intent(context, listService::class.java)
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)))

            val widget = RemoteViews(context.packageName, R.layout.new_app_widget)
            widget.setRemoteAdapter(R.id.listview, serviceIntent)

            var toastIntent = Intent(context, NewAppWidget::class.java)
            toastIntent.setAction(TOAST_ACTION)
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)))
            val toastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            widget.setPendingIntentTemplate(R.id.listview, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetIds, widget)
           // updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        /*val mgr = AppWidgetManager.getInstance(context)
        if (intent!!.action.equals(TOAST_ACTION)) {
            val appWidgetId = intent!!.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
            val viewIndex = intent!!.getIntExtra(EXTRA_ITEM, 0)
            Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
        }
        super.onReceive(context, intent)*/



        val appWidgetManager= AppWidgetManager.getInstance(context)
        if(intent?.action.equals(TOAST_ACTION)) {
            val i=Intent(context,MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(i)
        }
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context) {
super.onDisabled(context)
    }
}
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
var widgetText:CharSequence=context.getString(R.string.appwidget_text)
    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
    views.setTextViewText(R.id.textView, widgetText)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}