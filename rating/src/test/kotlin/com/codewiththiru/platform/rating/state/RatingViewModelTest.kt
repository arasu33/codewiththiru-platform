package com.codewiththiru.platform.rating.state

import com.codewiththiru.platform.rating.model.RatingConfig
import com.codewiththiru.platform.rating.model.RatingPromptType
import com.codewiththiru.platform.rating.repository.RatingRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RatingViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository: RatingRepository = mockk(relaxed = true)

    private val config = RatingConfig.Builder()
        .setPlayReviewThreshold(5)
        .setPromptType(RatingPromptType.Dialog)
        .build()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is set correctly`() {
        val viewModel = RatingViewModel(config, repository)
        assertEquals(RatingPromptType.Dialog, viewModel.uiState.value.promptType)
        assertEquals(0, viewModel.uiState.value.selectedStars)
    }

    @Test
    fun `StarSelected updates ui state`() {
        val viewModel = RatingViewModel(config, repository)
        viewModel.onAction(RatingAction.StarSelected(4))
        assertEquals(4, viewModel.uiState.value.selectedStars)
    }
}
