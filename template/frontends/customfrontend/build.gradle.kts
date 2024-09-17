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

android {
    namespace = "com.example.ivi.template.frontends.customfrontend"
    buildFeatures {
        dataBinding = true
    }
}

ivi{
    optInToExperimentalApis = true
}

dependencies {
    implementation(project(":template_services_customtheming_api"))
    implementation(iviDependencies.tomtomToolsCoreTheme)
    implementation(iviDependencies.tomtomToolsApiResourceresolution)
    implementation(iviDependencies.tomtomToolsApiDatabinding)
    implementation(iviDependencies.tomtomToolsApiLivedata)
    implementation(iviDependencies.tomtomToolsApiUicontrols)
    implementation(libraries.iviPlatformFrameworkApiProductDebugPermissions)
    implementation(libraries.iviPlatformFrameworkApiProductDefaultActivity)
    implementation(libraries.iviPlatformSystemuiApiStockSystemuihost)
}
