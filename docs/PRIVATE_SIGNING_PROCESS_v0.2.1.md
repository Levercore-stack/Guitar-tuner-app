# Guitar Tuner private signing procedure — v0.2.1 and later

This is a personal/development signing process. The **public GitHub repository contains no private signing key, password, or base64 representation of them**. Never copy the workout logger's public prototype key. This does not establish Play Store/production signing or hardware-key protection.

## Authority and recovery
- Single per-app stable signing key: alias `guitar-tuner-prototype-v1`, PKCS12 RSA-3072, 10-year self-signed certificate.
- Expected certificate SHA-256: `F3:29:47:E4:43:FB:9A:39:0F:83:47:58:6E:75:A8:5D:95:46:37:93:AE:45:CF:58:2D:67:74:09:9F:22:2E:6F`.
- Personal private Library path, **NOT Google Drive or GitHub**: `/Lever core/Guitar Tuner Private Signing/`. Files: `Guitar_Tuner_Private_Stable_Key_v1.p12`, `Guitar_Tuner_Private_Stable_Key_v1_Passphrase.txt`. The latter is an instruction record that includes the secret password, not a raw password file; extract only the secret into a transient restricted-permission password file when using the script.
- Advise owner to back up signing key and passphrase separately in an encrypted personal vault. Loss of key means future updates cannot preserve the Android signer identity.
- GitHub Actions `.github/workflows/bootstrap-android.yml` compiles APK from canonical source, uploads unsigned release and official Android apksigner tool as two separate artifacts; this makes the build reproducible without exposing signing secrets in a public CI run.
- Download those two artifacts into a private trusted signing environment, expand them, then use `scripts/sign_private_release.sh APKSIGNER_JAR P12_KEY PURE_PASSWORD_FILE APP_RELEASE_UNSIGNED.APK OUTPUT_SIGNED.APK`; script verifies signature fingerprint and SHA after signing. Run `apksigner verify` and compare signer fingerprint against the known-good value.
- Check `app/build.gradle.kts` package `com.levercore.guitartuner` and monotonically increasing `versionCode` before each release; never change package/signing key for the same app.
- Upload the **raw signed APK**, not only a ZIP, directly to the existing Guitar Tuner project folder on connected Google Drive. Verify filename, byte size, parent folder and link. Include link and SHA in `docs/CURRENT_STATUS.md` and the canonical Drive status/continuation.
- The existing v0.1/v0.2 apps were signed by ephemeral CI debug identities. They are **not compatible** with this new certificate; user has explicitly approved one clean uninstall/reinstall of test state to migrate. Do not silently delete state. The first **real upgrade proof** occurs when a later APK with the same private signer installs over signed v0.2.1 without uninstalling.
- No analytics, subscriptions, payments, ads or audio upload. Hardware acceptance remains user-reported.

**Status 2026-10-08:** Key generated and privately backed up. First release signing and Android Pixel confirmation pending CI completion.
