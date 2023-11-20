package io.github.kabirnayeem99.vindecoder.src

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale


// NHTSA Results not relevant for a specific vehicle can be either null or N/A
const val RESULT_NOT_APPLICABLE = "Not Applicable"
private const val URI_BASE = "https://vpic.nhtsa.dot.gov/api/vehicles"

internal class NHTSA {
    private val http by lazy { HTTP() }

    private suspend fun decodeVin(vin: String): NHTSAVehicleInfo? {
        return withContext(Dispatchers.IO) {
            try {
                val path = "$URI_BASE/DecodeVin/$vin?format=json"
                http.get(path, NHTSAVehicleInfo::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    // Obtain a map of key/value pairs containing known values for a given [vin]
    suspend fun decodeVinValues(vin: String): Map<String, Any?>? {
        return withContext(Dispatchers.IO) {
            try {
                val path = "$URI_BASE/DecodeVinValues/$vin?format=json"
                val json = http.get(path) ?: return@withContext null
                val results = json["Results"] as List<Map<String, Any?>>
                val map = results.firstOrNull()
                map?.filterValues { it != null && it != RESULT_NOT_APPLICABLE && it != "" }
            } catch (e: Exception) {
                emptyMap()
            }
        }
    }
}

internal data class NHTSAResult(
    val variable: String? = "",
    val value: String? = "",
    val valueId: String? = "",
    val variableId: Int? = 0
)


internal data class NHTSAVehicleInfo(
    val message: String = "",
    val results: List<NHTSAResult>?,
    val count: Int = 0,
    val searchCriteria: String = ""
) {

    private fun normalizeStringValue(s: String?): String {
        return s?.split(' ')?.joinToString(" ") { it.lowercase().capitalize(Locale.ENGLISH) } ?: ""
    }

    // Lookup the value of a variable by its [variableId] in the NHTSA DB results
    fun valueFromId(variableId: Int): String? {
        val result = results?.singleOrNull { it.variableId == variableId }
        return result?.value?.let { normalizeStringValue(it) }
    }

    // Lookup the value of a named [variable] in the NHTSA DB results
    fun value(variable: String): String? {
        val result = results?.singleOrNull { it.variable == variable }
        return result?.value?.let { normalizeStringValue(it) }
    }
}
