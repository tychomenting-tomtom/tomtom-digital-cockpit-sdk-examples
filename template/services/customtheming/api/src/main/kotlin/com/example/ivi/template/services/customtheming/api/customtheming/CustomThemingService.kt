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

package com.example.ivi.template.services.customtheming.api.customtheming

import com.tomtom.ivi.platform.framework.api.ipc.iviserviceannotations.IviService
import com.tomtom.ivi.platform.framework.api.ipc.iviserviceannotations.IviServiceFun

/**
 * IVI service to switch between day and night mode.
 */
@IviService(
    serviceId = "com.example.ivi.template.services.customtheming",
)
interface CustomThemingService {
    /**
     * Flag which indicates if the night mode is enabled.
     */
    val nightEnabled: Boolean

    /**
     * Enable the night mode.
     */
    @IviServiceFun
    suspend fun setNightMode()

    /**
     * Enable the day mode.
     */
    @IviServiceFun
    suspend fun setDayMode()

    public companion object{}
}
