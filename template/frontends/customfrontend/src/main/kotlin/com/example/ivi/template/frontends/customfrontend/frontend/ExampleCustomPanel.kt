/*
 * Copyright © 2022 TomTom NV. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom NV and its subsidiaries and may be
 * used for internal evaluation purposes or commercial use strictly subject to separate
 * license agreement between you and TomTom NV. If you are the licensee, you are only permitted
 * to use this software in accordance with the terms of your license agreement. If you are
 * not the licensee, you are not authorized to use this software in any manner and should
 * immediately return or destroy it.
 */

package com.example.ivi.template.frontends.customfrontend.frontend

import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ivi.template.frontends.customfrontend.common.CustomSystemUiPanel
import com.example.ivi.template.frontends.customfrontend.databinding.TtiviCustompaneltypeCustompanelBinding
import com.example.ivi.template.services.customtheming.api.customtheming.CustomThemingService
import com.example.ivi.template.services.customtheming.api.customtheming.createApi
import com.tomtom.ivi.platform.framework.api.ipc.iviservice.queueOrRun
import com.tomtom.ivi.platform.frontend.api.common.frontend.FrontendContext
import com.tomtom.ivi.platform.frontend.api.common.frontend.IviFragment
import com.tomtom.ivi.platform.frontend.api.common.frontend.viewmodels.FrontendViewModel

/**
 * The implementation of [CustomSystemUiPanel].
 */
internal class ExampleCustomPanel(
    frontendContext: FrontendContext,
    val isTaskPanelOpened: LiveData<Boolean>,
) : CustomSystemUiPanel(frontendContext) {
    override fun createInitialFragmentInitializer(): IviFragment.Initializer<*, *> =
        IviFragment.Initializer(ExampleCustomFragment(), this)
}

internal class ExampleCustomFragment :
    IviFragment<ExampleCustomPanel, ExampleCustomViewModel>(ExampleCustomViewModel::class) {

    override val viewFactory: ViewFactory<*> =
        ViewFactory(TtiviCustompaneltypeCustompanelBinding::inflate)
}

internal class ExampleCustomViewModel(panel: ExampleCustomPanel) :
    FrontendViewModel<ExampleCustomPanel>(panel) {

    val service = CustomThemingService.createApi(this, panel.frontendContext.iviServiceProvider)

    val isNightMode = service.nightEnabled

    val isTaskPanelOpened = panel.isTaskPanelOpened

    val serviceRunning = MutableLiveData(false)

    val listener = OnCheckedChangeListener { _, isChecked ->
        if (isChecked) {
            service.queueOrRun { it.setNightModeAsync { } }
        } else {
            service.queueOrRun { it.setDayModeAsync { } }
        }
    }

    init {
        service.serviceAvailable.observe(this){
            serviceRunning.value = it
        }
    }
}
