# Jenkins Shared Libraries

This project contains common pieces of groovy code that are commonly used in
Jenkinsfiles. For this the _Shared Libraries_ feature of Jenkins is used.

## What Are the Goals of This Project?

The goal of the project is to provide reusable functions and reduce duplication.

## Relevant URLs

- https://ci.process-engine.io
- https://jenkins.io/doc/book/pipeline/shared-libraries/

## How Do I Set This Project Up?

### Setup

There is no setup required.

## How Do I Use This Project?

### Installation

Follow the instructions at the [Jenkins Documentation](https://jenkins.io/doc/book/pipeline/shared-libraries/#using-libraries).
Use `jenkins_shared-libraries` as name and `master` as version. Configure
Source Code Management to `git` and set
`git@github.com:process-engine/jenkins_shared-libraries.git` as clone url.

### Usage

First you need to import this library into your pipeline:

```groovy
@Library('jenkins_shared-libraries@<version>') _
```

Replace `<version>` with the GitHub Release you want to use.

For example using version 1.0.0:

```groovy
@Library('jenkins_shared-libraries@1.0.0') _
```

#### NuGet Tools

This library contains useful functions when working with NuGet packages.

##### Checking if Package Is Published on a Feed

This function uses the NuGet API to check if a specified version of a given
package is published.

It will return either true or false.

**Synopsis:**

```groovy
script {
  def packageWasPublished = nuGetTools.isNuGetPackagePublished(
    nuGetFeedURL: "<Your Feed Index URL>",
    package: "<Name of The Package to Check>",
    version: "<Version of The Package>",
    nuGetToken: "<Your NuGet Access Token>",
  );
}
```

**Example Usage:**

```groovy
script {
  def packageWasPublished = nuGetTools.isNuGetPackagePublished(
    nuGetFeedURL: "https://5minds.myget.org/F/process_engine_public/api/v3/index.json",
    package: "ProcessEngine.Runtime",
    version: "3.8.2-pre1",
  );
}
```

This will check if the package `ProcessEngine.Runtime`, version `3.8.2-pre1`
is published on the feed `process_engine_public`. Because the feed is public,
the access token can be omitted.


##### Getting The Package From A .csproj File

This function will return the version defined in a `.csproj` file.

**Synopsis:**

```groovy
script {
  def packageVersion = nuGetTools.isNuGetPackagePublished(
    fileName: "<Path to .csproj File>",
  );
}
```

**Example Usage:**

```groovy
script {
  def packageVersion = nuGetTools.isNuGetPackagePublished(
    fileName: "myproject.csproj",
  );
}
```

This will return the version read from the `myproject.csproj` file.

### Authors/Contact Information

- Paul Heidenreich <paul.heidenreich@5minds.de>

### Related Projects (*)

- https://github.com/process-engine/ci_tools
