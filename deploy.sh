#!/bin/bash

start=$(date +"%s")

ssh ${SERVER_USER}@${SERVER_HOST} -i key.pem -t -t -o StrictHostKeyChecking=no << 'ENDSSH'
cd blogSystem
git pull
docker-compose stop
docker-compose up -d
exit
ENDSSH

if [ $? -eq 0 ]; then
  exit 0
else
  exit 1
fi

end=$(date +"%s")

diff=$(($end - $start))

echo "Deployed in : ${diff}s"
