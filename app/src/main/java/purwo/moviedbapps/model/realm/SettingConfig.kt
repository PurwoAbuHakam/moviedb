package purwo.moviedbapps.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by Purwo on 08/07/2020.
 */
open class SettingConfig(
    @PrimaryKey
    var id: Int = 1,
    var notifReminder: Boolean = true,
    var notifNewRelease: Boolean = true
) : RealmObject()