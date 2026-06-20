# Food Store

## Trabajo Práctico Integrador - Programación 2

Sistema de gestión de pedidos de comida desarrollado en Java utilizando Programación Orientada a Objetos (POO) y almacenamiento en memoria mediante colecciones.

---

## Alumno

**Carlos Gabriel Chiavarini**

**Tecnicatura Universitaria en Programación**
Universidad Tecnológica Nacional (UTN)

**Materia:** Programación 2
**Año:** 2026

---

## Descripción del Proyecto

Food Store es una aplicación de consola que permite gestionar la información de un negocio de comidas mediante operaciones CRUD (Crear, Listar, Modificar y Eliminar) sobre las entidades principales del sistema.

La aplicación permite administrar:

* Categorías
* Productos
* Usuarios
* Pedidos

Toda la información se almacena en memoria utilizando colecciones de Java (`ArrayList`), sin persistencia en base de datos.

---

## Funcionalidades

### Gestión de Categorías

* Crear categoría
* Listar categorías
* Editar categoría
* Eliminar categoría (baja lógica)

### Gestión de Productos

* Crear producto
* Listar productos
* Editar producto
* Eliminar producto (baja lógica)

### Gestión de Usuarios

* Crear usuario
* Listar usuarios
* Editar usuario
* Eliminar usuario (baja lógica)

### Gestión de Pedidos

* Crear pedidos con múltiples productos
* Calcular automáticamente subtotales y total
* Actualizar estado del pedido
* Actualizar forma de pago
* Eliminar pedido (baja lógica)

---

## Conceptos Aplicados

Durante el desarrollo se aplicaron los siguientes conceptos de Programación Orientada a Objetos:

* Encapsulamiento
* Herencia
* Polimorfismo
* Composición
* Asociaciones entre clases
* Interfaces
* Sobrescritura de métodos
* Manejo de Excepciones
* Colecciones
* Principio Tell Don't Ask
* Separación de responsabilidades mediante capas

---

## Modelo Implementado

### Clase Base

Todas las entidades heredan de una clase abstracta `Base`, la cual centraliza los atributos comunes del sistema:

* id
* eliminado
* createdAt

### Entidades del Dominio

Las siguientes entidades fueron implementadas siguiendo el UML provisto por la cátedra:

* Categoria
* Producto
* Usuario
* Pedido
* DetallePedido

### Enumeraciones

Se utilizaron enumeraciones para representar valores acotados del dominio:

* Rol
* Estado
* FormaPago

### Interface

Se implementó la interfaz:

* Calculable

La misma es utilizada por la clase `Pedido` para calcular el total del pedido respetando lo solicitado en la consigna.

### Capa de Servicios (Diseño Propio)

Si bien la consigna no exige explícitamente una capa de servicios, se decidió incorporar una capa denominada `services` para mejorar la organización y separación de responsabilidades del sistema.

Las clases implementadas fueron:

* CategoriaService
* ProductoService
* UsuarioService
* PedidoService

Estas clases centralizan la lógica de negocio, validaciones y operaciones CRUD, permitiendo que las entidades mantengan únicamente el comportamiento propio de su dominio y que la clase `Main` se limite a la interacción con el usuario.

Esta decisión se tomó siguiendo buenas prácticas de Programación Orientada a Objetos y favoreciendo el principio de responsabilidad única.

### Utilidades (Diseño Propio)

También se incorporó el paquete `utils`, no contemplado explícitamente en la consigna, con el objetivo de reutilizar lógica común de entrada de datos desde consola.

Se implementó:

* InputHelper

Esta clase concentra la validación y lectura de datos ingresados por el usuario, evitando duplicación de código y simplificando la implementación de los distintos menús del sistema.

### Manejo de Excepciones

Se implementaron excepciones personalizadas para representar errores de negocio específicos:

* ValidacionException
* EntidadNoEncontradaException
* MailDuplicadoException
* StockInsuficienteException

Esto permite informar errores de forma clara y mantener un flujo estable de ejecución sin finalizar inesperadamente la aplicación.

---

## Estructura del Proyecto

```text
src/
│
├── entities
│   ├── Base
│   ├── Categoria
│   ├── Producto
│   ├── Usuario
│   ├── Pedido
│   └── DetallePedido
│
├── enums
│   ├── Rol
│   ├── Estado
│   └── FormaPago
│
├── interfaces
│   └── Calculable
│
├── exceptions
│   ├── EntidadNoEncontradaException
│   ├── MailDuplicadoException
│   ├── StockInsuficienteException
│   └── ValidacionException
│
├── services
│   ├── CategoriaService
│   ├── ProductoService
│   ├── UsuarioService
│   └── PedidoService
│
├── utils
│   └── InputHelper
│
└── Main
```

## Requisitos

* Java 21
* NetBeans 25 (o superior)

---

## Cómo Ejecutar el Proyecto

1. Clonar o descargar el repositorio.
2. Abrir el proyecto en NetBeans.
3. Ejecutar la clase `Main`.
4. Utilizar los menús disponibles para gestionar categorías, productos, usuarios y pedidos.

---

## Reglas de Negocio Implementadas

* No se permite crear productos con precio negativo.
* No se permite crear productos con stock negativo.
* No se permite crear pedidos sin usuario.
* No se permite agregar detalles con cantidad menor o igual a cero.
* El mail de usuario debe ser único.
* Todas las eliminaciones se realizan mediante baja lógica.
* No se permite descontar stock inexistente.
* Los pedidos calculan automáticamente su total mediante la interfaz `Calculable`.

---

## Tecnologías Utilizadas

* Java 21
* Programación Orientada a Objetos (POO)
* Colecciones (`ArrayList`)
* NetBeans
* UML

---

## Video Demostrativo

Link al video:

**https://youtu.be/b092i7t7B50**

---

## Documentación PDF

Puede consultarse el archivo PDF incluido en el repositorio.

---

## Autor

**Carlos Gabriel Chiavarini**

Trabajo Práctico Integrador desarrollado para la materia Programación 2 de la Tecnicatura Universitaria en Programación (UTN).
