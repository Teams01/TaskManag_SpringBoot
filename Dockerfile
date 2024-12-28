# Étape 1 : Image de base
FROM openjdk:17-jdk-slim

# Étape 2 : Créer un répertoire de travail
WORKDIR /app

# Étape 3 : Copier le fichier JAR généré dans le conteneur
COPY target/spring-security-0.0.1-SNAPSHOT.jar app.jar

# Étape 4 : Exposer le port de l'application
EXPOSE 8080

# Étape 5 : Définir le point d'entrée pour démarrer le microservice
ENTRYPOINT ["java", "-jar", "app.jar"]
