DebLibs
-------

A Gradle plugin that creates Github issue and Slack message for outdated dependencies so they 
can easily be tracked and manually upgraded.

This plugin builds on the [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin).

Usage
-----

### `plugins` block:

```groovy
plugins {
  id "com.hellofresh.deblibs" version "$version"
}
```
or via the

### `buildscript` block:
```groovy
apply plugin: "com.hellofresh.deblibs"

buildscript {
  repositories {
    jcenter()
  }

  dependencies {
    classpath "com.hellofresh.deblibs:deblibs:$version"
  }
}
```

The current version is known to work with Gradle versions up to 4.8.

## Tasks

### `createGithuIssue`


### `createSlackMessage`

License
-------

    Copyright (C) 2018 The DebLibs Authors

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
