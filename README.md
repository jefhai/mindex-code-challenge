# Solution to Mindex Coding Challenge
You can find the initial template of this challenge on `template`

# Challenge 1: mindex-java-code-challenge

### OpenJDK
`brew cask install adoptopenjdk`

### Apache Maven
`brew install maven`

### Gradle
`brew install gradle`

## Run
1) cd to mindex-java-code-challenge
2) `gradle --version`
3) `gradle wrapper --gradle-version X.X.X`
4) run with `./gradlew bootRun`

## Run Tests
`./gradlew test`

## TODO
- Add duplication and circular dependency protection
- Query reporting structure vs lazy load recursion
- Reload database during test setup
