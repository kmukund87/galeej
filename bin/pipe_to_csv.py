import csv
import argparse

def convert_to_csv(input_file, output_file):
    with open(input_file, 'r') as file:
        input_data = file.read()

    # Parse the tabular data into a list of dictionaries
    lines = input_data.strip().split('\n')
    headers = [header.strip() for header in lines[1].strip('|').split('|')]
    data = []
    for line in lines[3:-1]:
        values = [value.strip() for value in line.strip('|').split('|')]
        data.append(dict(zip(headers, values)))

    # Write data to CSV file
    with open(output_file, mode='w', newline='') as csvfile:
        writer = csv.DictWriter(csvfile, fieldnames=headers)
        writer.writeheader()
        writer.writerows(data)

    print(f"Data converted and saved to '{output_file}' successfully!")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Convert tabular data to CSV")
    parser.add_argument("input_file", help="Path to the file containing the tabular data")
    parser.add_argument("output_file", help="Path to the output CSV file")
    args = parser.parse_args()

    convert_to_csv(args.input_file, args.output_file)
