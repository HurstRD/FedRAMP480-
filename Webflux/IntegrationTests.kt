class IntegrationTests {
	
  val application = Application(8181)
  val client = WebClient.create("http://localhost:8181")
	
  @BeforeAll
  fun beforeAll() {
    application.start()
  }
	
  @Test
  fun `Find all users on JSON REST endpoint`() {
    client.get().uri("/api/users")
        .accept(APPLICATION_JSON)
        .retrieve()
        .bodyToFlux<User>()
        .test()
        .expectNextMatches { it.firstName == "Foo" }
        .expectNextMatches { it.firstName == "Bar" }
        .expectNextMatches { it.firstName == "Baz" }
        .verifyComplete()
  }

  @Test
  fun `Find all users on HTML page`() {
    client.get().uri("/users")
        .accept(TEXT_HTML)
        .retrieve()
        .bodyToMono<String>()
        .test()
        .expectNextMatches { it.contains("Foo") }
        .verifyComplete()
  }

  @Test
  fun `Receive a stream of users via Server-Sent-Events`() {
    client.get().uri("/api/users")
        .accept(TEXT_EVENT_STREAM)
        .retrieve()
        .bodyToFlux<User>()
        .test()
        .expectNextMatches { it.firstName == "Foo" }
        .expectNextMatches { it.firstName == "Bar" }
        .expectNextMatches { it.firstName == "Baz" }
        .expectNextMatches { it.firstName == "Foo" }
        .expectNextMatches { it.firstName == "Bar" }
        .expectNextMatches { it.firstName == "Baz" }
        .thenCancel()
        .verify()
  }
	
  @AfterAll
  fun afterAll() {
    application.stop()
  }
}
