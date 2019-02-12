# Changelog

This project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Removed
-  Removed `githubOwner` property and replaced it with just `githubRepo` which means you have to add
the owner as well. So now `githubRepo` value would be `hellofresh/deblibs-gradle-plugin`

### Added
- Unit tests

### Removed
-  Removed Slack's default icon and replaced it with nothing. This means if you want an icon with the 
bot you've to provide a URL for the icon. 

## [1.0.0] - 2018-12-11
### Added
- Added support for posting report to Github as an issue.
- Added support for posting report to Slack as a message.

[Unreleased]: https://github.com/hellofresh/deblibs-gradle-plugin/compare/1.0.0...HEAD
[1.0.0]: https://github.com/hellofresh/deblibs-gradle-plugin/compare/04fd121...1.0.0
