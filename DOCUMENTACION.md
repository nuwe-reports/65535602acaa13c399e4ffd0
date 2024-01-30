#  ACCWE_Hospital

En este proyecto se uso la librería Mockito para realizar los test.

Todas las rutas de la REST API están en **/app/api**.

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
uso de su valor booleano para que en caso de que ya existiera una cita en dicho
espació retornará un error como **NOT_ACCEPTABLE**

Posteriormente si todo funciona correctamente guardaría la cita con un **HttpStatus.OK**

### Montando el Proyecto en Docker


Podemos utilizar el siguiente comando o hacer los pasos que siguen a continuación;

```
docker-compose up
```
Si se prefiere se pueden crear las imágenes

```
docker-compose up --build
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
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' my-microservice


docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mysql-container
```

.

<!-- ## Kubernetes 

La imagen fue creada con Kompose

* Instalación
```
winget install Kubernetes.kompose
```

* Convirtiendo la imagen
```
kompose convert -f docker-compose.yml
```

Este comando generará varios archivos `.yaml` que corresponden a los servicios y despliegues definidos en tu archivo `docker-compose.yml`.



### Montando el Cluster

Utilizando minikube montamos el kluster, pero antes este debe estar instalado.

```
winget install minikube
```

* Lo iniciamos

```
minikube start
```
En caso de error se recomienda usar

```
minikube start --network-plugin=cni --extra-config=kubelet.network-plugin=cni --extra-config=kubelet.pod-cidr=10.0.0.0/16
```

```
minikube stop
minikube delete
```

O simplemente ejecutar el contenedor desde la GUI de **Docker** 

* Verificamos que funciona

```
kubectl get po -A
```

* Finalmente, puedes aplicar los archivos generados a tu cluster de Kubernetes con el comando 

```
kubectl apply -f .
```

* Verificamos que los deployments están bien

```
kubectl get deployments
```

* Verificamos que los servicios funcionan

```
kubectl get services
```

* Verificamos el estado de los pods

```
kubectl get pods
```

* Abrimos los servicios en el navegador

```
minikube service <nombre del servicio>
```

* Exponemos los servicios en el navegador

```
minikube service kubernetes
```
```
kubectl run -it --rm --image=mysql:5.7 --restart=Never mysql-client -- mysql -h db-service -p

minikube service app-service --url
```
 -->