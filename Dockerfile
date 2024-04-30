# Usa uma imagem base do OpenJDK 21 para Java
FROM openjdk:21

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR da sua aplicação para o contêiner
COPY target/desafio_rpe-0.0.1-SNAPSHOT.jar /app

# Expõe a porta em que a aplicação Spring Boot está sendo executada
EXPOSE 8080

# Comando para executar a aplicação Spring Boot quando o contêiner for iniciado
CMD ["java", "-jar", "desafio_rpe-0.0.1-SNAPSHOT.jar"]
