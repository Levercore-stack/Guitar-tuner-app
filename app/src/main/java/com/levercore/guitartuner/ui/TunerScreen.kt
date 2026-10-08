package com.levercore.guitartuner.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal=16.dp,vertical=18.dp),verticalArrangement=Arrangement.spacedBy(16.dp)){
        Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically){
            Column{Text("GUITAR TUNER",style=MaterialTheme.typography.titleLarge,fontWeight=FontWeight.Bold)
                Text("ON-DEVICE · NO ADS",style=MaterialTheme.typography.labelSmall,color=MaterialTheme.colorScheme.onSurfaceVariant)}
            Text(if(running)"● LISTENING" else "○ MIC OFF",color=if(running)TunerGreen else MaterialTheme.colorScheme.onSurfaceVariant,style=MaterialTheme.typography.labelSmall)
        }
        Card(Modifier.fillMaxWidth(),colors=CardDefaults.cardColors(containerColor=TunerPanel)){
            Column(Modifier.padding(18.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(8.dp)){
                Text("DETECTED NOTE",style=MaterialTheme.typography.labelMedium,color=MaterialTheme.colorScheme.onSurfaceVariant)
                Text(detected?.let{Tunings.noteWithOctave(Tunings.midiForFrequency(it))}?:"—",
                    style=MaterialTheme.typography.displayLarge,fontWeight=FontWeight.Bold,color=indicator)
                Text(if(cents==null)"Waiting for string…" else if(tuned)"IN TUNE" else if(cents<0)"FLAT · Raise pitch" else "SHARP · Lower pitch",
                    color=indicator,fontWeight=FontWeight.Bold)
                PitchMeter(cents,indicator)
                Text(if(cents==null)"— cents" else "${if(cents>0)"+" else ""}${cents.roundToInt()} cents from ${Tunings.noteWithOctave(selected)}",
                    style=MaterialTheme.typography.bodyLarge,color=indicator)
                Text("Target ${Tunings.noteWithOctave(selected)} · ${"%.1f".format(targetHz)} Hz",
                    style=MaterialTheme.typography.bodySmall,color=MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Text("TUNING PRESET",style=MaterialTheme.typography.labelMedium,color=MaterialTheme.colorScheme.onSurfaceVariant)
        Row(Modifier.horizontalScroll(rememberScrollState()),horizontalArrangement=Arrangement.spacedBy(6.dp)){
            Tunings.presets.forEach { preset ->
                FilterChip(selected=tuning.id==preset.id,onClick={tuning=preset;stringIndex=0},label={Text(preset.name)})
            }
        }
        Text("SELECT STRING · LOW TO HIGH",style=MaterialTheme.typography.labelMedium,color=MaterialTheme.colorScheme.onSurfaceVariant)
        Column(verticalArrangement=Arrangement.spacedBy(8.dp)){
            for(row in 0..1){
                Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(8.dp)){
                    for(col in 0..2){
                        val index=row*3+col
                        val midi=tuning.midiNotes[index]
                        val selectedString=stringIndex==index
                        OutlinedButton(onClick={stringIndex=index},modifier=Modifier.weight(1f).height(68.dp),
                            shape=RoundedCornerShape(16.dp),
                            colors=ButtonDefaults.outlinedButtonColors(containerColor=if(selectedString)Color(0xFF284A3D) else TunerPanel)) {
                            Column(horizontalAlignment=Alignment.CenterHorizontally){
                                Text(Tunings.noteName(midi),style=MaterialTheme.typography.titleLarge,
                                    fontWeight=FontWeight.Bold,color=if(selectedString)TunerGreen else Color.White)
                                Text("STRING ${6-index}",style=MaterialTheme.typography.labelSmall,
                                    color=MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
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
private fun PitchMeter(cents:Double?,color:Color){
    Canvas(Modifier.fillMaxWidth().height(92.dp)){
        val mid=size.width/2f
        val y=size.height*0.62f
        drawLine(Color(0xFF515965),start=androidx.compose.ui.geometry.Offset(14f,y),
            end=androidx.compose.ui.geometry.Offset(size.width-14f,y),strokeWidth=4f,cap=StrokeCap.Round)
        val width=size.width-28f
        for(i in -5..5){
            val x=mid+width*(i/10f)
            drawLine(if(i==0)TunerGreen else Color(0xFF6C727B),
                start=androidx.compose.ui.geometry.Offset(x,y-if(i==0)23f else 12f),
                end=androidx.compose.ui.geometry.Offset(x,y+if(i==0)23f else 12f),strokeWidth=if(i==0)5f else 2f)
        }
        cents?.let{
            val x=mid+width*(it.coerceIn(-50.0,50.0).toFloat()/100f)
            drawCircle(color,15f,center=androidx.compose.ui.geometry.Offset(x,y))
            drawCircle(Color(0xFF15171D),5f,center=androidx.compose.ui.geometry.Offset(x,y))
        }
    }
}
