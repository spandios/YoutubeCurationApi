./gradlew build
aws ecr get-login-password | docker login --username AWS --password-stdin 257342555568.dkr.ecr.ap-northeast-2.amazonaws.com/ycapi
docker buildx build --platform linux/amd64 -t 257342555568.dkr.ecr.ap-northeast-2.amazonaws.com/ycapi --push .
