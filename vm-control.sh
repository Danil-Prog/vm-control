#!/bin/bash

# Array microservices with package backend/*
MODULES=("gateway" "user-service")
ROOT_DIRECTORY="backend"

help() {
  echo """
  --help                    - (-h) print all commands
  --build                   - (-b) build docker containers
  --recreate                - (-r) recreate docker containers
  --recreate 'service'      - recreate docker container by name
  --stop                    - (-s) stop all containers
  --stop 'service'          - stop container by name
  --clean                   - (-c) clean all containers
  --status                  - (-S) print status docker containers
  """
}

build() {
  echo 'Start build docker images...'
  for module in "${MODULES[@]}"; do
      (cd $ROOT_DIRECTORY && gradle "$module":jibDockerBuild -Djib.to.tags=latest)
  done
  echo 'Build last docker images successfully!'
}

recreate() {
  echo 'Recreate docker containers...'
  docker compose up -d --force-recreate
}

stop() {
  echo 'Stop docker containers...'
  docker compose stop
  echo 'Docker containers stopping..'
}

clean() {
  echo 'Clean docker images...'
}

status() {
  echo 'Status docker containers...print'
}

case $1 in
'--help' | '-h')
  help
  ;;
'--build'| '-b')
  build
  ;;
'--recreate'| '-r')
  recreate
  ;;
'--stop'| '-s')
  stop
  ;;
'--clean'| '-c')
  clean
  ;;
'--status'| '-S')
  status
  ;;
  *)
    help
esac

# &>/dev/null &   (без вывода)