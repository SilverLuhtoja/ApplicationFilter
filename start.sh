#!/bin/bash

# Simple ANSI colors
ORANGE='\033[38;5;208m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

# Exit script on first error
set -e

echo -e "${ORANGE}Application containerization process starting ${NC}"

cd server/docker
echo -e "${ORANGE}Starting to build server...${NC}"
docker-compose down
docker-compose up -d --build

# Wait for the backend on port 8000
echo -e "${ORANGE}Waiting for server to become available on port 8000...${NC}"
until curl -s http://localhost:8000 > /dev/null; do
  sleep 2
done

echo -e "${GREEN}Server container is up and runnning${NC}"

cd ../../client
echo -e "${ORANGE}Starting to build client-app...${NC}"
docker-compose down
docker-compose up -d --build

# Wait for the frontend to start
echo -e "${ORANGE}Waiting for client-app to be available...${NC}"
until curl -s http://localhost:5173 > /dev/null; do
  sleep 2
done

echo -e "${GREEN}Client-app container is up and runnning${NC}"

echo -e "${ORANGE}Opening http://localhost:5173...${NC}"
# Try different commands depending on the OS
xdg-open http://localhost:5173 2>/dev/null || \
open http://localhost:5173 2>/dev/null || \
cmd.exe /c start http://localhost:5173 2>/dev/null || \
echo -e "${GREEN}Failed to open. Manually open your browser at: http://localhost:5173${NC}"

echo -e "${GREEN}Application is running. Press Ctrl+C to exit...${NC}"
trap "echo -e '\n${GREEN}Exiting...${NC}'; exit 0" SIGINT
while true; do sleep 1; done