FROM postgres:15.0

VOLUME [ "/data" ]

ENV POSTGRES_PASSWORD=shortener-password

EXPOSE 5432