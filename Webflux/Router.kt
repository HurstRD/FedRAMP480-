router {
  accept(TEXT_HTML).nest {
    GET("/") { ok().render("index") }
    GET("/sse") { ok().render("sse") }
    GET("/users", userHandler::findAllView)
  }
  "/api".nest {
    accept(APPLICATION_JSON).nest {
      GET("/users", userHandler::findAll)
    }
    accept(TEXT_EVENT_STREAM).nest {
      GET("/users", userHandler::stream)
    }		
  }
  resources("/**", ClassPathResource("static/"))
}
