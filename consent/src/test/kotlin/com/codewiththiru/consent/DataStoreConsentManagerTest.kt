package com.codewiththiru.consent

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.codewiththiru.consent.manager.DataStoreConsentManager
import com.codewiththiru.consent.model.ConsentCategory
import com.codewiththiru.consent.model.ConsentStatus
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

@RunWith(RobolectricTestRunner::class)
class DataStoreConsentManagerTest {
    private lateinit var context: Context
    private lateinit var consentManager: DataStoreConsentManager

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        consentManager = DataStoreConsentManager(context)
    }

    @Test
    fun testNecessaryCategoryIsAlwaysGranted() =
        runTest {
            val snapshot = consentManager.consentSnapshot.first()
            assertTrue(snapshot.isGranted(ConsentCategory.NECESSARY))
        }

    @Test
    fun testGrantAllSetsAllCategoriesGranted() =
        runTest {
            consentManager.grantAll()
            val snapshot = consentManager.consentSnapshot.first()
            assertTrue(snapshot.isGranted(ConsentCategory.ANALYTICS))
            assertTrue(snapshot.isGranted(ConsentCategory.ADVERTISING))
            assertTrue(snapshot.isGranted(ConsentCategory.FUNCTIONAL))
        }

    @Test
    fun testDenyAllSetsAllCategoriesDenied() =
        runTest {
            consentManager.denyAll()
            val snapshot = consentManager.consentSnapshot.first()
            assertEquals(ConsentStatus.DENIED, snapshot.consents[ConsentCategory.ANALYTICS])
            assertEquals(ConsentStatus.DENIED, snapshot.consents[ConsentCategory.ADVERTISING])
        }
}
