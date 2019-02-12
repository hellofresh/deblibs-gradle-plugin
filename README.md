[![Build Status](https://travis-ci.org/hellofresh/deblibs-gradle-plugin.svg?branch=master)](https://travis-ci.org/hellofresh/deblibs-gradle-plugin) [![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v/https/plugins.gradle.org/m2/com/hellofresh/gradle/deblibs/com.hellofresh.gradle.deblibs.gradle.plugin/maven-metadata.xml.svg?label=Download)](https://plugins.gradle.org/plugin/com.hellofresh.gradle.deblibs)

DebLibs
-------

A Gradle plugin that creates Github issue and Slack message for outdated project dependencies so they 
can easily be tracked and manually upgraded.

This plugin builds on the [Gradle Versions Plugin](https://github.com/ben-manes/gradle-versions-plugin).

Screenshots
-----------

#### Sample Github Issue

![Alt text](screenshots/github-issue.png?raw=true "Sample github issue")

#### Sample Slack Issue
![Alt text](screenshots/slack-message.png?raw=true "Sample slack message")

Usage
-----

### `plugins` block:

_`Build script snippet for plugins DSL for Gradle 2.1 and later:`_

```groovy
plugins {
  id "com.hellofresh.gradle.deblibs" version "$version"
}
```

_`Build script snippet for use in older Gradle versions or where dynamic configuration is required:`_
### `buildscript` block:
```groovy
apply plugin: "com.hellofresh.gradle.deblibs"

buildscript {

  repositories {
    maven { url "https://plugins.gradle.org/m2/"}
  }

  dependencies {
    classpath "com.hellofresh.gradle:deblibs:$version"
  }
  
}
```

The plugin show work with Gradle version 4.3 and above.

Configuration
-------------
The following configuration block is _required._

If you don't configure the default will be used which are blank values. This will lead to undesirable behaviour which means info about outdated dependencies won't be uploaded.

```groovy
deblibs {
   projectName ="Project name"
   githubRepo = "project-github-repo"
   githubToken = "github-token"
   slackToken = "slack-token"
   slackChannel = "#slack-channel"
   slackIconUrl = "url-to-an-icon-to-be-used-by-the-slack-bot"    
}

```

Tasks
----

The plugin comes with two tasks. A task for publishing info about outdated dependencies to Github as an issue. This is useful when you want to track outdated dependencies as an issue on Github.

The second task is for publishing info about outdated dependencies to a Slack channel as a message. This is also useful when you want to post outdated dependencies to slack.

To create a Github issue, issue the command:

`./gradlew createGithubIssue` 

To post to Slack, issue the command:

`./gradlew createSlackMessage`

Development
-----------
### Import
Import the [settings.gradle.kts](https://github.com/hellofresh/deblibs-gradle-plugin/blob/master/settings.gradle.kts) file into your IDE for development.

### Build

Build the plugin with: `./gradlew build`

Publish to a local maven repository for testing with: `./gradlew publishToMavenLocal`


Link
----
[Deblibs Gradle Plugin on the Gradle Plugin Registry](https://plugins.gradle.org/plugin/com.hellofresh.gradle.deblibs)

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
