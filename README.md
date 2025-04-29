# GDAPI

A library for communicating the Geometry Dash Servers

> As of now, it is in alpha and is very incomplete. Once fully released, it should cover a lot of the Geometry Dash endpoints.
>
> If you plan on using this, you will need to read the source code for usages and how the library works.
>
> Features as of now:
>
> - Uploading and deleting both Account and Level comments

## Installation

### Gradle

```gradle
repositories {
    maven {
        name = "lncvrtRepositoryReleases"
        url = uri("https://repo.lncvrt.xyz/releases")
    }
}

dependencies {
    implementation("xyz.lncvrt:gdapi:1.0.0-alpha.1")
}
```

### Maven

```maven
<repository>
  <id>lncvrt-repository-releases</id>
  <name>Lncvrts repository</name>
  <url>https://repo.lncvrt.xyz/releases</url>
</repository>

<dependency>
  <groupId>xyz.lncvrt</groupId>
  <artifactId>gdapi</artifactId>
  <version>1.0.0-alpha.1</version>
</dependency>
```

## Usage

```java
GDAPI gdapi = new GDAPI("username", "password");
// ...
```

## Questions, Suggestions, or Feedback

If you have any questions, suggestions, or need clarification on anything, you can open a new issue or contact me on Discord (@lncvrt).

## Contributing

If you'd like to contribute to GDAPI, you can create a new pull request and I will review it when I can.
