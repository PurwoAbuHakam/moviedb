package purwo.moviedbapps

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * Created by Purwo on 08/07/2020.
 */
class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}