FROM gradle:7.4.0-jdk17-alpine AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

COPY . .

RUN gradle clean build

FROM openjdk:17.0.2-oracle

ENV ARTIFACT_NAME=jackal-game-enterprise-service-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app

WORKDIR $APP_HOME

COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .

ENTRYPOINT java -Dspring.datasource.url=$DB_URL -Dspring.datasource.username=$DB_USER -Dspring.datasource.password=$DB_PASSWORD -Dlobby_service.url=$LOBBY_SERVICE_API_URL -Dsecurity.oauth.vk.auth_url=$VK_AUTH_URL -Dsecurity.oauth.vk.api_version=$VK_API_VERSION -jar -Dsecurity.oauth.yandex.auth_url=$YANDEX_AUTH_URL -Dsecurity.jwt.secret=$ENTERPRISE_JWT_SECRET -Dsecurity.jwt.expiration-ms=$ENTERPRISE_JWT_TIME_EXPIRATION_MS $ARTIFACT_NAME