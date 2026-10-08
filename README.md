# Guitar Tuner — native Google Pixel Android project

**Status: source-only early prototype, NOT YET COMPILED or installed on a phone.**

This is a dedicated Android application, not a website, browser shell, or a module inside My Gym Log. No ads, subscriptions, accounts, network permission, audio file recording, or cloud calls.

## First screen
- Standard tuning appears immediately as E–A–D–G–B–E from string 6 to 1.
- Six string choices and horizontally selectable Standard, Drop D, Open E, Open D, Open G, DADGAD presets.
- Large detected chromatic note and a centered needle-style cents meter. **Red = flat, amber/orange = sharp, green = within ±5 cents of chosen string.**
- Tap **Start microphone** to request Android `RECORD_AUDIO` at runtime. Mic releases on Stop, when leaving foreground, or on disposal.
- `PitchEngine` is a pure Kotlin YIN-style pitch estimate; `MicrophoneCapture` is the only audio/Android adapter. `Tunings` contains independent note/tuning definitions, and `TunerTheme` holds visual styling.

## Build
Create a **new empty GitHub repo for this tuner**; do not put files in `workout-logger`. Upload the source preserving paths. GitHub Actions builds a test APK on `main` with Java 17, Gradle 8.9, Android SDK 35. The workflow is untested for this project until the first build passes. It currently uses a temporary debug signer; choose secure, consistent signer before valuable distribution. Do **not** borrow the workout app's test signing key.

## QA gate
Compilation does **not** prove microphone hardware or tuning accuracy. On the user's real Google Pixel: open, grant/deny permission, test E2 A2 D3 G3 B3 E4 strings, compare with known tuner/reference, vary background noise, note drops and harmonics, check cents stability and time to settle; then Open D, Open G, Drop D, Open E, DADGAD; verify microphone indicator turns off on exit. Correct octave mistakes and smooth signal before shipping as accepted.

## Planned refinements
- Tuning presets should become editable/storable with custom tunings later (not implemented in this initial hardware-risk slice).
- Noise-floor detection, temporal smoothing, optional auto-string targeting, A4 calibration and custom note library. Current version is manual string selection.
- One reusable design token layer enables a full color/skin swap without touching mic or DSP. Later platform replacement would use new platform adapter implementing the same audio input contract; native Android is intentionally first.

## Modularity
- `domain/Tunings.kt`: notes, frequencies, string assignments and preset definitions
- `domain/PitchEngine.kt`: no Android imports, replaceable DSP algorithm
- `audio/PitchSource.kt`: platform interface, and `audio/MicrophoneCapture.kt`: Android microphone implementation
- `ui/TunerTheme.kt`: replaceable color and shape tokens
- `ui/TunerScreen.kt`: mic permission, lifecycle, string selector and meter; `MainActivity.kt`: thin Android launcher. A future menu registry can be added if we expand beyond one screen.

## Safety and signing
The audio is processed on-device in memory. The manifest requests only `RECORD_AUDIO` and does **not** request `INTERNET`. App shouldn't ever assume mic permission and must visibly show listening status. New app package `com.levercore.guitartuner` is independent from workout app's package. No production release signing keys in repo or Drive.
