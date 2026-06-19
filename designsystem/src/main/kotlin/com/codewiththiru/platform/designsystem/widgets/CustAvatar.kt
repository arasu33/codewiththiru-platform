@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.codewiththiru.platform.designsystem.components.CustText

@Composable
fun CustAvatar(
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    initials: String? = null,
    size: AvatarSize = AvatarSize.Medium,
    contentDescription: String? = null
) {
    val sizeModifier = Modifier.size(size.dimension)
    
    val semanticModifier = if (contentDescription != null) {
        modifier.semantics { this.contentDescription = contentDescription }
    } else {
        modifier
    }

    Box(
        modifier = semanticModifier
            .then(sizeModifier)
            .clip(CircleShape)
            .background(CustAvatarDefaults.containerColor),
        contentAlignment = Alignment.Center
    ) {
        if (painter != null) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        } else if (!initials.isNullOrEmpty()) {
            val style = when (size) {
                AvatarSize.Small -> CustAvatarDefaults.initialsStyleSmall
                AvatarSize.Medium -> CustAvatarDefaults.initialsStyleMedium
                AvatarSize.Large -> CustAvatarDefaults.initialsStyleLarge
            }
            CustText(
                text = initials.take(2).uppercase(),
                style = style,
                color = CustAvatarDefaults.contentColor
            )
        } else {
            // Placeholder fallback (an icon or just the solid color which is already applied via background)
            CustShimmerBox(modifier = Modifier.matchParentSize(), shape = CircleShape)
        }
    }
}
