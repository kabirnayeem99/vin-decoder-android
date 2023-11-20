package io.github.kabirnayeem99.vindecoder

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class VinGeneratorUnitTest {

    private lateinit var vin: VIN

    @Before
    fun setUp() {
        vin = VIN.generate()
    }

    @Test
    fun testValidity() {
        assertTrue(vin.valid())
    }
}
