set -e # fail on error
this_script_location=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$this_script_location" # to be independent from run location
cd ..
./mvnw clean install -pl web -am
cd web
docker build -t awin/transaction-web .
docker run -p 8080:8080 awin/transaction-web