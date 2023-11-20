package io.github.kabirnayeem99.vindecoder

import io.github.kabirnayeem99.vindecoder.src.NHTSA
import io.github.kabirnayeem99.vindecoder.src.StillInAlpha
import io.github.kabirnayeem99.vindecoder.src.VINGenerator
import io.github.kabirnayeem99.vindecoder.src.manufacturers
import io.github.kabirnayeem99.vindecoder.src.yearMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

private const val UNKNOWN_VALUE = "N/A"

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

    @StillInAlpha
    suspend fun getCountry(): String {
        val countryCode = number.substring(0, 2)
        return withContext(Dispatchers.Default) {
            when (region) {
                "AF" -> {
                    when {
                        Regex("^[Aa][A-Ha-h]").matches(countryCode) -> "South Africa"
                        Regex("^[Aa][J-Nj-n]").matches(countryCode) -> "Côte d'Ivoire"
                        Regex("^[Aa][P/-Aa]").matches(countryCode) -> "Unassigned"
                        Regex("^[Bb][A-Ea-e]").matches(countryCode) -> "Angola"
                        Regex("^[Bb][F-Kf-k]").matches(countryCode) -> "Kenya"
                        Regex("^[Bb][L/-Br/-lr]").matches(countryCode) -> "Tanzania"
                        Regex("^[Bb][Uu]").matches(countryCode) -> "Uganda"
                        Regex("^[Bb][S/-B0b-s]").matches(countryCode) -> "Unassigned"
                        Regex("^[Cc][A-Ea-e]").matches(countryCode) -> "Benin"
                        Regex("^[Cc][F-Kf-k]").matches(countryCode) -> "Madagascar"
                        Regex("^[Cc][L/-Cr/-lr]").matches(countryCode) -> "Tunisia"
                        Regex("^[Cc][S/-C0c-s]").matches(countryCode) -> "Unassigned"
                        Regex("^[Dd][A-Ea-e]").matches(countryCode) -> "Egypt"
                        Regex("^[Dd][F/-Dk/-d]").matches(countryCode) -> "Morocco"
                        Regex("^[Dd][L/-Dr/-d]").matches(countryCode) -> "Zambia"
                        Regex("^[Dd][S/-D0d-s]").matches(countryCode) -> "Unassigned"
                        Regex("^[Ee][A-Ee-e]").matches(countryCode) -> "Ethiopia"
                        Regex("^[Ee][F/-E0e-f]").matches(countryCode) -> "Mozambique"
                        Regex("^[Ee][L/-E0e-l]").matches(countryCode) -> "Unassigned"
                        Regex("^[Ff][A-Ef/-e]").matches(countryCode) -> "Ghana"
                        Regex("^[Ff][F-Kf-k]").matches(countryCode) -> "Nigeria"
                        Regex("^[Ff][L/-F0f-l]").matches(countryCode) -> "Unassigned"
                        Regex("^[Gg][A-Gg]").matches(countryCode) -> "Unassigned"
                        Regex("^[Hh][A-H0h]").matches(countryCode) -> "Unassigned"
                        else -> "Unknown"
                    }
                }

                "AS" -> {
                    when {
                        Regex("^[J]").matches(countryCode) -> "Japan"
                        Regex("^[K][A-E]").matches(countryCode) -> "Sri Lanka"
                        Regex("^[Kk][F-Kf-k]").matches(countryCode) -> "Israel"
                        Regex("^[Kk][L-Rk-r]").matches(countryCode) -> "Korea (South)"
                        Regex("^[Kk][S-Tk-t]").matches(countryCode) -> "Jordan"
                        Regex("^[Ll]").matches(countryCode) -> "China (Mainland)"
                        Regex("^[Mm][A-Ea-eMm][Y0]").matches(countryCode) -> "India"
                        Regex("^[Mm][F-Kf-k]").matches(countryCode) -> "Indonesia"
                        Regex("^[Mm][L-Rm-r]").matches(countryCode) -> "Thailand"
                        Regex("^[Mm][S]").matches(countryCode) -> "Myanmar"
                        Regex("^[Mm][U]").matches(countryCode) -> "Mongolia"
                        Regex("^[Mm][X]").matches(countryCode) -> "Kazakhstan"
                        Regex("^[Nn][A-Ea-e]").matches(countryCode) -> "Iran"
                        Regex("^[Nn][F-Kf-k]").matches(countryCode) -> "Pakistan"
                        Regex("^[Nn][L-Rn-r]").matches(countryCode) -> "Turkey"
                        Regex("^[Nn][S-Tn-t]").matches(countryCode) -> "Uzbekistan"
                        Regex("^[Nn][U/-N0n-u]").matches(countryCode) -> "Unassigned"
                        Regex("^[P][A-E]").matches(countryCode) -> "Philippines"
                        Regex("^[P][F-K]").matches(countryCode) -> "Singapore"
                        Regex("^[Pp][L-Rp-r]").matches(countryCode) -> "Malaysia"
                        Regex("^[Pp][S-Tp-t]").matches(countryCode) -> "Bangladesh"
                        Regex("^[Pp][U/-P0p-u]").matches(countryCode) -> "Unassigned"
                        Regex("^[Rr][A-Er/-e]").matches(countryCode) -> "United Arab Emirates"
                        Regex("^[Rr][F-Kr/-k]").matches(countryCode) -> "Taiwan"
                        Regex("^[Rr][L-Rr/-l]").matches(countryCode) -> "Vietnam"
                        Regex("^[Rr][S-Tr-s]").matches(countryCode) -> "Saudi Arabia"
                        Regex("^[Rr][1-7]").matches(countryCode) -> "Hong Kong"
                        else -> "Unknown"
                    }
                }

                "EU" -> {
                    when {
                        Regex("^[Ss][A-Ms/-m]").matches(countryCode) -> "United Kingdom"
                        Regex("^[Ss][N-Ts-t]").matches(countryCode) -> "Germany (formerly East Germany)"
                        Regex("^[Ss][U-Zs-z]").matches(countryCode) -> "Poland"
                        Regex("^[Ss]1|[Ss]2").matches(countryCode) -> "Latvia"
                        Regex("^[Ss]3").matches(countryCode) -> "Georgia"
                        Regex("^[Ss]4").matches(countryCode) -> "Iceland"
                        Regex("^[Ss]5|[Ss]0").matches(countryCode) -> "Unassigned"
                        Regex("^[Tt][A-Ht/-h]").matches(countryCode) -> "Switzerland"
                        Regex("^[Tt][J-Pt/-p]").matches(countryCode) -> "Czech Republic"
                        Regex("^[Tt][R-Vt-v]").matches(countryCode) -> "Hungary"
                        Regex("^[Tt][W/-Tt-t][3-9]").matches(countryCode) -> "Portugal"
                        Regex("^[Tt][W/-Tt-t]0").matches(countryCode) -> "Unassigned"
                        Regex("^[Uu][A-Gu/-g]").matches(countryCode) -> "Unassigned"
                        Regex("^[Uu][H-Mu/-m]").matches(countryCode) -> "Denmark"
                        Regex("^[Uu][N-Uu-u][N-Tn-t]").matches(countryCode) -> "Ireland"
                        Regex("^[Uu][U-Zu-z]").matches(countryCode) -> "Romania"
                        Regex("^[Uu]1|[Uu]2").matches(countryCode) -> "North Macedonia"
                        Regex("^[Uu]3|[Uu]4").matches(countryCode) -> "Slovakia"
                        Regex("^[Uu]5|[Uu]6|[Uu]7").matches(countryCode) -> "Bosnia and Herzegovina"
                        Regex("^[Vv][A-Ev/-e]").matches(countryCode) -> "Austria"
                        Regex("^[Vv][F-Rv/-r]").matches(countryCode) -> "France"
                        Regex("^[Vv][S-Wv-w]").matches(countryCode) -> "Spain"
                        Regex("^[Vv][X/-Vv-v]2").matches(countryCode) -> "Serbia"
                        Regex("^[Vv]3|[Vv]4|[Vv]5").matches(countryCode) -> "Croatia"
                        Regex("^[Vv]6|[Vv]7|[Vv]8|[Vv]9|[Vv]0").matches(countryCode) -> "Estonia"
                        Regex("^[Ww]").matches(countryCode) -> "Germany (formerly West Germany)"
                        Regex("^[Xx][A-Ea-e]").matches(countryCode) -> "Bulgaria"
                        Regex("^[Xx][F-Kx/-k]").matches(countryCode) -> "Greece"
                        Regex("^[Xx][L-Rx/-r]").matches(countryCode) -> "Netherlands"
                        Regex("^[Xx][S-Wx/-w]").matches(countryCode) -> "Russia (former USSR)"
                        Regex("^[Xx][Xx]|[Xx]Y").matches(countryCode) -> "Luxembourg"
                        Regex("^[Xx]Z|[Xx]0").matches(countryCode) -> "Russia"
                        Regex("^[Yy][A-Ey/-e]").matches(countryCode) -> "Belgium"
                        Regex("^[Yy][F-Ky/-k]").matches(countryCode) -> "Finland"
                        Regex("^[Yy][L-Ry/-r]").matches(countryCode) -> "Malta"
                        Regex("^[Yy][S-Wy/-w]").matches(countryCode) -> "Sweden"
                        Regex("^[Yy][X-Yy-y]0").matches(countryCode) -> "Norway"
                        Regex("^[Yy]3|[Yy]4|[Yy]5").matches(countryCode) -> "Belarus"
                        Regex("^[Yy]6|[Yy]7|[Yy]8|[Yy]9|[Yy]0").matches(countryCode) -> "Ukraine"
                        Regex("^[Zz][A-Uz/-u]").matches(countryCode) -> "Italy"
                        Regex("^[Zz][V-Zz-z]").matches(countryCode) -> "Unassigned"
                        Regex("^[Zz][X-Zz-z]").matches(countryCode) -> "Slovenia"
                        Regex("^[Zz]3|[Zz]4|[Zz]5").matches(countryCode) -> "Lithuania"
                        Regex("^[Zz]6|[Zz]7|[Zz]8|[Zz]9|[Zz]0").matches(countryCode) -> "Russia"
                        else -> "Unknown"
                    }
                }

                "NA" -> {
                    when {
                        Regex(
                            "^[1,4,5,7]f", RegexOption.IGNORE_CASE
                        ).matches(countryCode) -> "United States"

                        Regex("^2", RegexOption.IGNORE_CASE).matches(countryCode) -> "Canada"
                        Regex("^3[A-X]", RegexOption.IGNORE_CASE).matches(countryCode) -> "Mexico"
                        Regex(
                            "^3Y/-37", RegexOption.IGNORE_CASE
                        ).matches(countryCode) -> "Costa Rica"

                        Regex(
                            "^38/-39", RegexOption.IGNORE_CASE
                        ).matches(countryCode) -> "Cayman Islands"

                        Regex("^30", RegexOption.IGNORE_CASE).matches(countryCode) -> "Unassigned"
                        else -> "Unknown"
                    }
                }

                "OC" -> {
                    when {
                        Regex("^[6]").matches(countryCode) -> "Australia"
                        Regex("^[7][A-Ea-e]").matches(countryCode) -> "New Zealand"
                        else -> "Unknown"
                    }
                }

                "SA" -> {
                    when {
                        Regex("^[8][A-Ea-e]").matches(countryCode) -> "Argentina"
                        Regex("^[8][F-Kf-k]").matches(countryCode) -> "Chile"
                        Regex("^[8][L-Rl-r]").matches(countryCode) -> "Ecuador"
                        Regex("^[8][S-Ws-w]").matches(countryCode) -> "Peru"
                        Regex("^[8][X-Z8/-2x-z]").matches(countryCode) -> "Venezuela"
                        Regex("^[9][A-Ea-e]").matches(countryCode) -> "Brazil"
                        Regex("^[9][F-Kf-k]").matches(countryCode) -> "Colombia"
                        Regex("^[9][L-Rl-r]").matches(countryCode) -> "Paraguay"
                        Regex("^[9][S-Ws-w]").matches(countryCode) -> "Uruguay"
                        Regex("^[9][X-Z9/-2x-z]").matches(countryCode) -> "Trinidad & Tobago"
                        else -> "Unknown"
                    }
                }

                else -> "Unknown"
            }
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

    suspend fun getMake(): String? {
        fetchExtendedVehicleInfo()
        return vehicleInfo["Make"]?.toString()
    }

    suspend fun getModel(): String? {
        fetchExtendedVehicleInfo()
        return vehicleInfo["Model"]?.toString()
    }

    suspend fun getVehicleType(): String? {
        fetchExtendedVehicleInfo()
        return vehicleInfo["VehicleType"]?.toString()
    }

    override fun toString(): String = wmi + vds + vis
}
