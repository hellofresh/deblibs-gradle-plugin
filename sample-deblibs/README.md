# Sample project to consume Deblibs

This is a sample project as a consumer of the Deblibs Gradle plugin. See it is as a project
for manually testing changes in the plugin.

To run Deblibs tasks from the sample project, make sure you've updated `sample-deblibs/build.gradle.kts` with the
version number you want to run.

### Build

Build the sample project with: `./gradlew build` when in `sample-deblibs` folder.

####  Run Deblibs commands

To create a Github issue, issue the command:

`./gradlew createGithubIssue`

To post to Slack, issue the command:

`./gradlew createSlackMessage`

To create a Gitlab issue, issue the command:

`./gradlew createGitlabIssue`

**Note:** You've to make sure you've configured the required environmental variables as defined in
`sample-deblibs/build.gradke.kts` under `deblibs` block.
