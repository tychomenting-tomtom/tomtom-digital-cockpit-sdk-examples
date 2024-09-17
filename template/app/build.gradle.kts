/*
 * Copyright © 2021. Change this to your own official copyright statement.
 */

import com.tomtom.ivi.appsuite.gradle.appstore.api.appsuitedefaults.appstore.appStoreGroup
import com.tomtom.ivi.appsuite.gradle.bluetooth.api.appsuitedefaults.bluetooth.bluetoothGroup
import com.tomtom.ivi.appsuite.gradle.communications.api.appsuitedefaults.communications.communicationsGroup
import com.tomtom.ivi.appsuite.gradle.hvac.api.appsuitedefaults.hvac.hvacGroup
import com.tomtom.ivi.appsuite.gradle.media.api.appsuitedefaults.media.mediaGroup
import com.tomtom.ivi.appsuite.gradle.messaging.api.appsuitedefaults.messaging.messagingGroup
import com.tomtom.ivi.appsuite.gradle.navappcomponents.api.appsuitedefaults.navappcomponents.navAppComponentsGroup
import com.tomtom.ivi.appsuite.gradle.navigation.api.appsuitedefaults.navigation.navigationGroup
import com.tomtom.ivi.appsuite.gradle.systemstatus.api.appsuitedefaults.systemstatus.systemStatusGroup
import com.tomtom.ivi.appsuite.gradle.userprofiles.api.appsuitedefaults.userprofiles.userProfilesGroup
import com.tomtom.ivi.appsuite.gradle.vehiclesettings.api.appsuitedefaults.vehiclesettings.vehicleSettingsGroup
import com.tomtom.ivi.buildsrc.environment.ProjectAbis
import com.tomtom.ivi.platform.gradle.api.common.dependencies.ModuleReference
import com.tomtom.ivi.platform.gradle.api.common.iviapplication.config.FrontendConfig
import com.tomtom.ivi.platform.gradle.api.common.iviapplication.config.IviAppsuite
import com.tomtom.ivi.platform.gradle.api.common.iviapplication.config.IviInstanceIdentifier
import com.tomtom.ivi.platform.gradle.api.common.iviapplication.config.IviThemeRegistrySourceConfig
import com.tomtom.ivi.platform.gradle.api.common.iviapplication.config.PanelTypesConfig
import com.tomtom.ivi.platform.gradle.api.common.iviapplication.configurators.IviDefaultsGroupsSelectionConfigurator
import com.tomtom.ivi.platform.gradle.api.framework.config.ivi

plugins {
    id("com.tomtom.ivi.product.defaults.core")
}

private val templateAppModule = ModuleReference(
    "com.example.ivi",
    "template_app",
    "com.example.ivi.template.app",
)

private val frontendModule = ModuleReference(
    "com.example.ivi",
    "template_frontends_customfrontend",
    "com.example.ivi.template.frontends",
)

/**
 * Defines the custom panel types.
 */
val customPanelTypes = PanelTypesConfig(
    "CUSTOM_SYSTEM_UI_PANEL_TYPES",
    frontendModule,
    "customfrontend.common",
)

val exampleFrontend =
    FrontendConfig(
        frontendBuilderName = "ExampleFrontendBuilder",
        implementationModule = frontendModule,
        subPackageName = "customfrontend.frontend",
        availablePanelTypes = customPanelTypes,
    )

val exampleMenuItem = exampleFrontend.toMenuItem("exampleMenuItem")

// Theming
apply(from = rootProject.file("template/themeregistrysources.gradle.kts"))

val customCoreComponentsThemeRegistrySource: IviThemeRegistrySourceConfig by project.extra
val customCommunicationsThemeRegistrySource: IviThemeRegistrySourceConfig by project.extra
val customHvacThemeRegistrySource: IviThemeRegistrySourceConfig by project.extra
val customMainMenuThemeRegistrySource: IviThemeRegistrySourceConfig by project.extra
val customMediaThemeRegistrySource: IviThemeRegistrySourceConfig by project.extra
val customNavAppComponentsThemeRegistrySource: IviThemeRegistrySourceConfig by project.extra
val customSystemStatusThemeRegistrySource: IviThemeRegistrySourceConfig by project.extra
val customSystemUiThemeRegistrySource: IviThemeRegistrySourceConfig by project.extra
val defaultThemeComponentsSelectorThemeRegistrySource: IviThemeRegistrySourceConfig by project.extra

ivi {
    application {
        enabled = true
        iviInstances {
            create(IviInstanceIdentifier.default) {
                applyGroups {
                    selectGroups()
                }
                frontends {
                    add(exampleFrontend)
                }
                menuItems {
                    addFirst(exampleMenuItem to exampleFrontend)
                }
                // Configures theming sources.
                theming {
                    addRegistrySources(
                        // These are the theming registry sources per IVI domains.
                        customCoreComponentsThemeRegistrySource,
                        customCommunicationsThemeRegistrySource,
                        customHvacThemeRegistrySource,
                        customMainMenuThemeRegistrySource,
                        customMediaThemeRegistrySource,
                        customNavAppComponentsThemeRegistrySource,
                        customSystemStatusThemeRegistrySource,
                        customSystemUiThemeRegistrySource,
                        // While new theming sources are added, this source selects the defined
                        // theming categories as the default theming sources.
                        defaultThemeComponentsSelectorThemeRegistrySource,
                    )
                }
            }
        }
        services {
            applyGroups {
                selectGroups()
            }
        }
    }
}

/**
 * Selects the build time configuration groups included in the IVI application.
 *
 * __Note:__ The build time configuration group `IviPlatform.debugGroup` is only added in `debug`
 * build types.
 */
fun IviDefaultsGroupsSelectionConfigurator.selectGroups() {
    includeDefaultPlatformGroups()
    include(
        IviAppsuite.appStoreGroup,
        IviAppsuite.bluetoothGroup,
        IviAppsuite.communicationsGroup,
        IviAppsuite.hvacGroup,
        IviAppsuite.mediaGroup,
        IviAppsuite.messagingGroup,
        IviAppsuite.navAppComponentsGroup,
        IviAppsuite.navigationGroup,
        IviAppsuite.systemStatusGroup,
        IviAppsuite.userProfilesGroup,
        IviAppsuite.vehicleSettingsGroup,
    )
}

android {
    namespace = "com.example.ivi.template.app"
    defaultConfig {
        applicationId = "com.example.ivi.template.app"
    }

    buildFeatures {
        dataBinding = true
    }

    signingConfigs.maybeCreate("release")
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    // Split the output into multiple APKs based on their ABI.
    splits.abi {
        isEnable = true
        reset()
        include(*ProjectAbis.enabledAbis)
    }
}

dependencies {
    implementation(iviDependencies.tomtomAutomotiveAndroidCar)
    implementation(libraries.iviPlatformThemingApiCommonAttributes)
    implementation(libraries.iviPlatformFrameworkApiProductDebugPermissions)
    implementation(libraries.iviPlatformFrameworkApiProductDefaultActivity)
    implementation(libraries.iviPlatformFrameworkApiProductDefaultApplication)
}
