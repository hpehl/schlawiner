FROM envoyproxy/envoy:latest
LABEL maintainer="Harald Pehl <hpehl@redhat.com>"

EXPOSE 8080

COPY /envoy.yaml /etc/envoy/envoy.yaml
CMD /usr/local/bin/envoy -c /etc/envoy/envoy.yaml
