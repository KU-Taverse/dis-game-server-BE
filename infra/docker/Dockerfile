FROM openjdk:17-jdk

ARG JAR_FILE=../../build/libs/*.jar

# JAR_FILE을 app.jar로 복사
COPY ${JAR_FILE} app.jar

# 기본값
ENV ACTIVE=prod

# 동적으로 profiles을 전달하기 위해 entrypoint 수정
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=${ACTIVE}"]