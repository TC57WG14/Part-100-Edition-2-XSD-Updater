# Part-100-Ed-2-XSD-Updater

Updates those XSD profile files written for IEC 61968-100 edition 1 so that they
work with IEC 61968-100 edition 2

This is a sample Java file that illustrates how to upgrade those part-specific
XSD files (e.g. GetMeterReadings.xsd) that were originally written for
IEC 61968-100 edition 1 to be usable with IEC 61968-100 edition 2.
It assumes that the input file to be so transformed has a very specific structure.

Requires: Java 6 or higher; Maven

To Build: Run "mvn install"

To Run: The program reads a single IEC 61968-100 edition 1 XSD file on its standard input
        and writes the corresponding generated IEC 61968-100 edition 2 XSD file to its standard output.
        See the "run.sh" script for an example of its usage.
