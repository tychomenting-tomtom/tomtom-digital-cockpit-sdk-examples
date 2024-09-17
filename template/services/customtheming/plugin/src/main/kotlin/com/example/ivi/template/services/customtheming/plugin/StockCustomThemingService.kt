/*
 * Copyright © 2020 TomTom NV. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom NV and its subsidiaries and may be
 * used for internal evaluation purposes or commercial use strictly subject to separate
 * license agreement between you and TomTom NV. If you are the licensee, you are only permitted
 * to use this software in accordance with the terms of your license agreement. If you are
 * not the licensee, you are not authorized to use this software in any manner and should
 * immediately return or destroy it.
 */

package com.example.ivi.template.services.customtheming.plugin

import androidx.lifecycle.MutableLiveData
import com.example.ivi.template.services.customtheming.api.customtheming.CustomThemingServiceBase
import com.tomtom.ivi.platform.framework.api.common.annotations.IviExperimental
import com.tomtom.ivi.platform.framework.api.ipc.iviservice.IviServiceHostContext
import com.tomtom.ivi.platform.framework.api.ipc.iviservice.queueOrRun
import com.tomtom.ivi.platform.theming.api.common.attributes.IviThemeComponent
import com.tomtom.ivi.platform.theming.api.common.attributes.get
import com.tomtom.ivi.platform.theming.api.service.theming.ThemingService
import com.tomtom.ivi.platform.theming.api.service.theming.createApi
import com.tomtom.ivi.platform.theming.api.theming.stock.StockColorThemeCategoryStylingFlavor
import com.tomtom.ivi.platform.theming.api.theming.stock.stockColorThemeComponents
import com.tomtom.tools.android.api.livedata.combine

@OptIn(IviExperimental::class)
internal class StockCustomThemingService(iviServiceHostContext: IviServiceHostContext) :
    CustomThemingServiceBase(iviServiceHostContext) {

    private val themingService =
        ThemingService.createApi(this, iviServiceHostContext.iviServiceProvider)

    override fun onCreate() {
        super.onCreate()

        nightEnabled = NIGHT_MODE_ENABLED_DEFAULT
        themingService.serviceAvailable.observe(this) {
            serviceReady = it
        }
    }

    private val isDarkModeThemeSelected = MutableLiveData(NIGHT_MODE_ENABLED_DEFAULT)

    private val applyThemeParameters = combine(
        themingService.availableThemeComponents,
        isDarkModeThemeSelected,
    ) { availableComponents, isDarkThemeSelected ->
        Pair(availableComponents, isDarkThemeSelected)
    }

    private fun applyTheme(
        availableComponents: List<IviThemeComponent>,
        isDarkModeThemeInUse: Boolean,
    ) {
        val selectedComponent = when {
            isDarkModeThemeInUse -> stockColorThemeComponents[StockColorThemeCategoryStylingFlavor.DARK]
            else -> stockColorThemeComponents[StockColorThemeCategoryStylingFlavor.LIGHT]
        }
        availableComponents.find { it.id == selectedComponent.id }
            ?.let { themingService.applyActiveThemeComponentAsync(it) }
    }

    private fun updateTheme() {
        themingService.queueOrRun {
            applyThemeParameters.observe(this) {
                applyTheme(
                    availableComponents = it.first,
                    isDarkModeThemeInUse = nightEnabled,
                )
            }
        }
    }

    override suspend fun setNightMode() {
        nightEnabled = true
        updateTheme()
    }

    override suspend fun setDayMode() {
        nightEnabled = false
        updateTheme()
    }

    private companion object {
        private const val NIGHT_MODE_ENABLED_DEFAULT = true
    }
}
