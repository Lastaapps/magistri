package cz.lastaapps.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun UsableDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest, properties) {
        val outsideInteraction = remember { MutableInteractionSource() }
        Box(
            Modifier
                .fillMaxSize(.9f)
                .clickable(outsideInteraction, null, onClick = onDismissRequest),
            contentAlignment = Alignment.Center,
        ) {
            val backgroundIntegration = remember { MutableInteractionSource() }
            Box(Modifier.clickable(backgroundIntegration, null, onClick = {})) {
                Surface(shape = MaterialTheme.shapes.extraLarge) {
                    Box(Modifier.padding(24.dp)) {
                        content()
                    }
                }
            }
        }
    }
}
