package com.levercore.guitartuner.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val TunerBackground=Color(0xFF15171D)
val TunerPanel=Color(0xFF23262D)
val TunerGreen=Color(0xFF67E3A2)
val TunerFlat=Color(0xFFF06372)
val TunerSharp=Color(0xFFF5A64A)
private val colors=darkColorScheme(
    primary=TunerGreen,onPrimary=Color(0xFF0C261C),secondary=Color(0xFF9FC4F9),
    background=TunerBackground,surface=TunerPanel,surfaceVariant=Color(0xFF30343D),
    onSurface=Color(0xFFF3F5F8),onBackground=Color(0xFFF3F5F8),
    onSurfaceVariant=Color(0xFFBAC2CC),outline=Color(0xFF4C5663),error=TunerFlat
)
private val shapes=Shapes(small=RoundedCornerShape(12.dp),medium=RoundedCornerShape(18.dp),large=RoundedCornerShape(24.dp))
@Composable fun TunerTheme(content:@Composable ()->Unit){MaterialTheme(colorScheme=colors,shapes=shapes,content=content)}
