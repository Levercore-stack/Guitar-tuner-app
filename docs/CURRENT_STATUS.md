# Guitar Tuner — current project status

Updated 2026-10-08. **ACTIVE / hardware-validation prototype**, NOT an accepted Android build or finished app.

**Source stage:** 17-file native Kotlin/Jetpack Compose starter in `Lever_Tuner_Android_Starter_v0.1.zip` in this project Drive folder; not uploaded to GitHub because the user has not created a dedicated tuner repository yet. Do not use the `workout-logger` repository for this unrelated app.

**User's exact UX**: standard tuning six strings E–A–D–G–B–E on opening; select string; top note/cents meter with flat RED, sharp AMBER/ORANGE, centered IN-TUNE GREEN; visible movement toward center; drop D, open E, open D, open G, DADGAD presets; gray/graphite dark background; microphone permission explicit; no ads, sign-in, cloud or unnecessary Internet.

**Implementation in ZIP**: `domain/Tunings.kt` six accurate presets; `domain/PitchEngine.kt` YIN-style frequency detector; `audio/PitchSource.kt` interchangeable audio contract; `audio/MicrophoneCapture.kt` Android microphone adapter; `ui/TunerScreen.kt` single-screen tuner, selectable strings and color-coded cents meter; `ui/TunerTheme.kt` interchangeable skin; thin `MainActivity.kt`; GitHub Actions APK workflow. Mic stops on UI stop and on Activity leaving foreground. Alpha allows selected string, not yet custom tunings or calibrated live guitar QA.

**Verified**: ZIP integrity; JVM Kotlin smoke checks for six exact preset arrays, seven synthetic fundamental tones (~D2 through E4), silence gate and cents color direction. **NOT verified**: Android SDK/Gradle full APK compile, Pixel installation, physical microphone/permission denial, guitar pitch stability/noise/false octave, real string tuning, lifecycle on screen-lock. Never confuse these.

**Next action**: user creates an empty dedicated GitHub repository such as `Levercore-stack/guitar-tuner`. Upload source files preserving paths, run Android APK workflow, resolve compile errors. Then deliver direct test APK via Drive and conduct physical Pixel hardware tests on all six strings. Lock app signer securely before non-disposable data or broader sharing; do NOT reuse public workout-app prototype signing key. Use `docs/MODULARITY_CONTRACT.md` when revising individual components.

**Prior locked workflow:** https://drive.google.com/file/d/1cn2002chdPbH8tQVSbjABAwDtIWGtx_u/view. It is a candidate playbook, not an installed skill. First cross-app trial occurs when tuner reaches real APK and Pixel test.
