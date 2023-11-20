package io.github.kabirnayeem99.vindecoder

import io.github.kabirnayeem99.vindecoder.src.NHTSA
import io.github.kabirnayeem99.vindecoder.src.VINGenerator
import io.github.kabirnayeem99.vindecoder.src.manufacturers
import io.github.kabirnayeem99.vindecoder.src.yearMap
import java.util.Locale


class VIN(
    val number: String,
    val wmi: String = normalize(number).substring(0, 3),
    val vds: String = normalize(number).substring(3, 9),
    val vis: String = normalize(number).substring(9, 17),
    val extended: Boolean = false
) {


    private val nhtsa by lazy { NHTSA() }
    private var vehicleInfo: Map<String, Any?> = mapOf()


    fun valid(inputNumber: String? = null): Boolean {
        val value = normalize(inputNumber ?: number)
        return Regex("^[a-zA-Z0-9]+$").matches(value) && value.length == 17
    }

    companion object {
        private fun normalize(number: String): String =
            number.uppercase(Locale.ENGLISH).replace("-", "")

        fun generate(extended: Boolean = false): VIN {
            val generator = VINGenerator()
            val number = generator.generate()
            return VIN(number = number, extended = extended)
        }

    }

    val year: Int
        get() = yearMap[modelYear] ?: 0

    val region: String
        get() {
            return when {
                Regex("[A-H]", RegexOption.IGNORE_CASE).matches(number[0].toString()) -> "AF"
                Regex("[J-R]", RegexOption.IGNORE_CASE).matches(number[0].toString()) -> "AS"
                Regex("[S-Z]", RegexOption.IGNORE_CASE).matches(number[0].toString()) -> "EU"
                Regex("[1-5]", RegexOption.IGNORE_CASE).matches(number[0].toString()) -> "NA"
                Regex("[6-7]", RegexOption.IGNORE_CASE).matches(number[0].toString()) -> "OC"
                Regex("[8-9]", RegexOption.IGNORE_CASE).matches(number[0].toString()) -> "SA"
                else -> "Unknown"
            }
        }

    val manufacturer: String
        get() {
            return manufacturers[this.wmi] ?: run {
                val id = this.wmi.substring(0, 2)
                manufacturers[id] ?: "Unknown (WMI: ${this.wmi.uppercase(Locale.ENGLISH)})"
            }
        }

    val checksum: String?
        get() = if (region != "EU") normalize(number)[8].toString() else null

    val modelYear: String
        get() = normalize(number)[9].toString()

    val assemblyPlant: String
        get() = normalize(number)[10].toString()

    val serialNumber: String
        get() = normalize(number).substring(12, 17)

    private suspend fun fetchExtendedVehicleInfo() {
        if (vehicleInfo.isEmpty() && extended) {
            vehicleInfo = nhtsa.decodeVinValues(number) ?: emptyMap()
        }
    }

    suspend fun getMake(): String {
        fetchExtendedVehicleInfo()
        return vehicleInfo["Make"].toString()
    }

    suspend fun getModel(): String {
        fetchExtendedVehicleInfo()
        return vehicleInfo["Model"].toString()
    }

    suspend fun getVehicleType(): String {
        fetchExtendedVehicleInfo()
        return vehicleInfo["VehicleType"].toString()
    }

    override fun toString(): String = wmi + vds + vis
}
