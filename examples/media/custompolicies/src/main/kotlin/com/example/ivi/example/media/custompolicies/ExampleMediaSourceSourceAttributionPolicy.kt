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

package com.example.ivi.example.media.custompolicies

import com.tomtom.ivi.appsuite.media.api.common.core.IviMediaItem
import com.tomtom.ivi.appsuite.media.api.common.core.SourceInfo
import com.tomtom.ivi.appsuite.media.api.common.frontend.policies.MediaSourceAttributionPolicy
import com.tomtom.ivi.appsuite.media.api.common.frontend.policies.SourceAttributionFormat
import com.tomtom.tools.android.api.resourceresolution.drawable.DrawableResolver
import com.tomtom.tools.android.api.resourceresolution.drawable.ResourceDrawableResolver
import com.tomtom.tools.android.api.resourceresolution.string.StaticStringResolver
import com.tomtom.tools.android.api.resourceresolution.string.StringResolver

/**
 * A [MediaSourceAttributionPolicy] changes how the logos, name and colors of a media source are
 * displayed by the stock media player UI, by using the [SourceAttributionFormat] flags to determine
 * the best logo or name to display in that context.
 */
internal class ExampleMediaSourceSourceAttributionPolicy : MediaSourceAttributionPolicy {
    override fun getName(
        sourceInfo: SourceInfo?,
        mediaItem: IviMediaItem?,
        sourceAttributionFormat: SourceAttributionFormat,
    ): StringResolver? {
        return sourceAttributionFormat
            .takeIf { !it.preferWordMark }
            ?.let { sourceInfo?.displayName?.let { name -> StaticStringResolver(name) } }
    }

    override fun getLogo(
        sourceInfo: SourceInfo?,
        mediaItem: IviMediaItem?,
        sourceAttributionFormat: SourceAttributionFormat,
    ): DrawableResolver {
        return sourceAttributionFormat
            .takeIf { it.preferMonochrome }
            ?.let { ResourceDrawableResolver(R.drawable.ic_test_media_source_icon) }
            ?: ResourceDrawableResolver(R.drawable.ic_test_media_source_icon_bright)
    }
}
