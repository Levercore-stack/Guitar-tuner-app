# Guitar Tuner — current project status
Updated 2026-10-08. ACTIVE: native Android v0.1 built; physical Google Pixel / actual guitar test remains outstanding.

## Identity and authority
- Official displayed app name: Guitar Tuner. User chooses name, visuals, features, privacy, and future maintenance.
- Canonical source: https://github.com/Levercore-stack/Guitar-tuner-app (main branch; check HEAD live).
- Native Kotlin and Jetpack Compose; standalone personal Pixel app. No ads, subscriptions, accounts, INTERNET permission, analytics, or network-based pitch processing.
- The source is now directly readable in app/, docs/, and root Gradle files. The original source backup remains in its existing Guitar Tuner Drive project folder, separately from the canonical GitHub source.
- Locked workflow v1.0: https://drive.google.com/file/d/1cn2002chdPbH8tQVSbjABAwDtIWGtx_u/view
- Transfer experiment: https://docs.google.com/document/d/13EdsFBaSlfZ9SfIqOBxz6QrF-RgbNQIX3raIjyQYXnc/edit

## Build evidence
- First successful GitHub Actions compilation, source import and corrected user-visible branding: https://github.com/Levercore-stack/Guitar-tuner-app/actions/runs/37850537264
- Original action SHA ff2581e571e8702a803b34d209dc8dd78f1bc96a; source was then committed to main by GitHub Actions. Read current HEAD, not just this historical SHA.
- GitHub Actions APK artifact 11580704554 named guitar-tuner-debug-apk (not expired when retrieved).
- Verified debug APK sha256 d0edbaab807037d428a1a35fbcd587b18a1c12940a4dce9af814ae59adb1ef84, size 9478721 bytes. Compiled Android package archive checked for dex, manifest and resource table.
- Exact debug test APK delivered as ChatGPT attachment in the active build session.
- Permanent project Drive backup: https://drive.google.com/file/d/1jOhZw_3DTmbCbpS6jYuTOWNAkS6qCKpb/view . This is a GitHub Actions artifact ZIP containing `app-debug.apk`, not a standalone APK file. The exact standalone APK was also delivered as a direct ChatGPT attachment.
- Readable GitHub source imported successfully. A redundant old CI workflow has been removed to prevent duplicate misleading failures.

## App scope and modularity
- Six-string standard E2 A2 D3 G3 B3 E4 default; select strings; Drop D, Open E, Open D, Open G, DADGAD preset support.
- Flat=red, sharp=amber/orange, in-tune=green. Dark graphite palette, large tuning meter and visible cents. Appearance and features are user-controlled; avoid ad/subscription dependencies.
- domain/PitchEngine.kt DSP algorithm, domain/Tunings.kt presets, audio/PitchSource.kt abstraction, audio/MicrophoneCapture.kt Android AudioRecord, ui/TunerScreen.kt user flow, ui/TunerTheme.kt style, MainActivity.kt native entry.
- Pure Kotlin synthetic frequencies were preliminarily accurate (within ~0.3 cents) for seven tones; synthetic silence/noise rejection passed. Harmonic-heavy low-E2 example returned roughly one octave high: must test actual guitar and revise DSP only if real evidence supports it.

## Physical acceptance pending
- Install APK on user's Pixel. On real guitar test low E2, A2, D3, G3, B3, E4, Drop D D2, both out-of-tune directions, room noise, clipped/sustained notes and silent input.
- Verify microphone permission grant/denial, stop/release mic on app background, screen lock behavior, legibility and tuning latency/stability.
- Verify app branding visible in launcher and inside UI; no ads, account or payment.
- Debug APK uses the CI test signer; stable secure signing for future in-place updates is a separate unresolved gate. Do not uninstall apps with important data without safe recovery.
- Do not claim actual Pixel tuner acceptance until user reports it. No third-party DSP dependency copied so far.

## Single next action
Install the validated v0.1 test APK on Pixel and report whether the mic permission works and the low E/A/D/G/B/high E strings display plausible, stable pitch and cents. Record observations in project status and the workflow process-trial file; resolve octave errors in a bounded DSP pass after hardware evidence.
