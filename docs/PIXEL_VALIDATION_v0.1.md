# Guitar Tuner v0.1 — Pixel validation checklist
Build and source authority: https://github.com/Levercore-stack/Guitar-tuner-app
Successful CI: https://github.com/Levercore-stack/Guitar-tuner-app/actions/runs/37850537264
Debug APK sha256: d0edbaab807037d428a1a35fbcd587b18a1c12940a4dce9af814ae59adb1ef84

**Evidence labels:** BUILT means GitHub Actions compiled it; TESTED means the user ran the scenario on their Pixel. No Pixel microphone result is known yet.

1. Verify installed app/launcher label is Guitar Tuner, and dark graphite UI shows standard E A D G B E.
2. Start microphone, grant permission. For each string E2 A2 D3 G3 B3 E4 record detected note, target note, cents stability, and red/amber/green direction.
3. Test low D2, sustain, string harmonics and loud/quiet room noise; specifically watch for erroneous octave-high readings on low E2.
4. Deny microphone permission and verify helpful UI, not crash. Stop mic and switch away from app; microphone must release.
5. Test graphite UI accessibility, size and feel; user chooses any revised colors/layout.
6. Confirm no ads, account, payments, subscription, web, or audio upload.
7. Record observed issues and prioritise exactly one bounded repair, rebuild with CI, and repeat hardware test.
8. Debug build signing: no seamless in-place update guarantee between independently signed GitHub runners; establish an appropriate secure stable signer prior to any non-disposable state or normal upgrades.

Keep locked workflow v1.0 unchanged. Record process evidence in the new transferable trial document, not a competing global workflow.
