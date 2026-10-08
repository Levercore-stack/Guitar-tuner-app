package com.levercore.guitartuner.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val TunerBackground=Color(0xFF111720)
val TunerPanel=Color(0xFF1A242E)
val TunerNeck=Color(0xFF202B35)
val TunerSelection=Color(0xFF294251)
val TunerOutline=Color(0xFF405360)
val TunerAccent=Color(0xFF91C2D4)
val TunerGreen=Color(0xFF83D1AC)
val TunerFlat=Color(0xFFE4848D)
val TunerSharp=Color(0xFFE0B679)
private val colors=darkColorScheme(
    primary=TunerAccent,onPrimary=TunerBackground,secondary=TunerGreen,
    background=TunerBackground,surface=TunerPanel,surfaceVariant=TunerSelection,
    onSurface=Color(0xFFF0F3F5),onBackground=Color(0xFFF0F3F5),
    onSurfaceVariant=Color(0xFFB4C0C9),outline=TunerOutline,error=TunerFlat
)
private val shapes=Shapes(small=RoundedCornerShape(10.dp),medium=RoundedCornerShape(14.dp),large=RoundedCornerShape(20.dp))
@Composable fun TunerTheme(content:@Composable ()->Unit){
    MaterialTheme(colorScheme=colors,shapes=shapes,content=content)
}
