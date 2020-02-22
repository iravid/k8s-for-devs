FROM adoptopenjdk/openjdk11:alpine-slim
RUN apk add --no-cache curl bash
WORKDIR /app
COPY ./target/universal/stage ./
ENTRYPOINT ["/app/bin/k8s-workshop"]
STOPSIGNAL SIGTERM
