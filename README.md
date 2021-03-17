[![New Relic Experimental header](https://github.com/newrelic/opensource-website/raw/master/src/images/categories/Experimental.png)](https://opensource.newrelic.com/oss-category/#new-relic-experimental)

# New Relic Java Instrumentation for Kotlin Coroutines

Provides instrumentation for Kotlin Coroutines.  In particular it will trace the coroutine from its start, suspend and resume.   It does this across multilple threads.

## Installation
This use this instrumentation.   
Download the latest release.    
In the New Relic Java Agent directory (directory containing newrelic.jar), create a directory named extensions if it doe not already exist.   
Copy the jars into the extensions directory.   
Restart the application.   

## Getting Started

>[Simple steps to start working with the software similar to a "Hello World"]

## Usage


## Building

If you make changes to the instrumentation code and need to build the instrumentation jars, follow these steps
1. Set environment variable NEW_RELIC_EXTENSIONS_DIR.  Its value should be the directory where you want to build the jars (i.e. the extensions directory of the Java Agent).   
2. Build one or all of the jars.   
a. To build one jar, run the command:  gradlew _moduleName_:clean  _moduleName_:install    
b. To build all jars, run the command: gradlew clean install
3. Restart the application

## Support

New Relic has open-sourced this project. This project is provided AS-IS WITHOUT WARRANTY OR DEDICATED SUPPORT. Issues and contributions should be reported to the project here on GitHub.

>We encourage you to bring your experiences and questions to the [Explorers Hub](https://discuss.newrelic.com) where our community members collaborate on solutions and new ideas.

## Contributing

We encourage your contributions to improve Salesforce Commerce Cloud for New Relic Browser! Keep in mind when you submit your pull request, you'll need to sign the CLA via the click-through using CLA-Assistant. You only have to sign the CLA one time per project. If you have any questions, or to execute our corporate CLA, required if your contribution is on behalf of a company, please drop us an email at opensource@newrelic.com.

**A note about vulnerabilities**

As noted in our [security policy](../../security/policy), New Relic is committed to the privacy and security of our customers and their data. We believe that providing coordinated disclosure by security researchers and engaging with the security community are important means to achieve our security goals.

If you believe you have found a security vulnerability in this project or any of New Relic's products or websites, we welcome and greatly appreciate you reporting it to New Relic through [HackerOne](https://hackerone.com/newrelic).

## License

[Project Name] is licensed under the [Apache 2.0](http://apache.org/licenses/LICENSE-2.0.txt) License.

>[If applicable: [Project Name] also uses source code from third-party libraries. You can find full details on which libraries are used and the terms under which they are licensed in the third-party notices document.]
