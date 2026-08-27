package com.codewiththiru.platform.updates.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewiththiru.platform.updates.api.UpdateEffect
import com.codewiththiru.platform.updates.api.UpdateManager
import com.codewiththiru.platform.updates.api.UpdateResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.receiveAsFlow

class UpdatesViewModel(
    private val updateManager: UpdateManager,
) : ViewModel() {
    val updateState: Flow<UpdateResult?> = updateManager.updateState

    private val _updateEffect = kotlinx.coroutines.channels.Channel<UpdateEffect>()
    val updateEffect: Flow<UpdateEffect> = _updateEffect.receiveAsFlow()

    fun checkForUpdates(
        activity: Activity,
        currentVersionCode: Int,
    ) {
        viewModelScope.launch {
            val effect = updateManager.checkAndPrompt(activity, currentVersionCode)
            _updateEffect.send(effect)
        }
    }

    fun onUpdateDeferred() {
        viewModelScope.launch {
            updateManager.recordUserDeferral()
        }
    }
}
