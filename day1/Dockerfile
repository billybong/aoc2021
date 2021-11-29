FROM ghcr.io/graalvm/graalvm-ce:latest
WORKDIR /opt/graalvm
RUN gu install native-image
COPY App.java App.java
COPY input.txt input.txt
RUN javac App.java && native-image App
CMD ["./app"]