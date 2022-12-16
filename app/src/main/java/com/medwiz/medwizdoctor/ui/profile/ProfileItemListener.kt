package com.medwiz.medwizdoctor.ui.profile

import com.medwiz.medwiz.model.ProfileItemModel

/**
 * @Author: Prithwiraj Nath
 * @Date:17/12/22
 */
interface ProfileItemListener {
    fun onClickItem(position: Int,itemObj: ProfileItemModel)
}