# Custom OpenTelemetry Java Extension for Dubbo 2.5.x Compatibility

## Overview

This repository contains a custom OpenTelemetry Java extension designed to offer backward compatibility for the Dubbo 2.5.x framework. Developed by the Splunk Presales team, it enables key OpenTelemetry capabilities such as distributed tracing and metrics capture for Dubbo 2.5.x.

## Features

- **Backward Compatibility**: Integration with Dubbo 2.5.x.
- **Distributed Tracing**: Enables Dubbo 2.5.x to be part of OpenTelemetry's distributed traces.

## Installation and Usage

To include this extension along with the official OpenTelemetry Java agent, use the following Java VM option:

\`\`\`bash
-javaagent:/path/to/splunk-otel-javaagent.jar -Dotel.javaagent.extensions=/path/to/custom-java-ext-dubbo2_5-x.x-all.jar
\`\`\`

Replace `/path/to/` with the appropriate directories where your `.jar` files are located.

## OpenTelemetry Java Extension Capabilities

This custom extension leverages OpenTelemetry's powerful Java extension capabilities, allowing for additional instrumentation and customization. For more details on extending the OpenTelemetry Java Agent, please refer to the [official documentation](https://github.com/open-telemetry/opentelemetry-java-instrumentation/blob/main/docs/contributing/extension.md).

## OTel Java Agent Co-existence

This extension uses OpenTelemetry SDK, and relies on the same version of the SDK being used by the Java agent it extends. 
The following versions have been built and tested:

|Extension version | OTel Java SDK Version | Splunk Otel Java Agent Version |
|----------------- |-----------------------|--------------------------------|
| 2.0 | 1.31.0                | 1.29.0                         |
|2.1| 1.26.0                | 1.28.0                         |

## Limitations

1. This is a temporary, stop-gap measure targeting specific use-cases where migration to a more recent version of Dubbo is not possible.
2. The Dubbo 2.5.x framework is no longer under active development or support.
3. This extension is not officially supported by Splunk. Use at your own risk.
