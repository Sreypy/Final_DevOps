FROM eclipse-temurin:21-jdk-jammy

# Install NGINX, OpenSSH Server, Git, and Maven
RUN apt-get update && apt-get install -y \
    nginx \
    openssh-server \
    git \
    maven \
    && rm -rf /var/lib/apt/lists/*

# Configure SSH on port 2222
RUN mkdir /var/run/sshd
RUN echo 'root:Hello@123' | chpasswd
RUN sed -i 's/#Port 22/Port 2222/' /etc/ssh/sshd_config
RUN sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config

# NGINX configuration to proxy to Spring Boot
COPY nginx.conf /etc/nginx/sites-available/default

# ✅ Write entrypoint.sh directly in Dockerfile (avoids Windows line ending issue)
RUN printf '#!/bin/bash\nservice ssh start\nservice nginx start\ncd /app\nmvn clean package -DskipTests\njava -jar target/*.jar\n' > /entrypoint.sh \
    && chmod +x /entrypoint.sh

WORKDIR /app
COPY . /app

EXPOSE 8085 2222

ENTRYPOINT ["/entrypoint.sh"]