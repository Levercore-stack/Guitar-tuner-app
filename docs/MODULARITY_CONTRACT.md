# Guitar Tuner — versioned modularity and upgrade contract (v0.1)

Owner: LeverCore. Native Pixel Android initial substrate. All modules are replaceable IN PRINCIPLE; not every boundary is fully abstracted in the alpha.

| Module | Owns | May change without | Current interface / future seam |
|---|---|---|---|
| `domain/Tunings.kt` | String presets, target MIDI notes, cents/note conversion | Mic capture, UI shell | Pure model; add optional custom tuning persistence independently |
| `domain/PitchEngine.kt` | Pitch estimation from PCM float samples | OS mic permissions, visual meter, string list | `detect(samples, rate): PitchSample?` |
| `audio/PitchSource.kt` | Hardware capture contract | UI and signal algorithm | `start(onSample,onError)` / `stop()` |
| `audio/MicrophoneCapture.kt` | Android RECORD_AUDIO capture, PCM conversion, release | Color palette, preset choices | Implements `PitchSource`; DSP dependency is injected |
| `ui/TunerTheme.kt` | Dark gray, green/amber/red design tokens, shapes | Mic, DSP, presets | One Jetpack Compose theme |
| `ui/TunerScreen.kt` | Visible meter, string choices, permission and lifecycle UX | Tuning math/DSP | Currently creates mic source directly; next pass extract presentation state and composition root |
| `MainActivity.kt` | Android launcher/composition root | Any individual feature | Thin Activity |
| `.github/workflows/android-apk.yml` | Build substrate | Domain/pitch algorithms | Can replace Gradle/CI separately |

**What is still coupled and must not be oversold**: One-screen v0.1 does not have a menu registry; mic state is currently managed in `TunerScreen` rather than a standalone controller/ViewModel. Android OS replacement still requires new permission/lifecycle adapters and UI host. No file/data migrations exist yet because there is no persistent custom tuning data. Adding screens should create a declarative navigation registry before menus proliferate.

**Change discipline**: preserve stable tuning IDs and pitch meaning; add tests for pure pitch conversion/algorithm and manual tests for Pixel. Never claim a simulator test proves live guitar accuracy. No audio upload, network permission, third-party analytics, account, recording file or continuous mic capture in background. Stop mic on Activity background/exit. Production signing secrets should be kept outside public code and Drive.
