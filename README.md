# [Spring WebFlux: Microservices Patterns & Advanced Resilience](https://www.udemy.com/course/spring-webflux-patterns/)

# 📚 Sección 2: API Composition: Gateway Aggregator Pattern

## 🔎 Gateway Aggregator Pattern - Intro

El **Gateway Aggregator Pattern** es un patrón de integración que permite al cliente obtener información de varios
microservicios mediante una única solicitud. Un servicio agregador consulta los servicios necesarios, combina sus
respuestas y devuelve un resultado unificado al cliente.

#### 💡 Diferencia entre API Gateway y Gateway Aggregator

Aunque ambos actúan como intermediarios entre el cliente y los microservicios, tienen responsabilidades diferentes:

- **API Gateway:** funciona como punto de entrada y enruta las solicitudes hacia los microservicios correspondientes.
- **Gateway Aggregator:** consulta varios microservicios, combina sus respuestas y devuelve un único resultado al
  cliente.

Ambos patrones pueden convivir en una misma arquitectura. Por ejemplo, el API Gateway puede enrutar una solicitud al
agregador, y este último consultar los microservicios necesarios.

Este patrón también se relaciona con el **Backend for Frontend (BFF)**, que adapta las respuestas a las necesidades de
un cliente específico. Sin embargo, no son equivalentes: el Gateway Aggregator se centra en combinar información de
varios servicios, mientras que el BFF se centra en ofrecer un backend adaptado a un tipo de cliente.

### ⚠️ Planteamiento del problema

Consideremos una aplicación en la que el navegador necesita obtener información de cuatro microservicios diferentes.

![01.png](assets/section-02/01.png)

En este escenario, el cliente realiza una solicitud independiente a cada microservicio para obtener toda la información
necesaria para construir la página.

Este enfoque presenta varios problemas:

- **Múltiples llamadas de red:** el cliente debe realizar varias solicitudes para obtener toda la información necesaria.
- **Límite de solicitudes simultáneas:** los navegadores pueden limitar la cantidad de conexiones concurrentes hacia un
  mismo origen. Si se alcanza ese límite, algunas solicitudes deberán esperar a que se liberen conexiones.
- **Mayor latencia:** si el cliente está en Europa y los servidores en Estados Unidos, las múltiples solicitudes pueden
  aumentar el tiempo necesario para reunir toda la información.
- **Mayor complejidad en el cliente:** si se incorpora un nuevo microservicio, el cliente podría necesitar
  modificaciones para consultar y procesar sus datos.

### ⚙️ Solución: Gateway Aggregator Pattern

Aquí es donde entra en juego el **Gateway Aggregator Pattern**.

![02.png](assets/section-02/02.png)

El agregador es un servicio backend que recibe una solicitud del cliente y consulta los microservicios necesarios para
obtener la información requerida. Después, combina las respuestas y devuelve un único resultado al cliente.

De esta manera, el agregador **oculta la complejidad de la comunicación con los distintos microservicios**, permitiendo
que el cliente se concentre principalmente en la lógica de presentación.

Además, si se incorpora un nuevo microservicio, la lógica necesaria para obtener e integrar su información puede
centralizarse en el agregador, evitando que el cliente tenga que gestionar directamente esa nueva dependencia.

### 🛒 Ejemplo práctico: página de detalles de un producto

Consideremos una página de detalles de un producto en Amazon. En ella podemos encontrar información como:

- Datos del producto.
- Calificaciones y reseñas.
- Promociones y ofertas de precios.

Esta información puede provenir de diferentes microservicios:

- **Product Service:** proporciona la información del producto.
- **Review Service:** proporciona las reseñas y calificaciones.
- **Promotion Service:** proporciona las promociones disponibles.

Aunque la página presenta toda esta información de manera unificada, los datos pueden provenir de servicios
independientes.

El `Gateway Aggregator` permite reunir esa información y devolverla al cliente en una única respuesta.

### 🏗️ Objetivo de la implementación

Consideremos una organización en la que diferentes equipos desarrollan y mantienen estos microservicios.

![03.png](assets/section-02/03.png)

Cada equipo es responsable de su propio servicio: uno gestiona los productos, otro las reseñas y otro las promociones.
Nuestra tarea consiste en desarrollar el agregador que se comunicará con estos servicios y reunirá la información que
necesita el cliente.

**El objetivo de esta sección es implementar el Gateway Aggregator Pattern utilizando Spring WebFlux.**

