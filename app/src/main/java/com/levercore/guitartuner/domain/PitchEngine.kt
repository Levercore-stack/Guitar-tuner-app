package com.levercore.guitartuner.domain

import kotlin.math.sqrt

/** Pure algorithm, independent of Android mic and UI. Returns null when no reliable fundamental. */
data class PitchSample(val frequencyHz: Double, val confidence: Double)

class PitchEngine {
    /** YIN-style squared difference / cumulative-mean normalization / parabolic refinement. */
    fun detect(samples: FloatArray, sampleRate: Int): PitchSample? {
        if (samples.size < 768 || sampleRate <= 0) return null
        val n=samples.size
        var mean=0.0
        for(v in samples) mean+=v
        mean/=n
        var energy=0.0
        for(v in samples){val q=v-mean;energy+=q*q}
        val rms=sqrt(energy/n)
        if(rms<0.009) return null
        val minTau=(sampleRate/430).coerceAtLeast(2)
        val maxTau=(sampleRate/62).coerceAtMost(n/3)
        if(maxTau<=minTau) return null
        val diff=DoubleArray(maxTau+1)
        val window=(n-maxTau).coerceAtMost(1100)
        for(tau in 1..maxTau){
            var sum=0.0
            var i=0
            while(i<window){
                val d=samples[i].toDouble()-samples[i+tau].toDouble()
                sum+=d*d
                i++
            }
            diff[tau]=sum
        }
        val cmnd=DoubleArray(maxTau+1){1.0}
        var cumulative=0.0
        for(tau in 1..maxTau){
            cumulative+=diff[tau]
            cmnd[tau]=if(cumulative>0)diff[tau]*tau/cumulative else 1.0
        }
        // Find first below-threshold local minimum, not the strongest overtone.
        var choice=-1
        for(tau in minTau until maxTau){
            if(cmnd[tau]<0.18){
                var best=tau
                while(best+1<=maxTau && cmnd[best+1]<cmnd[best])best++
                choice=best
                break
            }
        }
        if(choice<0) return null
        val tau=choice.toDouble()
        val correction=if(choice>minTau && choice<maxTau){
            val a=cmnd[choice-1];val b=cmnd[choice];val c=cmnd[choice+1]
            val denom=a-2*b+c
            if(kotlin.math.abs(denom)>1e-10) (0.5*(a-c)/denom).coerceIn(-0.5,0.5) else 0.0
        }else 0.0
        val hz=sampleRate/(tau+correction)
        val confidence=(1.0-cmnd[choice]).coerceIn(0.0,1.0)
        return if(hz in 60.0..440.0 && confidence>=0.60) PitchSample(hz,confidence) else null
    }
}
