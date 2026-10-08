package com.levercore.guitartuner.audio

import com.levercore.guitartuner.domain.PitchSample

/** Hardware boundary: replace Android mic without altering note detection, tunings or UI layout. */
interface PitchSource {
    fun start(onSample: (PitchSample?) -> Unit, onError: (String) -> Unit): Boolean
    fun stop()
}
