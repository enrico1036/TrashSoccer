# TrashSoccer

**TrashSoccer** is a trash-styled physics soccer game built with [libGDX](https://libgdx.com) and Box2D. It started as an inside joke while playing Ragdoll Soccer — *"wouldn't it be more fun if it had trash features?"* — and grew into its own game.

Ragdolls fly, goals are scored, worlds get slippery (or on fire), and nothing is quite as serious as it looks.

## Features

- **Ragdoll physics** — every player is a Box2D ragdoll with its own body, head, arms and legs, and a kicking "leg joint".
- **Trash worlds** — six themes, each with its own physics: `default`, `hell`, `night`, `pinky`, `slippery` and `space` (gravity and terrain friction are configured per-world in XML).
- **Single player** — play against the AI team.
- **Multiplayer** — local 2-player matches on the same device.
- **Touch controls** — dedicated *jump* and *kick* buttons per side (blue on the left, red on the right).
- **Custom player skins** — afro or bald.
- **Menu, pause and win screens** — pause/resume, back to menu, and a win/congratulations screen.
- **Sounds & music** — jump, kick and background music, with a persistent mute/volume toggle (saved to a file on exit).

## Project structure

```
TrashSoccer/
├── core/       Platform-independent game logic (Gradle module: `core`)
│   └── src/com/trashgames/trashsoccer/
│       ├── Game.java, Asset.java, B2DFilter.java, MyContactListener.java
│       ├── entities/   Player, Ball, Goal, Terrain, Score
│       └── screen/     MenuScreen, SinglePlayerScreen, MultiPlayerScreen, PauseScreen, ScreenManager
├── android/    Android launcher + all game assets (Gradle module: `android`)
│   └── assets/data/    textures (players, worlds, ui), fonts, sounds, world properties
├── desktop/    ⚠️ legacy LWJGL2 desktop launcher (libGDX 1.3.1 jars, not part of the Gradle build)
└── ios/        ⚠️ legacy RoboVM launcher (not part of the Gradle build)
```

Only the `core` and `android` modules are part of the current Gradle build (`settings.gradle`). The `desktop` and `ios` folders are leftovers from earlier iterations — the desktop launcher still references the old libGDX 1.3.1 jars under `desktop/jars/`.

## Dependencies

| Dependency | Version | Used for |
|---|---|---|
| [libGDX](https://libgdx.com) | 1.13.1 | Game framework (core, box2d, android backend) |
| [Box2D](https://box2d.org) | via libGDX | Physics engine |
| JDK | 17+ | Compilation (AGP 8.x requirement) |
| Gradle | 8.14.4 (wrapper) | Build system |
| Android Gradle Plugin | 8.10.1 | Android build |
| Android SDK | API 36 (compile/target), min API 21 | Android platform |
| Build Tools | 36.0.0 | Android toolchain |
| Nix + nixpkgs | channel-pinned | *Optional* reproducible build env (`shell.nix`) |

Java 11 source/target compatibility is used for the core and Android modules.

## Building & installation

### Prerequisites

- **JDK 17 or newer** and an **Android SDK** with **platform 36** and **build-tools 36.0.0**.
- The Gradle wrapper downloads Gradle and all Maven dependencies on first build.

You can either use the Nix development shell (recommended, reproducible) or a manually installed toolchain.

### Option A — Nix shell (recommended)

The repository ships a `shell.nix` that pins a full, reproducible Android toolchain (JDK 17, Android SDK with platforms 36/35, Gradle, plus the AGP `aapt2` override and `local.properties` generation).

```bash
nix-shell
./gradlew :android:assembleDebug -x lint
```

### Option B — Manual toolchain

If you already have JDK 17+ and the Android SDK installed:

```bash
export ANDROID_HOME=/path/to/android-sdk     # or create local.properties with: sdk.dir=/path/to/android-sdk
./gradlew :android:assembleDebug -x lint
```

> Android Studio also works: open the repo, let Gradle sync, and run the `android` module.

### Output

The APKs are written to:

```
android/build/outputs/apk/debug/android-debug.apk      # debug build
android/build/outputs/apk/release/android-release.apk  # release build
```

Install on a connected device (or emulator) with:

```bash
adb install -r android/build/outputs/apk/debug/android-debug.apk
```

The app is **landscape-only** and runs on Android 5.0+ (minSdk 21), targeting API 36. Package name: `com.trashgames.trashsoccer.android`.

### Release signing

Release builds are signed with `android/trashsoccer.keystore` (alias `trashsoccer`), which is **git-ignored and not in the repository** — it exists only on the machine where it was generated. To create your own:

```bash
keytool -genkey -v -keystore android/trashsoccer.keystore -alias trashsoccer \
        -keyalg RSA -keysize 2048 -validity 10000 \
        -storepass trashsoccer -keypass trashsoccer -dname "CN=TrashSoccer"
./gradlew :android:assembleRelease -x lint
```

## Gameplay & controls

- **Menu** — choose *Single Player* (vs AI) or *Multiplayer* (local 2P), toggle the volume.
- **In game** — each side has on-screen **jump** and **kick** buttons; the blue set controls the left team, the red set the right team.
- **Pause** — any moment of the match can be paused and resumed or abandoned to the menu.

## Configuration

Per-world physics come from XML files under `android/assets/data/textures/world/<world>/properties.xml`, e.g. the `slippery` world sets friction to `0`:

```xml
<world>
    <gravity>-9.81</gravity>
    <friction>0</friction>
</world>
```

The `shell.nix` file centrally declares the Android SDK versions; bump `platformVersions` / `buildToolsVersions` there if you need a different SDK level.

## License

No license file is included in this repository.