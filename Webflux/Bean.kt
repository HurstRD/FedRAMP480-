beans {
  bean<UserHandler>()
  bean<Routes>()
  bean<WebHandler>("webHandler") {
    RouterFunctions.toWebHandler(
      ref<Routes>().router(),
      HandlerStrategies.builder().viewResolver(ref()).build()
    )
  }
  bean("messageSource") {
    ReloadableResourceBundleMessageSource().apply {
      setBasename("messages")
      setDefaultEncoding("UTF-8")
    }
  }
  bean {
    val prefix = "classpath:/templates/"
    val suffix = ".mustache"
    val loader = MustacheResourceTemplateLoader(prefix, suffix)
    MustacheViewResolver(Mustache.compiler().withLoader(loader)).apply {
      setPrefix(prefix)
      setSuffix(suffix)
    }
  }
  profile("foo") {
    bean<Foo>()
  }
}
