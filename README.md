<a href="https://opensource.newrelic.com/oss-category/#new-relic-experimental"><picture><source media="(prefers-color-scheme: dark)" srcset="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/dark/Experimental.png"><source media="(prefers-color-scheme: light)" srcset="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/Experimental.png"><img alt="New Relic Open Source experimental project banner." src="https://github.com/newrelic/opensource-website/raw/main/src/images/categories/Experimental.png"></picture></a>    
   
![GitHub forks](https://img.shields.io/github/forks/newrelic/newrelic-java-kotlin-coroutines?style=social)
![GitHub stars](https://img.shields.io/github/stars/newrelic/newrelic-java-kotlin-coroutines?style=social)
![GitHub watchers](https://img.shields.io/github/watchers/newrelic/newrelic-java-kotlin-coroutines?style=social)

![GitHub all releases](https://img.shields.io/github/downloads/newrelic/newrelic-java-kotlin-coroutines/total)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/newrelic/newrelic-java-kotlin-coroutines)
![GitHub last commit](https://img.shields.io/github/last-commit/newrelic/newrelic-java-kotlin-coroutines)
![GitHub Release Date](https://img.shields.io/github/release-date/newrelic/newrelic-java-kotlin-coroutines)


![GitHub issues](https://img.shields.io/github/issues/newrelic/newrelic-java-kotlin-coroutines)
![GitHub issues closed](https://img.shields.io/github/issues-closed/newrelic/newrelic-java-kotlin-coroutines)
![GitHub pull requests](https://img.shields.io/github/issues-pr/newrelic/newrelic-java-kotlin-coroutines)
![GitHub pull requests closed](https://img.shields.io/github/issues-pr-closed/newrelic/newrelic-java-kotlin-coroutines)

# New Relic Java Instrumentation for Kotlin Coroutines

Provides instrumentation for Kotlin Coroutines.  In particular it will trace the coroutine from its start, suspend and resume.   It does this across multilple threads.

## Supported Versions

Kotlin-Coroutines-1.0 - all 1.0.x versions. 
Kotlin-Coroutines-1.1 - all 1.1.x versions.  
Kotlin-Coroutines-1.2 - all 1.2.x versions.  
Kotlin-Coroutines-1.3 - all 1.3.x versions.   
Kotlin-Coroutines-1.4 - all 1.4.x & 1.5.x versions.   

## Installation
To use this instrumentation.   
Download the latest release.    
In the New Relic Java Agent directory (directory containing newrelic.jar), create a directory named extensions if it does not already exist.   
Copy the jars into the extensions directory.   
Restart the application.   

## Getting Started

After deployment of the instrumentation jars, you should be able to see the invocation of a coroutine from start to finish across any threads that it executes on.

## Usage

Instrumentation of methods with high invocation rates can lead to CPU overhead especially if its average response time is very small (i.e. less than a few milliseconds).  Therefore it is possible to configure the agent to ignore certain suspend methods, dispatched tasks and continuation resumeWiths.  This configuation is done in the newrelic.yml file.   

### Finding Possible Methods to Ignore
Run each of the following three NRQL queries where appName is the name of the application using Kotlin Coroutines.

SELECT rate(count(newrelic.timeslice.value), 1 MINUTE)  FROM Metric WHERE metricTimesliceName Like 'Custom/DispatchedTask/%' AND appName='appName”' SINCE 24 HOURS AGO FACET metricTimesliceName
   
SELECT rate(count(newrelic.timeslice.value), 1 MINUTE)  FROM Metric WHERE metricTimesliceName Like 'Custom/WrappedSuspend/%' AND appName='appName”' SINCE 24 HOURS AGO FACET metricTimesliceName
   
SELECT rate(count(newrelic.timeslice.value), 1 MINUTE)  FROM Metric WHERE metricTimesliceName Like 'Custom/ContinuationWrapper%' AND appName='appName”' SINCE 24 HOURS AGO FACET metricTimesliceName
   
The following is a screenshot of DispatchedTasks
   
![image](https://user-images.githubusercontent.com/8822859/111648374-475b1c00-87d1-11eb-8a49-8b9d9f94fcdf.png)
   
At minumum consider ignoring anything over 50K.

Below each rate is the name of the metric,  it has the form Custom/DispatchedTask/..., or Custom/WrappedSuspend/... or Custom/ContinuationWrapper/... depending on the query that was run.   Collect a list of the remaining metric name (i.e. the ...).
   
### Configuring Methods to Ignore
To configure methods to ignore, edit the newrelic.yml file in the New Relic Java Agent directory.   
1.  Find the following lines in newrelic.yml

![image](https://user-images.githubusercontent.com/8822859/111703076-257e8b00-880b-11eb-8ae9-f0961e98f907.png)
     
2. Insert the following lines BEFORE the above lines being mindful of spaces at the beginning of each line (2 on first, 4 on second, 6 on third).  Each list is comma separated listed from ones found in the previous section.  If none are none for the particular line it can be omitted.   

![image](https://user-images.githubusercontent.com/8822859/111703257-64acdc00-880b-11eb-86ae-66eb0254f618.png)
   
3. Save newrelic.yml 
   
Note that these setting are dynamic, so typically the agent should pick up changes within a minute or so and implement the changes without having to restart.


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
