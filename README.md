# Nerdy Jokes App

To start the application run `./gradlew clean build bootRun`

To open web page in a browser type `localhost:8080` in URL bar

```
curl localhost:8080/api/v1                              # to receive a joke with default name 'Johnny Foobar' 
curl localhost:8080/api/v1?firstName=Foo&lastName=Bar   # to receive a joke with custom name 'Foo Bar'
```

To build a Docker image with [BuildKit](https://docs.docker.com/develop/develop-images/build_enhancements/)

```text
DOCKER_BUILDKIT=1 docker image build -t ifqthenp/nerdy-jokes-app:latest .
```
