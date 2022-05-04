#!/bin/bash

echo Initialising GPG

if [[ -z "$SONATYPE_USERNAME" ]]; then
  echo SONATYPE_USERNAME is not defined
  exit 1
fi
if [[ -z "$SONATYPE_PASSWORD" ]]; then
  echo SONATYPE_PASSWORD is not defined
  exit 1
fi
if [[ -z "$SIGNING_KEY_ID" ]]; then
  echo SIGNING_KEY_ID is not defined
  exit 1
fi
if [[ -z "$SIGNING_PASSWORD" ]]; then
  echo SIGNING_PASSWORD is not defined
  exit 1
fi
if [[ -z "$SIGNING_SECRET_KEY" ]]; then
  echo SIGNING_SECRET_KEY is not defined
  exit 1
fi

mkdir /root/.gradle

cat << EOF > /root/.gradle/gradle.properties
sonatypeUsername=$SONATYPE_USERNAME
sonatypePassword=$SONATYPE_PASSWORD
signing.keyId=$SIGNING_KEY_ID
signing.password=$SIGNING_PASSWORD
signing.secretKeyRingFile=/root/.gradle/sonatype-signing-key.gpg
EOF

echo $SIGNING_SECRET_KEY | base64 -d > /root/.gradle/sonatype-signing-key.gpg

echo GPG setup complete
