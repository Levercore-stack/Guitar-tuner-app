# Guitar Tuner v0.2.5 — live-meter/neck adjacency and gold selected note
2026-10-08. Owner's real Pixel v0.2.4 feedback: UI, charcoal fretboard and gold-selected tuning buttons look excellent; wants only rearrangement of the existing three app boxes and matching selection styling on neck notes.

## Change scope and exact order
- Keep GUITAR TUNER title, the existing live detected-note/card with pitch grid and target at TOP (first working area).
- Reorder the existing GUITAR NECK panel to directly BELOW live tuner card, second in scroll order; tuning notes can be tapped and user can read live meter without needing to scroll between tuning preset tiles.
- Move the current steel-blue TUNING panel unchanged to BELOW GUITAR NECK, third in scroll order. Selecting a new preset still resets to string 6 as before.
- Reuse the exact selected TUNING tile treatment on selected GUITAR NECK note capsule: selected fill `TunerSelectedPreset` deep navy #233B4B; selected stroke `TunerGold` #F3D38C at 2.5dp, selected note text gold. Unselected note chips retain existing charcoal fill / outlined stroke / white note letters. The physical 6-line guitar neck strings, thick-to-thin ordering, fretboard geometry, string highlighting, touch semantics and note names are unchanged.
- Preserve original title, Android OS system insets, microphone start-stop placement/behavior, cents meter, pitch accuracy code and alternate preset mappings. Preserve user-approved adaptive/legacy pick launcher icon EXACTLY; no resource changes.
- Android `com.levercore.guitartuner` versionCode 7 (from 6), versionName 0.2.5. Only `TunerScreen.kt`, version gradle file, this doc changed.

## Delivery/acceptance
- Compile unsigned release via Github Actions; sign offline with previously retained unique Guitar Tuner private PKCS12. Matching expected signing cert SHA256 `F3:29:47:E4:43:FB:9A:39:0F:83:47:58:6E:75:A8:5D:95:46:37:93:AE:45:CF:58:2D:67:74:09:9F:22:2E:6F`, v2/v3 verification and APK integrity required. Never produce or deliver ephemeral debug-signer APK.
- Direct standalone signed APK in existing Google Drive Guitar Tuner folder. User installs OVER v0.2.4, no uninstall. Real physical Pixel approval remains separate.
