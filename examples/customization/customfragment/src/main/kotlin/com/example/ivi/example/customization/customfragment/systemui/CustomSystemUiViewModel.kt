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

package com.example.ivi.example.customization.customfragment.systemui

import com.tomtom.ivi.platform.systemui.api.common.frontendcoordinator.FrontendCoordinator
import com.tomtom.ivi.platform.systemui.api.common.systemuihost.CoreSystemUiViewModel
import com.tomtom.ivi.platform.systemui.api.common.systemuihost.createDefaultFrontendCoordinatorContext
import com.tomtom.tools.android.api.lifecycle.LifecycleViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * The view model for the system UI. It exposes a subset of the data from the [frontendCoordinator]
 * to the view layer.
 *
 * It is unrelated to fragment customization.
 */
internal class CustomSystemUiViewModel(
    coreViewModel: CoreSystemUiViewModel,
) : LifecycleViewModel() {

    private val createAfterStartupFrontendsTrigger = MutableStateFlow(false)

    private val frontendCoordinator = FrontendCoordinator(
        createDefaultFrontendCoordinatorContext(
            coreSystemUiViewModel = coreViewModel,
            createAfterStartupFrontendsTrigger = createAfterStartupFrontendsTrigger,
        ),
    )

    val panelRegistry = frontendCoordinator.panelRegistry

    val menuPanel = panelRegistry.mainMenuPanel

    /**
     * A callback to be called when frontends with
     * [FrontendCreationPolicy.CREATE_FRONTEND_AFTER_STARTUP] can be created.
     */
    internal fun onCreateAfterStartupFrontends() {
        createAfterStartupFrontendsTrigger.value = true
    }
}
