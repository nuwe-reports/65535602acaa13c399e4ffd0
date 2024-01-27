#  ACCWE_Hospital

En este proyecto se uso la librería Mockito para realizar los test.

## Testing

### EntityUnitTest.java
En este test se realizó un testing de las entidades(modelos)
en los cuales verificaba si estos podían ser guardados y antes de ser almacenados
en la bdd asegurarme de que todos los campos fueran rellenados correctamente, y 
que por ejemplo en **roomValidationUniqueName()** verificase que no existierá ya
dicho objeto.

### EntityControllerUniTest.java

Dicho test verifica que las rutas creadas con **Mapping** cumplan su función, ya sea Post,
Delete, Get y obtenerTodos/eliminarTodos en cada uno de los controladores disponibles.

### AppointmentController.java @PostMapping("/appointment")

Aquí simplemente me aseguré de obtener todas las citas disponibles para asegurarme
de que estas no estuviesen repetidas, aprovechando la función **overlaps()** hice
uso de su valor booleano para que en caso de que ya existierá una cita en dicho
espació retornará un error como **NOT_ACCEPTABLE**

Posteriormente si todo funciona correctamente guardaría la cita con un **HttpStatus.OK**