package purwo.moviedbapps.view.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_setting.*
import purwo.moviedbapps.R
import purwo.moviedbapps.alarm.AlarmReceiver
import purwo.moviedbapps.model.realm.SettingConfig


class SettingActivity : AppCompatActivity() {
    lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.title = resources.getString(R.string.menu_1)
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        val alarmReceiver = AlarmReceiver()
        val mContext: Context = this
        mRealm = Realm.getDefaultInstance()

        val switch1: Switch = findViewById(R.id.switch1)
        val switch2: Switch = findViewById(R.id.switch2)

        val settingConfig = mRealm.where<SettingConfig>().findFirst() as SettingConfig

        switch1.isChecked = settingConfig.notifNewRelease
        switch2.isChecked = settingConfig.notifReminder

        crdSetting.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            var enable = true
            if (isChecked) {
                if (!alarmReceiver.isAlarmSet(mContext, AlarmReceiver.TYPE_NEW_RELEASE)) {
                    val repeatTime = "08:00"
                    val repeatMessage = getString(R.string.notif_repeat)
                    alarmReceiver.setRepeatingAlarmNewRelease(
                        this, AlarmReceiver.TYPE_NEW_RELEASE,
                        repeatTime, repeatMessage
                    )
                    enable = true
                }
            } else {
                alarmReceiver.cancelAlarm(mContext, AlarmReceiver.TYPE_NEW_RELEASE)
                enable = false
            }
            mRealm.beginTransaction()
            mRealm.copyToRealmOrUpdate(SettingConfig(1, switch2.isChecked, enable))
            mRealm.commitTransaction()
        }

        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            var enable = true
            if (isChecked) {
                if (!alarmReceiver.isAlarmSet(mContext, AlarmReceiver.TYPE_REPEATING)) {
                    val repeatTime = "07:00"
                    val repeatMessage = getString(R.string.notif_repeat)
                    alarmReceiver.setRepeatingAlarm(
                        this, AlarmReceiver.TYPE_REPEATING,
                        repeatTime, repeatMessage
                    )
                }
                enable = true
            } else {
                alarmReceiver.cancelAlarm(mContext, AlarmReceiver.TYPE_REPEATING)
                enable = false
            }

            mRealm.beginTransaction()
            mRealm.copyToRealmOrUpdate(SettingConfig(1, enable, switch1.isChecked))
            mRealm.commitTransaction()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
}
