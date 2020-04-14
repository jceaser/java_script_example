# Scripter
An example application on how to use the built in scripting engine in java to control actions in java externally from a script.

## Overview

Demonstrate how to use the built in scripting engine in Java.

App.java is the command line application which pulls everything together. It will create two objects, Global and ReversePolishNotation which represent two Plain Old Java Objects (POJO) which need to be connected together through scripting. Also in this example it will be shown how the scripting environment can be used to hold information across multiple calls.

## Design

App will hold the scripting environment, a global object, and a RPN calculator. The App will provide the global object and the RPN to the scripting environment and allow them to be manipulated through that (scripting) layer. Scripts must implement a "main" method which returns a value. This value will be provided to other scripts as the "last" object allowing for another way for scripts to consume data from other runs.

## Usage

    java -cp <path to jar> \
        --help \
        --script <path to script> \
        --engine <name of java scripting engine> \
        --standard-in

* --help displays a help message and then stops execution
* --engine name of scripting engine as defined by the Java Scripting API. Default is `nashorn`.
* --standard-in reads script from standard in, after all --script files have run
* --script can repeat to run multiple scripts

## Conclusion
