package com.ucsm.campusmarket.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ucsm.campusmarket.ui.theme.MintSoft
import com.ucsm.campusmarket.ui.theme.MintLight

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MintSoft
        )
    ) {
        Text(text)
    }
}