#!/bin/bash

# Initialize the total count of tests run
total_tests=0

# Loop through each submodule's Surefire report
for report in **/target/surefire-reports/TEST-*.xml; do
    if [[ -f "$report" ]]; then
        # Count the number of <testcase> elements in the XML file
        tests_in_report=$(grep -o "<testcase" "$report" | wc -l)
        total_tests=$((total_tests + tests_in_report))
    fi
done

# Output the total number of tests run across all submodules
echo "Total number of tests run: $total_tests"
echo