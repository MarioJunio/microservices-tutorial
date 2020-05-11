# Docker build
docker build -t photo-app-albums-api:<tag>

# Create Docker Tag
docker tag photo-app-albums-api:<tag> <user>/photo-app-albums-api:<tag>

# Docker push
docker push <user>/photo-app-albums-api:<tag>

# Docker run
docker run -d --network host --name photo-app-albums-api -e "spring.rabbitmq.host=<host>" -e "spring.cloud.config.uri=http://<host>:8012" -e "logging.file.name=/api-logs/albums-api.log" -v /home/ec2-user/api-logs:/api-logs/ marioj95/photo-app-albums-api:<tag>