# https://github.com/kubernetes-sigs/kind/releases
VERSION=https://github.com/kubernetes-sigs/kind/releases/download/v0.23.0/kind-linux-amd64

# amd64
[ $(uname -m) = x86_64 ] && curl -Lo ./kind $VERSION

chmod +x ./kind

sudo mv ./kind /usr/local/bin/kind