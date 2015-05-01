FROM java:openjdk-7u75-jre
COPY target/staged /gardencaffe/staged
COPY target/start /gardencaffe/
EXPOSE 9010
ENTRYPOINT ["/gardencaffe/start", "-Dhttp.port=9010"]