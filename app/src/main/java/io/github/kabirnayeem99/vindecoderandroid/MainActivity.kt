package io.github.kabirnayeem99.vindecoderandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kabirnayeem99.vindecoder.VIN
import io.github.kabirnayeem99.vindecoderandroid.ui.theme.VINDecoderKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VINDecoderKotlinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    VINText()
                }
            }
        }
    }
}

@Composable
fun VINText() {
    val randomVin = remember { VIN(number = "WP0ZZZ99ZTS392124") }

    LazyColumn(
        modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(text = "VIN Number: ${randomVin.number}")
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text(text = "WMI: ${randomVin.wmi}")
            Text(text = "VDS: ${randomVin.vds}")
            Text(text = "VIS: ${randomVin.vis}")
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Text("Model year is " + randomVin.modelYear)
            Text("Serial number is " + randomVin.serialNumber)
            Text("Assembly plant is " + randomVin.assemblyPlant)
            Text("Manufacturer is " + randomVin.manufacturer)
            Text("Year is " + randomVin.year)
            Text("Region is " + randomVin.region)
            Spacer(modifier = Modifier.height(16.dp))
        }
//
//
//        item {
//            Text("Model is " + model.value)
//            Text("Make is " + make.value)
//            Text("Vehicle type is " + vehicleType.value)
//            Spacer(modifier = Modifier.height(16.dp))
//        }

        item {
            Text("Checksum is ${randomVin.checksum}");
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun VINTextPreview() {
    VINDecoderKotlinTheme {
        VINText()
    }
}