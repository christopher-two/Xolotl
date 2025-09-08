package org.christophertwo.xlotl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import org.christophertwo.xlotl.presentation.theme.XólotlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XólotlTheme {
                Surface(
                    color = colorScheme.background,
                    contentColor = colorScheme.onBackground,
                    modifier = Modifier.fillMaxSize(),
                    content = {}
                )
            }
        }
    }
}