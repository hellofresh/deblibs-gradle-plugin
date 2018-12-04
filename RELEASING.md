Releasing
========

 1. Change the `version` value in `build.gradle.kts` to the version number to be released.
 2. Update the `README.md` with the new version.
 3. Run `./gradlew clean build` to make sure project builds successfully
 4. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the new version)
 5. `git tag -a X.Y.Z -m "Version X.Y.Z"` -m "Changelog message 1" - m "Changelog message 2" -m "Changelog message 3"
 6. `git push && git push --tags`
 7. Update `version` value in `build.gradle.kts` to the next SNAPSHOT version.
 8. `git commit -am "Prepare next development version."`
 9. `git push`

 *Note:* To get the changelog messages from the commit history, issue

 ```shell
 git log "$(git tag | tail -n2 | head -n1)..$(git tag | tail -n1)" --oneline --invert-grep --grep="Merge pull request" --grep="Prepare for release" | cut -d' ' -f2- | sed -E -e 's/^/-m "/' | sed -E -e 's/$/"/'
 ```

