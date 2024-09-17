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

import com.example.ivi.template.services.customtheming.api.customtheming.CustomThemingServiceBase
import com.tomtom.ivi.platform.framework.api.ipc.iviservice.IviServiceHostContext

internal class StockCustomThemingService(iviServiceHostContext: IviServiceHostContext) :
    CustomThemingServiceBase(iviServiceHostContext) {

    override fun onCreate() {
        super.onCreate()
        nightEnabled = false

        serviceReady = true
    }

    override suspend fun setNightMode() {
        nightEnabled = true
    }

    override suspend fun setDayMode() {
        nightEnabled = false
    }
}
