# Criar imagem
docker build -t logstash-albums-api:latest .

# Criar tag para Docker Hub
docker tag logstash-albums-api:latest marioj95/logstash-albums-api:latest

# Enviar para Docker Hub
docker push marioj95/logstash-albums-api:latest

# Criar container e executar
docker run -d -p 9600:9600 -v /home/ec2-user/api-logs:/api-logs --name logstash marioj95/logstash-albums-api:latest