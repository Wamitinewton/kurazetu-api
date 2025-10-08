#!/bin/bash

set -e

# Load environment variables from .env if it exists
if [ -f .env ]; then
  export $(grep -v '^#' .env | xargs)
fi

# Use environment variables
VM_USER=$(whoami)

echo "Uploading docker-compose to VM..."
gcloud compute scp docker/docker-compose.yml ${VM_USER}@${VM_INSTANCE}:~/kurazetu-api/docker/ \
  --project=$PROJECT_ID --zone=$ZONE

echo "Starting Kafka services..."
gcloud compute ssh ${VM_INSTANCE} --project=$PROJECT_ID --zone=$ZONE --command="
    cd ~/kurazetu-api/docker
    docker-compose up -d
"

echo "Building and uploading application..."
./mvnw clean package -DskipTests

gcloud compute scp target/*.jar ${VM_USER}@${VM_INSTANCE}:~/kurazetu-api/kurazetu-api.jar \
  --project=$PROJECT_ID --zone=$ZONE

echo "Starting application service..."
gcloud compute ssh ${VM_INSTANCE} --project=$PROJECT_ID --zone=$ZONE --command="
    sudo systemctl enable kurazetu-api
    sudo systemctl start kurazetu-api
"

echo "Initial setup complete!"
