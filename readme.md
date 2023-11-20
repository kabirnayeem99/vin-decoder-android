VIN Decoder Android
================

[![](https://jitpack.io/v/kabirnayeem99/vin-decoder-android.svg)](https://jitpack.io/#kabirnayeem99/vin-decoder-android)

A VIN decoding, validation, and generation library for Android, written in Kotlin.

**VIN Decoder Android** provides a simple decoding and validation library for Vehicle Identification
Numbers (
VINs) based on
ISO 3779:2009 and World Manufacturer Identifiers (WMIs) based on ISO 3780:2009. It further supports
generation of
synthetic VINs derived from valid WMIs.

This library is heavily, heavily and heavily inspired
by [vin_decoder_dart](https://github.com/adaptant-labs/vin-decoder-dart).

The decoder can be used standalone in an offline mode (the default behaviour, as per earlier
versions of the API), or
can be further enriched by querying additional VIN information from the [NHTSA Vehicle API][nhtsa],
such as the precise
make, model, and vehicle type in extended mode. Note that synthetic VINs will fail lookup in the
NHTSA API, and should
only be used for experimentation.

[nhtsa]: https://vpic.nhtsa.dot.gov/api/Home

## Usage

A simple usage example:

```kotlin
val vin = VIN(number = "WP0ZZZ99ZTS392124", extended = true)

println('WMI: ${vin.wmi}')
println('VDS: ${vin.vds}')
println('VIS: ${vin.vis}')

println("Model year is " + vin.modelYear)
println("Serial number is " + vin.serialNumber)
println("Assembly plant is " + vin.assemblyPlant)
println("Manufacturer is " + vin.getManufacturer)
println("Year is " + vin.year)
println("Region is " + vin.region)
println("VIN string is $vin")

// The following calls are to the NHTSA DB, and are carried out asynchronously
val make = vin.getMakeAsync()
println("Make is $make")

val model = vin.getModelAsync()
println("Model is $model")

val type = vin.getVehicleTypeAsync()
println("Type is $type")

val generated = VIN.generate()
println('Randomly Generated VIN is ${generated}')
```

which produces the following:

```shell script
WMI: WP0
VDS: ZZZ99Z
VIS: TS392124
Model year is T
Serial number is 92124
Assembly plant is S
Manufacturer is Porsche
Year is 1996
Region is EU
VIN string is WP0ZZZ99ZTS392124
Make is Porsche
Model is 911
Type is Passenger Car
Randomly Generated VIN is NMC111LKPRD100007
```

## Features and bugs

Please file feature requests and bugs at the [issue tracker][tracker].

[tracker]: https://github.com/kabirnayeem99/vin-decoder-android/issues

## License

Licensed under the terms of the Apache 2.0 license, the full version of which can be found in the
[LICENSE](https://raw.githubusercontent.com/kabirnayeem99/vin-decoder-android/master/LICENSE)
file included in the distribution.
