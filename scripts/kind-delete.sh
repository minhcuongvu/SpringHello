#!/bin/bash
kind delete cluster --name wslkindmultinodes
sudo kill -9 $(sudo lsof -i :8001 | grep LISTEN | awk '{print $2}')