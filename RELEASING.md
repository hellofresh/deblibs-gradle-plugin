Releasing
========

 1. Create a branch from `master` in the format `release/<version_to_be_released>` Eg. `release/1.0.0`
 2. Change the `version` value in `build.gradle.kts` to the version number to be released.
 3. Update the `README.md` with the new version.
 6. Update `CHANGELOG.md` entry with changes of the release. 
 4. Run `./gradlew clean build` to make sure project builds successfully.
 5. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the new version).
 6. `git tag -a X.Y.Z -m "Version X.Y.Z"`
 7. `git push && git push --tags`
 8. Update `version` value in `build.gradle.kts` to the next SNAPSHOT version.
 9. `git commit -am "Prepare next development version."`
 10. `git push`

 *Note:* To get the changelog messages from the commit history, issue.

 ```shell
 git log "$(git tag | tail -n2 | head -n1)..$(git tag | tail -n1)" --oneline --invert-grep --grep="Merge pull request" --grep="Prepare for release" | cut -d' ' -f2- | sed -E -e 's/^/-m "/' | sed -E -e 's/$/"/'
 ```

