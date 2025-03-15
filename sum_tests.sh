#!/bin/bash

# Initialize total tests counter
total_tests=0

# Find all surefire-reports directories and process each one
while IFS= read -r report_dir; do
    # Skip if report_dir is empty (just in case)
    [[ -z "$report_dir" ]] && continue

    if [ -d "$report_dir" ]; then
        # Extract test counts from all .txt files in the directory, 
        # joining them with plus signs if there are multiple files.
        count=$(grep "Tests run:" "$report_dir"/*.txt 2>/dev/null | \
                awk -F': ' '{gsub(/[^0-9]/, "", $2); print $2}' | \
                paste -sd+ -)

        if [[ -n "$count" ]]; then
            # Evaluate the arithmetic expression (whether it's "3" or "3+5")
            count=$(echo "$count" | bc)
            total_tests=$(echo "$total_tests + $count" | bc)
        else
            echo "No valid tests found in $report_dir"
        fi

        # Debugging output to track progress
        echo "Directory: $report_dir"
        echo "Extracted count: $count"
    fi
done < <(find . -type d -name surefire-reports | sort)

# Output total tests run
echo "Total tests run: $total_tests"
