docker volume create --driver local \
  --opt type=none \
  --opt device=/home/adrian/repos/SpringHello \
  --opt o=bind app