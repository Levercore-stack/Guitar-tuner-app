# Guitar Tuner v0.2.4 — Gold active preset / charcoal ebony neck
Date: 2026-10-08

**Owner's real Pixel feedback on v0.2.3:** The pick launcher icon is now perfect and MUST NOT change; overall color separation is working well. Two remaining visual issues: selected tuning preset blends into blue tray and warm brown fretboard is unattractive.

## Bounded visual adjustments
- The selected tuning preset uses a 2.5dp warm gold #F3D38C outline and gold text, plus darker selected blue #233B4B. Unselected tiles retain thin blue outlines. No click handling or tuning mappings change.
- The fretboard becomes cool ebony charcoal #222B34, with a cool blue-charcoal #30404D frame, steel-blue #7893A6 frame outline, and pale steel-gray fret lines #ACBCCB. No brown hues.
- Keep the same six string widths and positions, touch interactions, note-only labels, tuning tray design, bright title, safe status/navigation insets, all audio logic and the existing pick icon unchanged.
- Native Android package remains `com.levercore.guitartuner`; versionCode 6 and versionName 0.2.4.
- Build unsigned via GitHub Actions, privately sign with the SAME pre-existing Guitar Tuner PKCS12. Expected certificate SHA256 `F3:29:47:E4:43:FB:9A:39:0F:83:47:58:6E:75:A8:5D:95:46:37:93:AE:45:CF:58:2D:67:74:09:9F:22:2E:6F`.
- Verify signing scheme, APK integrity and SHA256; place directly in same Guitar Tuner Google Drive folder. The owner will install over v0.2.3 without uninstall. Actual phone visual acceptance remains pending until user reports.
