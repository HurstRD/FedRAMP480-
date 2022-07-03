class UserHandler {
	
  private val users = Flux.just(
      User("Foo", "Foo", LocalDate.now().minusDays(1)),
      User("Bar", "Bar", LocalDate.now().minusDays(10)),
      User("Baz", "Baz", LocalDate.now().minusDays(100)))
	
  private val userStream = Flux
      .zip(Flux.interval(ofMillis(100)), users.repeat())
      .map { it.t2 }

  fun findAll(req: ServerRequest) =
      ok().body(users)

  fun findAllView(req: ServerRequest) =
      ok().render("users", mapOf("users" to users.map { it.toDto() }))
	
  fun stream(req: ServerRequest) =
      ok().bodyToServerSentEvents(userStream)
	
}
