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

## 🔌 External Services

Para desarrollar el **Gateway Aggregator**, utilizaremos una aplicación externa proporcionada por el instructor.
Esta aplicación simula los microservicios con los que nuestro agregador deberá comunicarse.

### 📥 Descarga y ejecución

Descargamos el archivo JAR desde el siguiente enlace:
[Descargar external-services-v2.jar](https://vins-udemy.s3.amazonaws.com/webflux-design-patterns/external-services-v2.jar)

Guardamos el archivo en un directorio de nuestro proyecto para tenerlo disponible durante el desarrollo. Una vez
descargado, ejecutamos la aplicación con el siguiente comando:

````bash
D:\programming\spring\01.udemy\03.vinoth_selvaraj\spring-webflux-patterns-resilience\external-services (main -> origin)
$ java -jar external-services-v2.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.6.4)

2026-10-19 12:21:07.849  INFO 6600 --- [           main] c.v.e.ExternalServicesApplication        : Starting ExternalServicesApplication v0.0.1-SNAPSHOT using Java 25.0.2 on SysEngJava with PID 6600 (D:\programming\spring\01.udemy\03.vinoth_selvaraj\spring-webflux-patterns-resilience\external-services\external-services-v2.jar started by magadiflo in D:\programming\spring\01.udemy\03.vinoth_selvaraj\spring-webflux-patterns-resilience\external-services)
2026-10-19 12:21:07.852  INFO 6600 --- [           main] c.v.e.ExternalServicesApplication        : No active profile set, falling back to 1 default profile: "default"
WARNING: A terminally deprecated method in sun.misc.Unsafe has been called
WARNING: sun.misc.Unsafe::objectFieldOffset has been called by io.netty.util.internal.PlatformDependent0$3 (jar:file:/D:/programming/spring/01.udemy/03.vinoth_selvaraj/spring-webflux-patterns-resilience/external-services/external-services-v2.jar!/BOOT-INF/lib/netty-common-4.1.74.Final.jar!/)
WARNING: Please consider reporting this to the maintainers of class io.netty.util.internal.PlatformDependent0$3
WARNING: sun.misc.Unsafe::objectFieldOffset will be removed in a future release
2026-10-19 12:21:10.021  INFO 6600 --- [           main] o.s.b.web.embedded.netty.NettyWebServer  : Netty started on port 7070
2026-10-19 12:21:10.400  INFO 6600 --- [           main] c.v.e.ExternalServicesApplication        : Started ExternalServicesApplication in 2.946 seconds (JVM running for 3.404) 
````

La aplicación inicia correctamente, vemos en los registros que la aplicación se ha iniciado correctamente y está
escuchando en el puerto `7070`.

**⚙️ Cambiar el puerto del servidor**

Si necesitamos utilizar otro puerto, podemos indicarlo como argumento al ejecutar el JAR:

````bash
java -jar external-services-v2.jar --server.port=6060 
````

En este curso utilizaremos el puerto predeterminado `7070`.

### 📖 Explorar los servicios disponibles

Con la aplicación en ejecución, accedemos desde el navegador a la siguiente dirección:
[http://localhost:7070/swagger-ui/](http://localhost:7070/swagger-ui/)

![04.png](assets/section-02/04.png)

Esta interfaz de Swagger permite consultar los endpoints disponibles y conocer cómo interactuar con los servicios
externos que utilizaremos durante el curso.

En esta sección trabajaremos principalmente con tres servicios:

- **Product Service:** proporciona información de los productos.
- **Review Service:** proporciona las reseñas y calificaciones de los productos.
- **Promotion Service:** proporciona información sobre las promociones disponibles.

**💡 ¿Por qué los tres servicios están en un solo JAR?**

En una arquitectura real de microservicios, cada servicio podría ejecutarse como una aplicación independiente, con su
propio proceso y puerto.

Sin embargo, para simplificar el curso, el instructor ha reunido los tres servicios en un único archivo JAR. Aunque se
ejecutan dentro de la misma aplicación, debemos tratarlos conceptualmente como servicios independientes, ya que cada uno
representa una responsabilidad diferente y dispone de sus propios endpoints.

### 🏗️ Desarrollo del Gateway Aggregator

Nuestro objetivo es desarrollar un nuevo microservicio llamado **Aggregator Service**, que se comunicará con los tres
servicios anteriores para reunir la información de un producto.

![05.png](assets/section-02/05.png)

Por ejemplo, cuando el cliente solicite la información del producto con identificador `81`, el agregador consultará los
servicios necesarios para obtener los datos del producto, sus reseñas y sus promociones.

Posteriormente, reunirá las respuestas en una estructura unificada, representada por el objeto `ProductAggregator`, y
devolverá el resultado al cliente.

De esta manera, el cliente recibirá toda la información necesaria mediante una única solicitud, sin tener que
comunicarse directamente con cada servicio ni encargarse de combinar sus respuestas. Así, podrá concentrarse en la
lógica de presentación.
