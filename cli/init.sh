set -e # fail on error
this_script_location=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$this_script_location" # to be independent from run location
cd ..
./mvnw clean install -pl cli -am
cd cli
docker build -t awin/transaction-cli .
docker run -p 8080:8080 awin/transaction-cli