# Changelog

This project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

[Unreleased]:

## Added
-

## [2.2.0] - 2020-02-13

## Changed
- Fixed a bug with Moshi not able to de/serialize JSON strings.

## Added
- Added a sample project to help with manual testing of changes to the project.

## Updated
- Updated CHANGELOG.md footer links with unreleased info.

## [2.1.0] - 2020-02-06

### Updated
- Updated README.md specifying the minimum required Gradle version.
- Updated all the dependencies to their latest version.

### Added
- Added support for posting report to Gitlab as an issue.

## [2.0.0] - 2019-02-19

### Added
- Replaced hardcoded latest version number in the README.md with a dynamic latest version badge.

- Made error message when a repository is not found to read better. Now instead of `404 Repository with Repo: 'hellofresh/deblibs-gradle-plugin' was not found` it now reads `404 Repository at 'hellofresh/deblibs-gradle-plugin' was not found`.

## Changed
- Fixed wrong Gradle plugin website URL.

### Removed
-  Removed `githubOwner` property and replaced it with just `githubRepo` which means you have to add
the owner as well. So now `githubRepo` value would be `hellofresh/deblibs-gradle-plugin`.

-  Removed Slack's default icon and replaced it with nothing. This means if you want an icon with the
bot you have to provide a URL for the icon.

## [1.0.0] - 2018-12-11
### Added
- Added support for posting report to Github as an issue.
- Added support for posting report to Slack as a message.

[Unreleased]: https://github.com/hellofresh/deblibs-gradle-plugin/compare/2.1.0...HEAD
[2.1.0]: https://github.com/hellofresh/deblibs-gradle-plugin/compare/2.0.0...2.1.0
[2.0.0]: https://github.com/hellofresh/deblibs-gradle-plugin/compare/1.0.0...2.0.0
[1.0.0]: https://github.com/hellofresh/deblibs-gradle-plugin/compare/04fd121...1.0.0
