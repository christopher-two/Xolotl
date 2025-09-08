package org.christophertwo.xlotl.presentation.components

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import org.christophertwo.xlotl.R

@Composable
fun CreatedByOverride() {
    val context = LocalContext.current
    TextButton(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, "https://www.override.com.mx".toUri())
            context.startActivity(intent)
        },
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(
                    text = stringResource(R.string.created_by),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onBackground
                )
                Icon(
                    painter = painterResource(R.drawable.overrideblanco),
                    contentDescription = "Override Logo",
                    tint = colorScheme.onBackground,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(R.string.override),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onBackground
                )
            }
        }
    )
}
