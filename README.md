
# PhysioNotes (Android)

A minimal, private app for physical therapists to maintain a patient list and treatment notes.

## Features
- Add, view, and delete patients
- Add and delete time-stamped treatment notes per patient
- Simple search via the patient list (compose UI ready to extend)
- Export all data to a CSV file using Android's share/document picker

## Build
1. Open Android Studio > *Open an Existing Project* > select this folder.
2. Let Gradle sync. If prompted to update plugin or Kotlin versions, accept.
3. Run on your Android device or emulator (minSdk 24).

## Privacy
All data is stored locally on your device in a Room database (`physio.db`).

## Next steps (easy improvements)
- Add search field on patient list
- Add pain scale or outcome measures to notes
- Add image attachments (e.g., exercise photos)
- Enable biometric lock (AppCompat Biometric)
- Cloud backup (optional) via Google Drive

---
Made with Jetpack Compose + Room.
