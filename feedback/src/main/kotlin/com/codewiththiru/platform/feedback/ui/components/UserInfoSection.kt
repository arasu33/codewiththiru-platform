@file:Suppress("FunctionNaming", "LongParameterList", "UnusedParameter")

package com.codewiththiru.platform.feedback.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.components.CustTextField
import com.codewiththiru.platform.feedback.model.FeedbackThemeConfig
import com.codewiththiru.platform.feedback.model.FeedbackVisibility

@Composable
internal fun UserInfoSection(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String?,
    visibility: FeedbackVisibility,
    themeConfig: FeedbackThemeConfig,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (visibility.showNameField) {
            CustTextField(
                value = name,
                onValueChange = onNameChange,
                label = { com.codewiththiru.platform.designsystem.components.CustText("Name (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        val emailLabel = if (visibility.requireEmail) "Email (Required)" else "Email (Optional)"
        
        CustTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { com.codewiththiru.platform.designsystem.components.CustText(emailLabel) },
            errorText = emailError,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
