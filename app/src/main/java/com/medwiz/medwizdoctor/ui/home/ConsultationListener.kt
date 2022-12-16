package com.medwiz.medwizdoctor.ui.home

import com.medwiz.medwiz.model.Consultation

interface ConsultationListener {

    fun onClickConsult(position: Int, consultation: Consultation)



}