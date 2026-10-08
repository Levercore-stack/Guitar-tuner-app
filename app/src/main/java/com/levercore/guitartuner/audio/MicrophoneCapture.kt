package com.levercore.guitartuner.audio

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.levercore.guitartuner.domain.PitchEngine
import com.levercore.guitartuner.domain.PitchSample
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.thread

/** OS-only permission-backed mic adapter. No file recording and no network path. */
class MicrophoneCapture(private val detector: PitchEngine = PitchEngine()): PitchSource {
    private val running=AtomicBoolean(false)
    private var recorder: AudioRecord?=null
    private var worker: Thread?=null
    private val sampleRate=44100

    @SuppressLint("MissingPermission") // Called only after explicit runtime permission approval.
    @Synchronized override fun start(onSample:(PitchSample?)->Unit, onError:(String)->Unit):Boolean {
        if(running.get()) return true
        val minSize=AudioRecord.getMinBufferSize(sampleRate,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT)
        if(minSize<=0){onError("Microphone sample rate unavailable.");return false}
        val record=try {
            AudioRecord(MediaRecorder.AudioSource.MIC,sampleRate,AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,maxOf(minSize,8192*2))
        }catch(e:Exception){onError("Microphone unavailable: "+(e.message?:"unknown error"));return false}
        if(record.state!=AudioRecord.STATE_INITIALIZED){record.release();onError("Could not initialize microphone.");return false}
        try{record.startRecording()}catch(e:Exception){record.release();onError("Microphone access failed.");return false}
        recorder=record
        running.set(true)
        worker=thread(name="lever-tuner-mic",isDaemon=true){
            val pcm=ShortArray(4096)
            val downsampled=FloatArray(1024)
            try{
                while(running.get()){
                    val n=record.read(pcm,0,pcm.size,AudioRecord.READ_BLOCKING)
                    if(n<0){if(running.get())onError("Microphone read error: $n");break}
                    if(n<4096)continue
                    for(i in downsampled.indices){
                        val j=i*4
                        downsampled[i]=(pcm[j].toFloat()+pcm[j+1]+pcm[j+2]+pcm[j+3])/(4*32768f)
                    }
                    onSample(detector.detect(downsampled, sampleRate/4))
                }
            }catch(e:Exception){if(running.get())onError("Microphone stopped: "+(e.message?:"unknown"))}
            finally{running.set(false)}
        }
        return true
    }
    @Synchronized override fun stop(){
        running.set(false)
        val record=recorder
        recorder=null
        worker=null
        if(record!=null){
            try{record.stop()}catch(_:Exception){}
            try{record.release()}catch(_:Exception){}
        }
    }
}
