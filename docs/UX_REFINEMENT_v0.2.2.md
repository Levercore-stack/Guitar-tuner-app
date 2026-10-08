# Guitar Tuner v0.2.2 — Pixel appearance and system-bar fit
Date: 2026-10-08

User on-device feedback from v0.2.1: six-string neck diagram is much better; keep string thickness progression, frets and tap targets. Current palette is too dark, title is hard to see and too close to top Android status icons. Neck letters have unexpected octave numbers below them. Launcher icon needs a distinct small guitar pick.

- Only remove octave **subscripts in the six neck tiles** (e.g. E2 -> E). Retain mathematical pitch octaves in detected/target pitch information and the accessibility descriptions, because E2 and E4 are physically different frequencies.
- Brighten overall graphite / slate-blue surfaces, panel outlines, grid and note text without loud colors; title explicitly white.
- Use Compose `WindowInsets.safeDrawing` to leave native Android status/navigation bar space and apply extra top margin and bottom content margin. Android MainActivity sets edge-to-edge layout with LIGHT icons off so system bar icons are light on dark blue. Do not substitute a fixed screen height: Pixel devices differ.
- Replace launcher graphic with adaptive vector icon: pale ivory guitar pick on muted deep blue, two understated string-stroke lines. Large distinct pick silhouette readable on small launcher icon.
- Keep the current guitar neck layout and presets; optionally brighten existing strings while keeping thick-to-thin and selected marker. Do not modify pitch detector, microphone/audio adapter, tuning preset mappings or permissions.
- Next release is versionName 0.2.2, versionCode 4, package com.levercore.guitartuner. Build unsigned release with existing GitHub CI, then sign privately with the **existing Guitar Tuner key**. Expected certificate SHA256 F3:29:47:E4:43:FB:9A:39:0F:83:47:58:6E:75:A8:5D:95:46:37:93:AE:45:CF:58:2D:67:74:09:9F:22:2E:6F.
- Deliver raw signed APK to the same Guitar Tuner project Google Drive folder and verify size/parent. User should install over v0.2.1 **without uninstall** to perform the first live signer-continuity test. CI pass does not prove in-place update.
