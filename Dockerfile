FROM maven:3-jdk-8-alpine

WORKDIR /usr/src/app

COPY . /usr/src/app
RUN mvn package

EXPOSE 5000
CMD [ "sh", "-c", "mvn -Dserver.port=5000 spring-boot:run" ]
