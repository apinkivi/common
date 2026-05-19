# Common utilities

These Kotlin/Common domain independent utilities use minimal dependencies.

## Project hierarchy

```mermaid
flowchart TD
kotlinx-serialization-json --> kotlinx-serialization-core
common[<b>common</b><br>primitive extensions, Version, collection utils, Domain] --> kotlinx-serialization-core
common -.-> kotlin-test
datetime[<b>common-datetime</b><br>parsers] --> kotlinx-datetime
serialization[<b>common-serialization</b><br>de- & serializers] --> kotlinx-serialization-json
serialization --> common
serialization --> datetime
io[<b>common-io</b><br>Dir, File and FileLinesReader] --> common
```

## Commands

- `gradlew dependencyUpdates --no-configuration-cache --no-parallel -Dorg.gradle.warning.mode=none` - Report updates
- `gradlew wrapper --gradle-version 9.5.1 --distribution-type all` - Update Gradle wrapper
- `gradlew spotlessApply --no-configuration-cache` - Format Kotlin source
