set -e # fail on error
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"
../mvnw clean install
docker build -t awin/transaction-cli .
docker run -p 8080:8080 awin/transaction-cli