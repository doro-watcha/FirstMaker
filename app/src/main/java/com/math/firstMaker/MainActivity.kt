package com.math.firstMaker


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.math.firstMaker.R.*
import com.math.firstMaker.broadcast.Broadcast
import com.math.firstMaker.common.disposedBy
import com.math.firstMaker.databinding.ActivityMainBinding
import com.math.firstMaker.dialog.showTextDoubleDialog
import com.math.firstMaker.navigation.Navigator
import com.math.firstMaker.views.MainMenu
import com.math.firstMaker.views.home.HomeFragment
import com.math.firstMaker.views.makeCollection.MakeCollectionFragment
import com.math.firstMaker.views.setting.SettingFragment
import com.math.firstMaker.views.workBook.WorkBookFragment
import com.math.firstMaker.views.wrong.WrongStorageFragment
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    /**
     * Binding Instance
     */
    private lateinit var mBinding: ActivityMainBinding

    /**
     *  ViewModel Instance
     */

    private val mViewModel: MainViewModel by viewModel()

    private val mFragment1 = HomeFragment.newInstance()
    private val mFragment2 = WrongStorageFragment.newInstance()
    private val mFragment3 = MakeCollectionFragment.newInstance()
    private val mFragment5 = SettingFragment.newInstance()

    private var mActiveFragment: Fragment = mFragment1


    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this

        mBinding.vm = mViewModel
        setContentView(mBinding.root)

        initFragments()
        setupBottomIcon()
        setupBottomNavigation()
        setupBroadcast()


    }

    private fun initFragments() {
        val fm = supportFragmentManager

        fm.beginTransaction().add(id.mMainContainer, mFragment5,"5").hide(mFragment5).commit()
        fm.beginTransaction().add(id.mMainContainer, mFragment3, "3").hide(mFragment3).commit()
        fm.beginTransaction().add(id.mMainContainer, mFragment2, "2").hide(mFragment2).commit()
        fm.beginTransaction().add(id.mMainContainer, mFragment1, "1").commit()
    }


    private fun changeFragment(position: Int) {

        mViewModel.currentTab.value = position

        val willShow = when (position) {
            0 -> mFragment1
            1 -> mFragment2
            2 -> mFragment3
            4 -> mFragment5
            else -> throw IllegalArgumentException()
        }
        supportFragmentManager.beginTransaction()
            .hide(mFragment1)
            .hide(mFragment2)
            .hide(mFragment3)
            .hide(mFragment5)
            .show(willShow).commit()
        mActiveFragment = willShow

    }

    private fun setupBottomNavigation() {

        mBinding.mBottomNavView.setOnNavigationItemSelectedListener {

            val selectedMenu = MainMenu.parseIdToMainMenu(it.itemId)

            navigator.curMainMenu.value = selectedMenu

            when (selectedMenu) {
                MainMenu.HOME -> {
                    changeFragment(0)
                }
                MainMenu.WRONG -> {
                    changeFragment(1)
                }
                MainMenu.PAPER -> {
                    changeFragment(2)
                }
                MainMenu.SETTING -> {
                    changeFragment(4)
                }
            }
            true
        }

        mBinding.mBottomNavView.setOnNavigationItemReselectedListener {

            val selectedMenu = MainMenu.parseIdToMainMenu(it.itemId)

            when (selectedMenu) {
                MainMenu.HOME -> {
                    Broadcast.homeReselectBroadcast.onNext(Unit)
                }
                MainMenu.WRONG -> {
                    Broadcast.wrongReselectBroadcast.onNext(Unit)
                }
                MainMenu.PAPER -> {
                    Broadcast.makeCollectionReselectBroadcast.onNext(Unit)
                }
                MainMenu.SETTING -> {
                    changeFragment(4)
                }

            }
            true
        }
    }

    private fun setupBroadcast() {

        Broadcast.apply {

            exitApplication.subscribe {
                showTextDoubleDialog(
                    titleResource = R.string.common_notification,
                    bodyResource = R.string.txt_hoxy_close_application,
                    onPositive = {
                        finish()
                    },
                    onNegative = {
                    }
                )
            }.disposedBy(compositeDisposable)

            moveToHomeBroadcast.subscribe {
                changeFragment(0)
                mBinding.mBottomNavView.selectedItemId = id.nav_item_home
            }.disposedBy(compositeDisposable)
        }
    }

    override fun onBackPressed() {

        debugE(TAG, mViewModel.currentTab.value ?: 0 )
        Broadcast.moveToFirstPageBroadcast.onNext(mViewModel.currentTab.value ?: 0)

    }


    private fun setupBottomIcon() {

        mBinding.mBottomNavView.menu.apply {
            findItem(id.nav_item_home).setIcon(drawable.ic_home_white_24dp)
            findItem(id.nav_item_wrong_problem).setIcon(drawable.ic_block_gray_24dp)
            findItem(id.nav_item_make_work_paper).setIcon(drawable.ic_access_alarm_gray_24dp)
            findItem(id.nav_item_setting).setIcon(drawable.ic_settings_gray_24dp)
        }
    }

    companion object {
        private val TAG: String = "MainActivity"

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java).apply {
            }
        }

    }
}

