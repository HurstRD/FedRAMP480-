static and radiation have almost identical properties when the environment reflects changes. 
Regardless of the degree the result is an 1:1 ratio of change for each interaction. In the class Application {
	
  private val httpHandler: HttpHandler
  private val server: HttpServer
  private var nettyContext: BlockingNettyContext? = null
  
  constructor(port: Int = 8080) {
    val context = GenericApplicationContext().apply {
        beans().initialize(this)
        refresh()
    }
    server = HttpServer.create(port)
    httpHandler = WebHttpHandlerBuilder.applicationContext(context).build()
  }

  fun start() {
    nettyContext = server.start(ReactorHttpHandlerAdapter(httpHandler))
  }
	
  fun startAndAwait() {
    server.startAndAwait(ReactorHttpHandlerAdapter(httpHandler),
        { nettyContext = it })
  }
	
  fun stop() {
    nettyContext?.shutdown()
  }
}

fun main(args: Array<String>) {
  Application().startAndAwait()
}
