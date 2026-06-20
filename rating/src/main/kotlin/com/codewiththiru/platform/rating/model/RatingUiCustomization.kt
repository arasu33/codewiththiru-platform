package com.codewiththiru.platform.rating.model

import androidx.annotation.StringRes
import com.codewiththiru.platform.rating.R

/**
 * Customizes the UI copy of the rating component.
 * Supports string resource resolution for localization.
 */
data class RatingUiCustomization(
    @StringRes val titleRes: Int = R.string.rating_default_title,
    @StringRes val messageRes: Int = R.string.rating_default_message,
    @StringRes val positiveButtonTextRes: Int = R.string.rating_default_submit,
    @StringRes val negativeButtonTextRes: Int = R.string.rating_default_dismiss,
    @StringRes val starSelectedContentDescriptionRes: Int = R.string.rating_star_selected_cd,
    @StringRes val starUnselectedContentDescriptionRes: Int = R.string.rating_star_unselected_cd
)
