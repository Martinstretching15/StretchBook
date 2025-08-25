
package com.example.physionotes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(title: String, onBack: (() -> Unit)? = null, actions: (@Composable RowScope.() -> Unit)? = null) {
    SmallTopAppBar(
        title = { Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") }
            }
        },
        actions = { actions?.invoke(this) }
    )
}

object Icons {
    val Default = androidx.compose.material.icons.Icons.Default
}
