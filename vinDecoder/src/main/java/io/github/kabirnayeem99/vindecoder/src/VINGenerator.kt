package io.github.kabirnayeem99.vindecoder.src

import kotlin.random.Random

// VINs do not use the letters O, I, or Q to avoid confusion with numbers
private const val VIN_CHARS = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789"

internal class VINGenerator {
    private val random = Random

    // Generate a random (valid) WMI
    private fun generateWmi(): String {
        val wmi = manufacturers.keys.elementAt(random.nextInt(manufacturers.size))

        // If the manufacturer produces less than 500 vehicles/year, the 3rd digit
        // of the WMI must be 9.
        return if (wmi.length == 2) wmi + '9' else wmi
    }

    // Generate a random (valid) VIS using a valid model year followed by a
    // default assembly plant definition (A) and a random production sequence
    // number.
    private fun generateVis(): String {
        return yearMap.keys.elementAt(random.nextInt(yearMap.size)) + 'A' + Random.nextInt(1000000)
            .toString().padStart(6, '0')
    }

    private fun generateVds(): String {
        val randomVds = (0 until 5).map { VIN_CHARS.random() }.joinToString("")
        // Random check digit
        return "$randomVds${Random.nextInt(10)}"
    }

    // Generate a random VIN
    fun generate(): String {
        return generateWmi() + generateVds() + generateVis()
    }
}
