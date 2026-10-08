# Guitar Tuner — current project status
Updated 2026-10-08. ACTIVE: native Android v0.2 built; basic v0.1 Pixel pitch-response accepted by user; refined v0.2 UI awaits Pixel verification.

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
Install v0.2 APK directly from Google Drive, check string-neck order and dynamic tuning labels, preset grid, graph-paper pitch marker and updated styling; report UX issues without changing the proven v0.1 DSP. Keep debug-signing upgrade caveat visible.

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
