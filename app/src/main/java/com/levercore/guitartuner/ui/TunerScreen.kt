package com.levercore.guitartuner.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.levercore.guitartuner.audio.PitchSource
import com.levercore.guitartuner.audio.MicrophoneCapture
import com.levercore.guitartuner.domain.*
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun TunerScreen(){
    val context=LocalContext.current
    val lifecycleOwner=LocalLifecycleOwner.current
    val mic:PitchSource=remember {MicrophoneCapture()}
    var tuning by remember {mutableStateOf(Tunings.presets.first())}
    var stringIndex by remember {mutableIntStateOf(0)}
    var running by remember {mutableStateOf(false)}
    var frequency by remember {mutableStateOf<Double?>(null)}
    var confidence by remember {mutableDoubleStateOf(0.0)}
    var status by remember {mutableStateOf("Tap Start microphone to begin.")}
    var permissionGranted by remember { mutableStateOf(
        ContextCompat.checkSelfPermission(context,Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED) }
    fun startMic(){
        if(!permissionGranted){status="Microphone permission required.";return}
        val result=mic.start(onSample={sample->
            frequency=sample?.frequencyHz
            confidence=sample?.confidence?:0.0
        },onError={error->status=error;running=false})
        running=result
        if(result)status="Listening. Pluck one string clearly."
    }
    val permission=rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        permissionGranted=granted
        if(granted)startMic() else status="Permission denied. Microphone remains off."
    }
    DisposableEffect(lifecycleOwner,mic){
        val observer=LifecycleEventObserver { _,event->
            if(event==Lifecycle.Event.ON_STOP){mic.stop();running=false;frequency=null;status="Microphone paused when app left foreground."}
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose{lifecycleOwner.lifecycle.removeObserver(observer);mic.stop()}
    }
    val selected=tuning.midiNotes[stringIndex]
    val targetHz=Tunings.frequency(selected)
    val detected=frequency?.takeIf{it>0.0}
    val cents=detected?.let{Tunings.centsFromTarget(it,selected)}
    val tuned=cents!=null && abs(cents)<=5.0
    val indicator=when{
        cents==null->MaterialTheme.colorScheme.onSurfaceVariant
        tuned->TunerGreen
        cents<0->TunerFlat
        else->TunerSharp
    }
    Column(Modifier.fillMaxSize().background(TunerBackground)
        .windowInsetsPadding(WindowInsets.safeDrawing)
        .verticalScroll(rememberScrollState())
        .padding(start=16.dp,end=16.dp,top=38.dp,bottom=14.dp),verticalArrangement=Arrangement.spacedBy(16.dp)){
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically){
            Column{Text("GUITAR TUNER",fontSize=30.sp,fontWeight=FontWeight.Black,fontStyle=androidx.compose.ui.text.font.FontStyle.Italic,letterSpacing=1.2.sp,color=Color.White)
                Text("PRECISION / SIX STRING",style=MaterialTheme.typography.labelSmall,color=TunerAccent)}
            Text(if(running)"● LISTENING" else "○ MIC OFF",color=if(running)TunerGreen else MaterialTheme.colorScheme.onSurfaceVariant,style=MaterialTheme.typography.labelSmall)
        }
        Card(Modifier.fillMaxWidth(),colors=CardDefaults.cardColors(containerColor=TunerPanel)){
            Column(Modifier.padding(18.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(8.dp)){
                Text("DETECTED NOTE",style=MaterialTheme.typography.labelMedium,color=MaterialTheme.colorScheme.onSurfaceVariant)
                Text(detected?.let{Tunings.noteWithOctave(Tunings.midiForFrequency(it))}?:"—",
                    style=MaterialTheme.typography.displayLarge,fontWeight=FontWeight.Bold,color=indicator)
                Text(if(cents==null)"Waiting for string…" else if(tuned)"IN TUNE" else if(cents<0)"FLAT · Raise pitch" else "SHARP · Lower pitch",
                    color=indicator,fontWeight=FontWeight.Bold)
                GriddedPitchMeter(cents,indicator)
                Text(if(cents==null)"— cents" else "${if(cents>0)"+" else ""}${cents.roundToInt()} cents from ${Tunings.noteWithOctave(selected)}",
                    style=MaterialTheme.typography.bodyLarge,color=indicator)
                Text("Target ${Tunings.noteWithOctave(selected)} · ${"%.1f".format(targetHz)} Hz",
                    style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // Cool ebony-charcoal fretboard contrasts with the blue tuning tray without brown tones.
        Column(Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(TunerNeckFrame)
            .border(BorderStroke(1.dp,TunerNeckBorder),RoundedCornerShape(20.dp))
            .padding(10.dp),verticalArrangement=Arrangement.spacedBy(9.dp)){
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.CenterVertically){
                Text("GUITAR NECK",style=MaterialTheme.typography.labelMedium,color=TunerAccent,letterSpacing=2.sp)
                Text("6 LOW  →  HIGH 1",style=MaterialTheme.typography.labelSmall,
                    color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
            GuitarNeck(tuning,stringIndex){stringIndex=it}
        }

        // A distinct steel-blue tuning tray keeps presets visually grouped.
        Column(Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(TunerTuningPanel)
            .border(BorderStroke(1.dp,TunerTuningBorder),RoundedCornerShape(18.dp))
            .padding(14.dp),verticalArrangement=Arrangement.spacedBy(10.dp)){
            Text("TUNING",style=MaterialTheme.typography.labelMedium,color=TunerAccent,letterSpacing=2.sp)
            PresetTile(Tunings.presets[0], tuning.id==Tunings.presets[0].id,Modifier.fillMaxWidth()) {
                tuning=Tunings.presets[0];stringIndex=0
            }
            Tunings.presets.drop(1).chunked(2).forEach { rowPresets ->
                Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(9.dp)){
                    rowPresets.forEach { preset ->
                        PresetTile(preset,tuning.id==preset.id,Modifier.weight(1f)){
                            tuning=preset;stringIndex=0
                        }
                    }
                    if(rowPresets.size==1) Spacer(Modifier.weight(1f))
                }
            }
        }
        Button(onClick={
            if(running){mic.stop();running=false;frequency=null;status="Microphone stopped."}
            else if(permissionGranted)startMic()
            else permission.launch(Manifest.permission.RECORD_AUDIO)
        },modifier=Modifier.fillMaxWidth().height(54.dp)){
            Text(if(running)"Stop microphone" else "Start microphone")
        }
        Text(status,modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Center,
            style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
    }
}


@Composable
private fun PresetTile(preset:GuitarTuning,selected:Boolean,
    modifier:Modifier=Modifier,onClick:()->Unit){
    Surface(onClick=onClick, modifier=modifier.height(46.dp),
        shape=RoundedCornerShape(12.dp),
        color=if(selected)TunerSelectedPreset else TunerPresetTile,
        border=BorderStroke(if(selected)2.5.dp else 1.dp,if(selected)TunerGold else TunerTuningBorder)){
        Box(contentAlignment=Alignment.Center){
            Text(preset.name,style=MaterialTheme.typography.labelLarge,
                fontWeight=if(selected)FontWeight.Bold else FontWeight.Medium,
                color=if(selected)TunerGold else Color(0xFFE4EDF2))
        }
    }
}

@Composable
private fun GuitarNeck(tuning:GuitarTuning,selected:Int,onSelect:(Int)->Unit){
    Box(Modifier.fillMaxWidth().height(195.dp)
        .clip(RoundedCornerShape(18.dp)).background(TunerNeck)){
        Canvas(Modifier.fillMaxSize()){
            val cell=size.width/6f
            drawRect(TunerSelection.copy(alpha=0.50f),topLeft=Offset(selected*cell,0f),
                size=Size(cell,size.height))
            for(fret in 1..5){
                val y=size.height*fret/6f
                drawLine(Color(0xFFACBCCB).copy(alpha=0.46f),
                    Offset(0f,y),Offset(size.width,y),strokeWidth=1.5f)
            }
            for(i in 0..5){
                val x=cell*(i+0.5f)
                drawLine(if(i==selected)TunerAccent else Color(0xFFDAE5EB),
                    Offset(x,0f),Offset(x,size.height),
                    strokeWidth=(3.4f-i*0.34f).dp.toPx(),cap=StrokeCap.Round)
            }
        }
        Row(Modifier.fillMaxSize()){
            tuning.midiNotes.forEachIndexed { index,midi ->
                val active=index==selected
                Box(Modifier.weight(1f).fillMaxHeight()
                    .semantics {contentDescription="String "+(6-index)+", "+Tunings.noteWithOctave(midi)+(if(active)", selected" else "")}
                    .clickable{onSelect(index)},
                    contentAlignment=Alignment.Center){
                    Column(horizontalAlignment=Alignment.CenterHorizontally,
                        verticalArrangement=Arrangement.spacedBy(7.dp)){
                        Text((6-index).toString(),fontSize=11.sp,fontWeight=FontWeight.Bold,
                            color=if(active)Color.White else Color(0xFFE3E6E3),
                            modifier=Modifier.background(TunerNeck.copy(alpha=0.9f),
                                RoundedCornerShape(5.dp))
                                .padding(horizontal=6.dp,vertical=2.dp))
                        Surface(shape=RoundedCornerShape(11.dp),
                            color=if(active)TunerSelectedPreset else Color(0xFF34434F),
                            border=BorderStroke(if(active)2.5.dp else 1.dp,
                                if(active)TunerGold else TunerOutline)){
                            Column(Modifier.padding(horizontal=5.dp,vertical=7.dp),
                                horizontalAlignment=Alignment.CenterHorizontally){
                                Text(Tunings.noteName(midi),fontSize=20.sp,
                                    fontWeight=FontWeight.Black,maxLines=1,
                                    color=if(active)TunerGold else Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GriddedPitchMeter(cents:Double?,color:Color){
    Column(Modifier.fillMaxWidth()){
        Canvas(Modifier.fillMaxWidth().height(131.dp)){
            val pad=14.dp.toPx()
            val usable=size.width-2*pad
            val midY=size.height*0.52f
            val grid=Color(0xFF91ADBC)
            for(i in 0..20){
                val x=pad+usable*i/20f
                drawLine(grid.copy(alpha=if(i%5==0)0.54f else 0.23f),
                    Offset(x,0f),Offset(x,size.height),
                    strokeWidth=if(i%5==0)1.25f else 0.70f)
            }
            for(j in 0..6){
                val y=size.height*j/6f
                drawLine(grid.copy(alpha=if(j==3)0.52f else 0.22f),
                    Offset(pad,y),Offset(size.width-pad,y),strokeWidth=1f)
            }
            drawLine(Color(0xFFBACBD4),Offset(pad,midY),
                Offset(size.width-pad,midY),strokeWidth=2f)
            drawLine(TunerGreen.copy(alpha=0.7f),
                Offset(size.width/2f,0f),Offset(size.width/2f,size.height),strokeWidth=2.5f)
            cents?.let{
                val x=pad+usable*((it.coerceIn(-50.0,50.0)+50.0)/100.0).toFloat()
                drawLine(color.copy(alpha=0.30f),Offset(x,0f),
                    Offset(x,size.height),strokeWidth=5f)
                val diamond=Path().apply{
                    moveTo(x,midY-13.dp.toPx())
                    lineTo(x+10.dp.toPx(),midY)
                    lineTo(x,midY+13.dp.toPx())
                    lineTo(x-10.dp.toPx(),midY)
                    close()
                }
                drawPath(diamond,color)
                drawCircle(TunerBackground,3.dp.toPx(),center=Offset(x,midY))
            }
        }
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){
            listOf("−50","−25","0","+25","+50").forEach{
                Text(it,style=MaterialTheme.typography.labelSmall,
                    color=if(it=="0")TunerGreen else MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
