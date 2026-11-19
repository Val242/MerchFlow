FROM postgres:17-alpine

LABEL org.opencontainers.image.title="MerchFlow Database"

ENV POSTGRES_DB=merchflowdb
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD_FILE=/run/secrets/postgres_password

COPY backup.sql /docker-entrypoint-initdb.d/

EXPOSE 5432