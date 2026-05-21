package com.ucsm.campusmarket.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ucsm.campusmarket.ui.theme.MintSoft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String = "Campus Market"
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MintSoft,
            titleContentColor = Color(0xFF2E2E2E)
        )
    )
}