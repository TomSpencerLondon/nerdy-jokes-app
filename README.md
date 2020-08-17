# Nerdy Jokes App

To start the application run `./gradlew clean build bootRun`

To open web page in a browser type `localhost:8080` in URL bar

```
curl localhost:8080/api/v1                              # to receive a joke with default name 'Johnny Foobar' 
curl localhost:8080/api/v1?firstName=Foo&lastName=Bar   # to receive a joke with custom name 'Foo Bar'
```

For building jars, images and running the app, use `Makefile`:

```
make all            # to build everything and push images to the DockerHub (need to be logged-in into docker for push)
make docker_run     # to run all using Docker Compose file from ./docker folder
make docker_clean   # to remove all docker containers, volumes, networks, images
```
