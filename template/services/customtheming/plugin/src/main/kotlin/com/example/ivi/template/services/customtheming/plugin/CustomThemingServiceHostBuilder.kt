package com.example.ivi.template.services.customtheming.plugin

import com.tomtom.ivi.platform.framework.api.ipc.iviservice.IviServiceBase
import com.tomtom.ivi.platform.framework.api.ipc.iviservice.IviServiceHostContext
import com.tomtom.ivi.platform.framework.api.ipc.iviservice.SimpleIviServiceHostBuilder

class CustomThemingServiceHostBuilder : SimpleIviServiceHostBuilder() {

    override fun createIviServices(
        iviServiceHostContext: IviServiceHostContext,
    ): Collection<IviServiceBase> =
        listOf(StockCustomThemingService(iviServiceHostContext))

    companion object
}
