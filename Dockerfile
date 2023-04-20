FROM ubuntu:22.10

COPY . /app
WORKDIR /app
EXPOSE 8080

RUN apt update
RUN apt install openjdk-17-jdk postgresql -y

CMD ["./gradlew", "bootRun"]
