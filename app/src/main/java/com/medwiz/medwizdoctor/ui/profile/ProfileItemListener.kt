package com.medwiz.medwizdoctor.ui.profile

import com.medwiz.medwiz.model.ProfileItemModel

/**
 * @Author: Prithwiraj Nath
 * @Date:23/01/23
 */
interface ProfileItemListener {
    fun onClickItem(position: Int,itemObj: ProfileItemModel)
}