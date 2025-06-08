# Usa a imagem oficial do OpenJDK 21
FROM openjdk:21

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia todos os arquivos do projeto para dentro do container
COPY . .

# Concede permissão de execução ao Maven Wrapper
RUN chmod +x mvnw

# Compila a aplicação e gera o JAR, ignorando os testes
RUN ./mvnw clean package -DskipTests

# Define o nome do JAR gerado. Substitua se necessário.
CMD ["java", "-jar", "target/EsterSystem-0.0.1-SNAPSHOT.jar"]

# Expõe a porta padrão da aplicação Spring Boot
EXPOSE 8080