package com.levercore.guitartuner.domain

import kotlin.math.log2
import kotlin.math.pow

/** MIDI note numbers run low-to-high from string 6 to string 1. */
data class GuitarTuning(val id: String, val name: String, val midiNotes: List<Int>) {
    init { require(midiNotes.size == 6) }
}

object Tunings {
    val presets = listOf(
        GuitarTuning("standard", "Standard", listOf(40,45,50,55,59,64)), // E A D G B E
        GuitarTuning("drop-d", "Drop D", listOf(38,45,50,55,59,64)),
        GuitarTuning("open-e", "Open E", listOf(40,47,52,56,59,64)),
        GuitarTuning("open-d", "Open D", listOf(38,45,50,54,57,62)),
        GuitarTuning("open-g", "Open G", listOf(38,43,50,55,59,62)),
        GuitarTuning("dadgad", "DADGAD", listOf(38,45,50,55,57,62))
    )
    private val names = listOf("C","C♯","D","D♯","E","F","F♯","G","G♯","A","A♯","B")
    fun noteName(midi: Int) = names[Math.floorMod(midi,12)]
    fun noteWithOctave(midi: Int) = noteName(midi) + (midi/12-1)
    fun frequency(midi: Int, referenceA4: Double = 440.0): Double = referenceA4 * 2.0.pow((midi-69)/12.0)
    fun midiForFrequency(freq: Double, referenceA4: Double = 440.0): Int =
        (69.0 + 12.0*log2(freq/referenceA4)).toIntRound()
    private fun Double.toIntRound() = kotlin.math.round(this).toInt()
    fun centsFromTarget(freq: Double, midi: Int, referenceA4: Double = 440.0): Double =
        1200.0 * log2(freq / frequency(midi,referenceA4))
}
