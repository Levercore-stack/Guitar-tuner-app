#!/usr/bin/env bash
# Guitar Tuner: OFFLINE private signing. No keystore or password is stored in this public repository.
# Usage: bash scripts/sign_private_release.sh APKSIGNER_JAR KEYSTORE_P12 PURE_PASSWORD_FILE UNSIGNED_APK SIGNED_APK
set -euo pipefail
if [[ "$#" -ne 5 ]]; then
  echo "Usage: $0 APKSIGNER_JAR KEYSTORE_P12 PURE_PASSWORD_FILE UNSIGNED_APK SIGNED_APK" >&2
  exit 2
fi
for filename in "$1" "$2" "$3" "$4"; do
  test -s "$filename" || { echo "Missing required private/build file: $filename" >&2; exit 3; }
done
java -jar "$1" sign \
  --ks "$2" \
  --ks-type PKCS12 \
  --ks-key-alias guitar-tuner-prototype-v1 \
  --ks-pass "file:$3" --key-pass "file:$3" \
  --out "$5" "$4"
java -jar "$1" verify --verbose --print-certs "$5"
sha256sum "$5"
