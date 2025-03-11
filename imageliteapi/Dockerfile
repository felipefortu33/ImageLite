# Usa uma imagem base do Maven com OpenJDK 17 para construir a aplicação.
FROM maven:3.8.5-openjdk-17 as builder

# Define o diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copia todos os arquivos do projeto para o diretório de trabalho.
COPY . .

# Executa o comando Maven para limpar e empacotar a aplicação, ignorando os testes.
# Adiciona timeouts de conexão e leitura para o Maven.
RUN mvn clean package -DskipTests -Dmaven.wagon.http.connectionTimeout=60000 -Dmaven.wagon.http.readTimeout=60000

# Usa uma imagem base do Amazon Corretto 17 para a execução da aplicação.
FROM amazoncorretto:17

# Define o diretório de trabalho dentro do contêiner.
WORKDIR /app

# Copia o arquivo JAR gerado do estágio anterior para o diretório de trabalho.
COPY --from=builder ./app/target/*.jar /app/application.jar

# Expõe a porta 8080 para permitir o tráfego de entrada na aplicação.
EXPOSE 8080

# Define uma variável de ambiente para o host do PostgreSQL.
ENV POSTGRES_HOST=db

# Adiciona um comando de echo para imprimir o valor da variável de ambiente POSTGRES_HOST.
RUN echo "the env var POSTGRES_HOST value is $POSTGRES_HOST"

# Define o ponto de entrada do contêiner, executando a aplicação com o perfil de produção ativo.
ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-jar", "application.jar"]
