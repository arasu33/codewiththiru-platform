package com.codewiththiru.ads.provider.admob

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.LoadAdError
import org.junit.Assert.assertEquals
import org.junit.Test

class AdErrorMapperTest {

    @Test
    fun testMapLoadError() {
        val loadError = LoadAdError(1, "Test error", "com.test", null, null)
        val mappedError = AdErrorMapper.mapLoadError(loadError)

        assertEquals(1, mappedError.errorCode)
        assertEquals("Test error", mappedError.message)
        assertEquals("com.test", mappedError.domain)
    }

    @Test
    fun testMapShowError() {
        val showError = AdError(2, "Show error", "com.test")
        val mappedError = AdErrorMapper.mapShowError(showError)

        assertEquals(2, mappedError.errorCode)
        assertEquals("Show error", mappedError.message)
        assertEquals("com.test", mappedError.domain)
    }
}
