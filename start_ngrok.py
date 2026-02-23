import subprocess
import time
import requests
from pathlib import Path
from os import getcwd

loading = "Generating new ngrok url"
print(loading, end="")

# Start ngrok
process = subprocess.Popen(
    ["ngrok", "http", "127.0.0.1:8080"],
    stdout=subprocess.DEVNULL,
    stderr=subprocess.DEVNULL
)

# Need to sleep for at least 3secs to wait for ngrok
for i in range(3):
    print(".", end="")
    time.sleep(1)

print()

res = requests.get("http://127.0.0.1:4040/api/tunnels")
data = res.json()

public_url = data["tunnels"][0]["public_url"]
print(f"Generated new ngrok url: {public_url}")

# Modify gradle
gradle_file = Path(getcwd() + "/app/build.gradle.kts")

print("Updating gradle.build.kts...")

with gradle_file.open('r') as f:
    lines = f.readlines()

for i in range(len(lines)):
    line = lines[i]
    if "buildConfigField" in line:
        start_index = line.index("BASE_URL")
        end_index = line[start_index:].index(")") + start_index
        base_and_url = line[start_index:end_index]
        url_start_index = base_and_url.index("\\")
        url_end_index = base_and_url[url_start_index + 1:].index("\\") + url_start_index
        url_to_replace = base_and_url[url_start_index:url_end_index+3]
        lines[i] = line.replace(url_to_replace, f"\\\"{public_url}\\\"")

with gradle_file.open('w') as f:
    f.writelines(lines)
    
print("--- Done ---")
print("Sync project to complete new url generation process")
