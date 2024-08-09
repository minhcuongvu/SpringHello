# reminder: change filter
DOCKER_CONTAINER_CURRENT=$(docker ps -a --filter "name=wslkindmultinodes-worker" -q | head -n 1)
docker exec -it $DOCKER_CONTAINER_CURRENT /bin/sh