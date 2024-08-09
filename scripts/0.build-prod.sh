# doing this is just a little bit extra, just because i want to do it
# find the jar file name and replaces the string ${JAR_FILE} with
# the path to the jar, modifying dockerfile.production
# then build and push to a private registry

#!/bin/bash
cd ../
# Set the path to your Dockerfile
DOCKERFILE_PATH="./dockerfile.production"

# Find the JAR file in the ../target directory
JAR_FILE=$(ls ./target/SpringHello-*.jar 2>/dev/null)

# Check if the JAR file was found
if [ -z "$JAR_FILE" ]; then
    echo "No JAR file found in ../target matching 'SpringHello-*.jar'"
    exit 1
fi

# Check if the Dockerfile exists
if [ ! -f "$DOCKERFILE_PATH" ]; then
    echo "Dockerfile not found at $DOCKERFILE_PATH"
    exit 1
fi

# Use sed to replace the specific string in the Dockerfile
sed -i.bak "s|\${JAR_FILE}|$JAR_FILE|" "$DOCKERFILE_PATH"

# Check if the replacement was successful
if [ $? -eq 0 ]; then
    echo "Successfully replaced '\${JAR_FILE}' with '$JAR_FILE' in $DOCKERFILE_PATH"
else
    echo "Failed to replace the line in $DOCKERFILE_PATH"
fi
#===================================================================

docker build -f dockerfile.production --no-cache -t local.registry.com:32555/springhello:release .

docker push local.registry.com:32555/springhello:release


# Replace back to ${JAR_FILE} in the Dockerfile
sed -i "s|$JAR_FILE|\${JAR_FILE}|" "$DOCKERFILE_PATH"

if [ $? -eq 0 ]; then
    echo "Successfully replaced '$JAR_FILE' back to '\${JAR_FILE}' in $DOCKERFILE_PATH"
else
    echo "Failed to revert the line in $DOCKERFILE_PATH"
fi