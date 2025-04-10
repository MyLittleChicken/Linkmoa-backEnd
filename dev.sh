#!/bin/bash

echo "[dev.sh] Starting deployment..."

./gradlew clean build

sudo docker compose --env-file .env.dev up -d --build

echo "[dev.sh] deploy complete!"