#!/bin/bash

# Initialize total tests counter
total_tests=0

# Find all surefire-reports directories and sort them alphabetically
for report_dir in $(find . -type d -name surefire-reports | sort); do
    if [ -d "$report_dir" ]; then
        # Extract test counts
        count=$(grep "Tests run:" "$report_dir"/*.txt 2>/dev/null | \
                awk -F':' '/Tests run:/ {gsub(/[^0-9]/, "", $2); print $2}' | \
                paste -sd+ -)

        # Debug: Print extracted count before processing
        echo "Directory: $report_dir"
        echo "Extracted count: $count"

        # Safeguard: Ensure count is valid for bc
        if [[ -n "$count" && "$count" =~ ^[0-9+]+$ ]]; then
            count=$(echo "$count" | bc)
        else
            count=0
        fi

        # Add to total
        total_tests=$((total_tests + count))
    fi
done

# Output total tests run
echo "Total tests run: $total_tests"
