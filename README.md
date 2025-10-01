# Random Image Viewer

This app randomly shows 1 our of 15 Duck images.

The images are taken from this API:

[Random-d.uk](https://random-d.uk/api)

To start the app run

```shell
docker-compose up
```

If you get an error, try 

```shell
docker-compose up --build
```

and use this URL to access it in the browser

```shell
http://localhost:8080
```

The two underlying APIs are
* POST localhost:8080/api/fetch-random
* GET localhost:8080/api/latest

The app was build using
* Springboot
* In-memory H2 database
* Thymeleaf
* Lombok

Todo:
- Automated Test