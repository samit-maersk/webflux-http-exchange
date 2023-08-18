# webflux-http-exchange

The Spring Framework release 6, as well as Spring Boot version 3, enables us to define declarative HTTP services using Java interfaces

The declarative HTTP interface includes annotated methods for HTTP exchanges. We can simply express the remote API details using an annotated Java interface and let Spring generate a proxy that implements this interface and performs the exchanges. This helps reduce the boilerplate code


The available supported annotation are :

`@GetExchange` for HTTP GET requests.

`@PostExchange` for HTTP POST requests.

`@PutExchange` for HTTP PUT requests.

`@PatchExchange` for HTTP PATCH requests.

`@DelectExchange` for HTTP DELETE requests.


Reference Documents :

- [https://www.baeldung.com/spring-6-http-interface](https://www.baeldung.com/spring-6-http-interface)
- [https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-http-interface](https://docs.spring.io/spring-framework/reference/integration/rest-clients.html#rest-http-interface)