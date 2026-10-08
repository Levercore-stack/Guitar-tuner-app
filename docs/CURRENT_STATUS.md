# Guitar Tuner — current project status
Updated 2026-10-08. Current native stable-signed v0.2.4 (versionCode 6), gold selected preset + cool ebony neck. v0.2.3 Pixel feedback: icon and panel grouping accepted, colors needed final refinement. v0.2.4 hardware visual acceptance pending.

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
On Pixel, download the native v0.2.4 signed APK from https://drive.google.com/file/d/1z_R5Z7e_HrzaFh3k-RfUr_VNLkfji5OW/view and install OVER existing v0.2.3 without uninstall. Check gold selected tuning border/text and dark charcoal ebony neck; confirm accepted launcher icon and working mic unchanged.

## Guitar Tuner v0.2 — successful UX build (2026-10-08)
- User's v0.1 real-Pixel check: installed/opened, microphone permission and start passed, detected pitch moved responsively when strings were played, preset changes worked. This is **basic on-device functional acceptance**, not a quantified cents/octave/hardware-permission regression suite.
- User requested guitar-neck diagram: six vertical strings left-to-right 6 low to 1 high with target note labels centered directly on the strings; tapping each string selects the reference. All presets map to their own correct note set, including Open E.
- Standard tuning is promoted to a full-width top preset; remaining five tuning choices form a two-column grid. The cents display uses subtle graph-paper lines and a moving diamond marker on a centered -50 to +50 scale.
- Restrained industrial/futuristic/rock styling, graphite/slate-blue with gentle accents and angled title. The app no longer displays a NO ADS promotional line; business/privacy promise remains unchanged.
- Only UI/TunerScreen.kt, UI/TunerTheme.kt and Android version fields changed; DSP and microphone capture, permission and lifecycle behavior are untouched.
- GitHub source change https://github.com/Levercore-stack/Guitar-tuner-app/commit/c4513c12b4369e85caee2b3ab2c319bfe0a68baf
- CI https://github.com/Levercore-stack/Guitar-tuner-app/actions/runs/37852027267 succeeded; v0.2 artifact ID 11582141697.
- Verified standalone v0.2 app-debug.apk: 9,495,101 bytes; SHA-256 b5fe71971a05ed2dc23813de0edc69bcf6a08fa02f6ebe5e742ec66fbfc3dada, Android archive and artifact ZIP integrity passed.
- **Direct standalone APK on project Drive:** https://drive.google.com/file/d/1GOZPAXh1Ik1RunDYIygEPK5Qk4tv6qax/view . Readback confirmed file MIME, size and project folder parent. No need to unzip GitHub Actions artifact to install.
- Current functional acceptance gate: user install v0.2 on Pixel and assess string layout, note labels on all tunings, mobile readability and dynamic graph-paper cents marker. v0.2 physical device tests NOT YET REPORTED.
- Signed debug APKs may use different signing identities across GitHub runners. If Android refuses a normal install-over upgrade, user must be aware that uninstalling the earlier test copy clears app-local state. Do not claim seamless upgrading or production signing.
- Process trial: https://docs.google.com/document/d/13EdsFBaSlfZ9SfIqOBxz6QrF-RgbNQIX3raIjyQYXnc/edit . Baseline Android workflow v1.0 remains locked and unchanged.

## Earlier signing gap (resolved for v0.2.1 signing, update compatibility still requires a later-device test)
The next APK must use a new Guitar Tuner-only persistent signing certificate. Keep private key material out of the public repository; store it in protected GitHub Actions secrets, then verify two successive APKs have matching certificate fingerprints. The older v0.2 test build was signed by a temporary runner identity, so the first stable-signer install requires one user-approved clean install. The existing v0.2 APK remains saved directly in the project Drive folder.

## Current latest signed personal Android test release — v0.2.1
- User authorized a single clean uninstall/reinstall to transition away from runner-generated ephemeral debug signing; user does NOT need another GitHub repository or to generate keys.
- Canonical private signer: new per-app PKCS12 certificate alias `guitar-tuner-prototype-v1`, SHA256 fingerprint `F3:29:47:E4:43:FB:9A:39:0F:83:47:58:6E:75:A8:5D:95:46:37:93:AE:45:CF:58:2D:67:74:09:9F:22:2E:6F`. Private key and private passphrase were retained separately in personal ChatGPT Library `/Lever core/Guitar Tuner Private Signing/`. Do not copy them to the GitHub repository or app project Drive folder. Recommend separate encrypted private backup.
- GitHub `app/build.gradle.kts`: `versionCode=3`, `versionName=0.2.1`. Android package identity unchanged `com.levercore.guitartuner`.
- Build run `37854229117` completed successfully; https://github.com/Levercore-stack/Guitar-tuner-app/actions/runs/37854229117 . CI compiled an unsigned release and published it along with an Android signing utility as private-offline signing inputs. GitHub public repository **does not store private signing material**.
- Private local signing with `apksigner` succeeded. `apksigner verify --verbose --print-certs` passed, with v2 and v3 APK signature schemes enabled and certificate SHA256 matching the canonical per-app fingerprint. ZIP integrity and required Android manifest/DEX/resource files checked.
- **SIGNED Android APK:** `Guitar_Tuner_v0.2.1_Stable_Signed.apk`, **6,506,907 bytes**, SHA256 `e506f873ec7dcb35821387f50f96bb8ab1409f442efbabb37b1fc2c3abe5bd7c`.
- **Direct Google Drive link for phone:** https://drive.google.com/file/d/1aswt7HV2RHxJSMswykY47U1ef0lU39Gi/view . Readback confirmed correct name, `application/vnd.android.package-archive` MIME type, exact size and canonical Guitar Tuner Drive folder parent `1XB9aYuhxyCvNLGWOP_AYEacFuxmi1zEw`. User downloads it directly to Pixel; no manual upload needed.
- Migration note: prior v0.1/v0.2 debug APKs were signed under different ephemeral identities. The newly signed v0.2.1 APK cannot install over them using Android's standard update path. User consented to uninstalling prior disposable prototype once; warn before data loss. Later builds **must reuse same private P12 certificate** with strictly increasing `versionCode` to permit in-place updates.
- Future build recipe: `docs/PRIVATE_SIGNING_PROCESS_v0.2.1.md` and `scripts/sign_private_release.sh`. Signing happens outside public GitHub; the private key must be kept recoverable.
- **Acceptance pending:** user installs v0.2.1 on Pixel and verifies guitar neck UI, pitch meter, permission/tunings; a later signed v0.2.2+ over-the-top install must be tested before calling update-path proof fully accepted.

## Latest Guitar Tuner release — v0.2.2 signed (2026-10-08)
- User Pixel feedback from the first stable-signed app: neck is better; keep thick-to-thin strings, frets and presets. Octave numbers below neck notes are unwanted. Interface is too dark, header nearly invisible/under status bar, and old launcher icon unappealing.
- Changed **only presentation and launcher visuals**, Android system insets, version fields: remove mini octave numbers below six neck note labels (the note names alone remain); keep pitch math/target octave in tuning meter and accessibility descriptions; brighten backdrop, panels, grid and string strokes; explicitly white guitar tuner header; reserve `WindowInsets.safeDrawing`, add extra margin below Android status icons/above gesture bar, make OS status/nav icons light. Minimalist ivory pick on blue as adaptive/legacy launcher icon.
- Kotlin pitch detection, microphone adapter, existing user preset mappings and string positions **unchanged**.
- Visual source commit: https://github.com/Levercore-stack/Guitar-tuner-app/commit/18d7a4dcdea79b3820854d4ee6d17f4b66452fb3 .
- GitHub Actions v0.2.2 run `37855516302` **COMPLETED SUCCESS**: https://github.com/Levercore-stack/Guitar-tuner-app/actions/runs/37855516302 . Unsigned release artifact `11584146396` checked before signing.
- Exact v0.2.2 standalone signed APK: `Guitar_Tuner_v0.2.2_Stable_Signed.apk`, **6,511,115 bytes**, SHA256 `3f011c45fddf0f27ebad78081631e34b21ee5212b235f887dd7ead9c748668ff`. APK verified with Android `apksigner` v2+v3 signatures, ZIP integrity, AndroidManifest.xml, classes.dex and resources. Same signer SHA256 `F3:29:47:E4:43:FB:9A:39:0F:83:47:58:6E:75:A8:5D:95:46:37:93:AE:45:CF:58:2D:67:74:09:9F:22:2E:6F` as signed v0.2.1. Used **same private P12 file** from personal protected Library/local workspace; no replacement key created.
- **Raw APK for direct Pixel download on existing Guitar Tuner project Google Drive:** https://drive.google.com/file/d/1U-6g_VhTekFB_JHnC3tJcNHKscV9mfSQ/view . Drive metadata readback confirmed native Android APK MIME, exact 6511115 bytes and project parent `1XB9aYuhxyCvNLGWOP_AYEacFuxmi1zEw`. User needs no intermediary download/re-upload or ZIP extraction.
- One-off signing-script defect discovered in this pass: `--ks-pass file` and `--key-pass file` consumed the same one-line password file twice, producing EOF; corrected script to use **PKCS12 keystore password once**, after which the APK signing and certificate verification passed. Fixed in GitHub commit `1ab7fcb270af5ee5bd21e0e2d73f9eb6799da345`.
- **Hardware acceptance still pending**: user should install v0.2.2 on top of stable-signed v0.2.1 without uninstall, test updated home icon, note-only neck labels, brightness, top/bottom inset, microphone and state retention. Local APK cryptography verification ≠ actual Pixel over-install test; do not mark same-signer update fully accepted until user reports.
- Reusable Android locked baseline v1.0 stays unchanged; process-trial doc holds v0.2.2 user feedback and signed update evidence.

## Guitar Tuner v0.2.3 — grouped panels and smaller circular pick (2026-10-08)
- **Real Pixel update gate now passed for v0.2.1→v0.2.2:** user explicitly reported v0.2.2 installed over existing v0.2.1 without uninstall. User said overall brightened presentation looks much better. This is successful real-device update compatibility for these two signed prototype APKs; unrelated detailed mic/pitch accuracy gates are still open.
- User requested two last visual refinements: guitar neck/strings and TUNING choices should no longer visually blend into overall background, while preserving the successful screen, existing guitar-neck thickness/order/note labels and scrolling/insets. Existing icon resembled a turnip, so make the guitar pick smaller and broad-shouldered, completely inside a **darker circle** and within round Pixel adaptive launcher safe zone.
- Source UI-only commit https://github.com/Levercore-stack/Guitar-tuner-app/commit/aea67d606670feed5cb4a77eee1049f7b7fe0cc2 . New UI named palette colors `TunerTuningPanel`, `TunerTuningBorder`, `TunerPresetTile`, `TunerNeckFrame`, `TunerNeckBorder`; existing choice tiles wrapped in blue steel section while existing guitar-neck drawing placed in warmer stone/slate framed section. Launcher adaptive/legacy XML resources redrawn to pale ivory smaller triangular pick in dark blue circular medallion with neutral rim. No pitch-domain/audio/preset changes; package `com.levercore.guitartuner` unchanged.
- App `versionCode=5`, `versionName=0.2.3`. GitHub Actions run **37857433531 COMPLETED SUCCESS**: https://github.com/Levercore-stack/Guitar-tuner-app/actions/runs/37857433531 . Unsigned release artifact `11585205173` extracted and tested; standard Android apksigner signed it outside the public repository.
- Reused EXACT original protected Guitar Tuner signing key, not a replacement; signing certificate SHA256 **`f32947e443fb9a390f8347586e75a85d95463793ae45cf582d6774099f222e6f`** verified on both previous v0.2.2 and new v0.2.3. APK v2/v3 signature verification PASS, ZIP integrity passed and standard dex/manifest/resources present.
- Raw native standalone `Guitar_Tuner_v0.2.3_Stable_Signed.apk`: **6,511,115 bytes**, SHA256 **`a6b1ecd2448626f67886f63bc3fa446d808ddb45e9a7a699231b239a486d9ca9`**. **Direct Google Drive for Pixel** https://drive.google.com/file/d/1gVmo12mTbwtsqYaWlXnxlTwJnyuIpRBq/view . Drive metadata readback confirmed exact name, size, `application/vnd.android.package-archive` MIME and correct existing project folder ID `1XB9aYuhxyCvNLGWOP_AYEacFuxmi1zEw`. No ZIP extraction or manual upload required.
- User acceptance pending for v0.2.3 icon and section contrast. User should install OVER v0.2.2 without uninstall, visually inspect and report. Previous v0.2.2 installed over v0.2.1 on phone **was** reported successful and should no longer be labeled merely pending in current summary.

## Guitar Tuner v0.2.4 — gold preset selection / ebony fretboard (2026-10-08)
- **User's real Pixel feedback on installed v0.2.3:** overall appearance, separate guitar-neck/tuning containers and especially the new ivory pick launcher icon look much better. User explicitly calls the icon PERFECT and it must not change. Remaining visual defects: selected tuning tile blue border/fill disappears into blue panel and warm/brown fretboard looks ugly. Requested gold or green accent selected tile, cool dark gray/ebony fretboard.
- Only `ui/TunerScreen.kt`, `ui/TunerTheme.kt` and `app/build.gradle.kts` modified, plus `docs/UX_REFINEMENT_v0.2.4.md`. Selected preset now visibly gold #F3D38C 2.5dp stroke and gold text, dark selected tile #233B4B. Nonselected presets remain quieter steel blue. Neck is ebony charcoal #222B34, cool blue-charcoal framed #30404D with muted steel border #7893A6, cool gray-blue frets #ACBCCB. No brown hues.
- **Launcher icon source files UNCHANGED**, by explicit user acceptance. Six-string geometry, string-note selection, actual notes and presets, pitch detector, mic capture and permission/lifecycle, Android system insets and overall branding unchanged.
- Source commit https://github.com/Levercore-stack/Guitar-tuner-app/commit/c64f1908d924f9a4ffb6a8c1f4b7b9812ccf9ffe . Android package ID remains `com.levercore.guitartuner`, versionCode 6/versionName 0.2.4.
- GitHub Actions run `37858468007` **COMPLETED SUCCESS**: https://github.com/Levercore-stack/Guitar-tuner-app/actions/runs/37858468007 ; unsigned release artifact `11585625058` unpacked/integrity tested. Signed PRIVATELY outside public GitHub using the exact previously saved Guitar Tuner PKCS12 signing key. APK Signature Scheme **v2 and v3 VERIFIED TRUE**, certificate SHA256 `f32947e443fb9a390f8347586e75a85d95463793ae45cf582d6774099f222e6f` matches previous v0.2.3; signed ZIP integrity/manifest/dex/resources checked. No new signer created.
- **Final direct signed APK:** `Guitar_Tuner_v0.2.4_Stable_Signed.apk`, **6,511,115 bytes**, SHA256 `d7abdbbae2903120e3d4167a04851f6ae48351c73d57f7d614c0b74dfecbd33e`.
- **Direct native installer on existing Guitar Tuner Google Drive folder:** https://drive.google.com/file/d/1z_R5Z7e_HrzaFh3k-RfUr_VNLkfji5OW/view . Drive file ID `1z_R5Z7e_HrzaFh3k-RfUr_VNLkfji5OW`; readback verified APK MIME `application/vnd.android.package-archive`, exact 6511115 bytes, parent folder ID `1XB9aYuhxyCvNLGWOP_AYEacFuxmi1zEw`. User downloads directly on Pixel, no GitHub ZIP extraction or manual Drive re-upload.
- **Current gate:** user should install v0.2.4 OVER already installed v0.2.3 without uninstall. Check gold active tuning option, charcoal gray neck, unchanged tiny launcher pick icon and working mic. A successful CI/same-signer check does not replace user visual acceptance on real Pixel.
