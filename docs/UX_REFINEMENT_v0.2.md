# Guitar Tuner v0.2 — Pixel UX refinement

Date: 2026-10-08
Source authority: GitHub main. Process authority: locked Android Pixel/GitHub workflow v1.0.

User-reported v0.1 Pixel acceptance: installs and opens, microphone request/grant and activation work, strings make note/pitch reading respond, tuning presets work. Pitch accuracy and octave errors not comprehensively verified.

v0.2 changes: guitar-neck diagram with vertical strings 6 (left, low) through 1 (right, high), large string-center note labels, string selection per column and labels dynamically mapped to active preset. Standard preset first as full-width top choice, others arranged in a two-column grid. Realtime pitch meter gains subtle -50 through +50 cents graph grid, center guide and live colored diamond marker. Typography moves to bold italic industrial-rock edge with a restrained graphite/steel-blue palette. Remove promotional claims in app heading. Keep no ads/accounts/network/subscriptions as product contract.

Do not modify domain/PitchEngine.kt, domain/Tunings.kt, audio/PitchSource.kt or audio/MicrophoneCapture.kt. Existing microphone foreground stop and grant flow preserved. This is a UI-only refinement.

Validation gate: Android CI build -> upload direct standalone APK to project Drive -> user's Pixel UI, sound and interaction testing. Debug signing may not match prior build: an install-over error means older test app must be uninstalled; no retained user settings presently exist, but do not imply safe upgrades.
