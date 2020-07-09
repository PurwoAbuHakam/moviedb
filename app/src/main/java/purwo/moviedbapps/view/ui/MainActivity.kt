package purwo.moviedbapps.view.ui

import android.content.ContentResolver
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import purwo.moviedbapps.R
import purwo.moviedbapps.alarm.AlarmReceiver
import purwo.moviedbapps.model.realm.SettingConfig
import purwo.moviedbapps.view.ui.home.HomeFragment
import purwo.moviedbapps.view.ui.setting.SettingActivity
import purwo.moviedbapps.view.ui.viewmodel.DataViewModel
import purwo.moviedbapps.view.ui.viewmodel.FavouriteViewModel
import purwo.moviedbapps.view.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mSupportActionBar: ActionBar
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mRealm: Realm
    private var reminder = true
    private var newrelease = true

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.anim.design_bottom_sheet_slide_in,
                R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.content, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportActionBar != null) {
            mSupportActionBar = supportActionBar as ActionBar
            mSupportActionBar.title = getString(R.string.main_title)
        }

        mRealm = Realm.getDefaultInstance()
        alarmReceiver = AlarmReceiver()

        initializeSetting()

        setContextCompanion(this, contentResolver)

        mainViewModel = ViewModelProvider(this, getViewModelFactory()).get(
            MainViewModel::class.java
        )

        navigation.setOnNavigationItemSelectedListener(mainViewModel.mOnNavigationItemSelectedListener)
        val fragment = HomeFragment.newInstance()
        addFragment(fragment)

        mainViewModel.mFragment.observe(this, Observer<Fragment> {
            addFragment(it)
        })

        mainViewModel.mTitle.observe(this, Observer<Int> {
            mSupportActionBar.title = getString(it)
        })

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bar_menus, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    fun initializeSetting() {
        val settingConfig = mRealm.where<SettingConfig>().findFirst()
        if (settingConfig == null) {
            mRealm.beginTransaction()
            mRealm.copyToRealmOrUpdate(SettingConfig(1, true, true))
            mRealm.commitTransaction()
        } else {
            newrelease = settingConfig.notifNewRelease
            reminder = settingConfig.notifReminder
        }
    }

    companion object {
        lateinit var mContext: ViewModelStoreOwner
        lateinit var mContentResolver: ContentResolver

        fun setContextCompanion(param: ViewModelStoreOwner, contentResolver: ContentResolver) {
            mContext = param
            mContentResolver = contentResolver
        }

        fun getViewModelFactory(): ViewModelProvider.Factory {
            return ViewModelProvider.NewInstanceFactory()
        }

        fun DataViewModel(): DataViewModel {
            return ViewModelProvider(mContext, getViewModelFactory()).get(
                DataViewModel::class.java
            )
        }

        fun FavouriteViewModel(): FavouriteViewModel {
            return ViewModelProvider(mContext, getViewModelFactory()).get(
                FavouriteViewModel::class.java
            )
        }
    }

}
