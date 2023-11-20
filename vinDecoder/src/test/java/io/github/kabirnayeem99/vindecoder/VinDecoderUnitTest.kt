package io.github.kabirnayeem99.vindecoder

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VinDecoderUnitTest {

    @Test
    fun testEuVin() {
        val vin = VIN(number = "WP0ZZZ99ZTS392124")

        assertTrue(vin.valid())
        assertEquals("EU", vin.region)
        assertEquals("Porsche", vin.manufacturer)
    }

    @Test
    fun testAsVin() {
        val vin = VIN(number = "JS1VX51L7X2175460")

        assertTrue(vin.valid())
        assertEquals("AS", vin.region)
    }

    @Test
    fun testTwoCharacterWmiVin() {
        val vin = VIN(number = "5TENL42N94Z436445")

        assertTrue(vin.valid())
        assertEquals("NA", vin.region)
        assertEquals("Toyota - trucks", vin.manufacturer)
    }
}
