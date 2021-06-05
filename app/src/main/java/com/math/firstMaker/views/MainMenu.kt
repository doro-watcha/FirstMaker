package com.math.firstMaker.views

import androidx.annotation.IdRes
import com.math.firstMaker.R


/**
 * created By DORO 2020-03-22
 */

enum class MainMenu (@IdRes
override val menuId: Int, override val idx: Int) : IMainMenu{

    HOME(R.id.nav_item_home, 0),
    WRONG(R.id.nav_item_wrong_problem, 1),
    PAPER(R.id.nav_item_make_work_paper, 2),
    SETTING(R.id.nav_item_setting,4);



    companion object {
        fun parseIdToIdx(@IdRes id: Int) = values().indexOfFirst { it.menuId == id }
        fun parseIdToMainMenu(@IdRes id: Int) = values().first { it.menuId == id }
        fun parseIndexToMainMenu(idx: Int) = values().first { it.idx == idx }
    }
}
