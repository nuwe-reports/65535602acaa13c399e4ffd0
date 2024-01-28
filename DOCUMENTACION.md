#  ACCWE_Hospital

En este proyecto se uso la librería Mockito para realizar los test.

## Testing

### EntityUnitTest.java
En este test se realizó un testing de las entidades(modelos)
en los cuales verificaba si estos podían ser guardados.

### EntityControllerUniTest.java

Dicho test verifica que las rutas creadas con **Mapping** cumplan su función, ya sea Post,
Delete, Get y obtenerTodos/eliminarTodos en cada uno de los controladores disponibles.

### AppointmentController.java @PostMapping("/appointment")

Aquí simplemente me aseguré de obtener todas las citas disponibles para asegurarme
de que estas no estuviesen repetidas, aprovechando la función **overlaps()** hice
uso de su valor booleano para que en caso de que ya existierá una cita en dicho
espació retornará un error como **NOT_ACCEPTABLE**

Posteriormente si todo funciona correctamente guardaría la cita con un **HttpStatus.OK**

### Montando el Proyecto en Docker

* Construimos las imagenes

``` 
docker build -t mysql-database -f Dockerfile.mysql .
```
```
docker build -t my-microservice -f Dockerfile.maven .
```

* Ejecutamos los contenedores
```
docker run -d --name mysql-container -p 3307:3306 mysql-database
```

```
docker run -d --name my-microservice -p 8081:8080 my-microservice
```

## Comprobaciones Docker
Docker info
```
docker ps
```
Network info
```
docker network ls
```
Find the IP
```
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mysql-container
```

