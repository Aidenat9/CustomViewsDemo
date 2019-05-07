言归正传，我们小结一下本文的内容：

TypeAdapter(包含JsonSerializer、JsonDeserializer) 是 Gson 解析的银弹，所有 Json 解析的定制化要求都可以通过它来实现。
registerTypeAdapter() 方法需要制定确定的数据类型，如果想支持继承，需要使用 registerTypeHierarchyAdapter() 方法。
如果数据量不大，推荐使用 JsonSerializer 和 JsonDeserializer。
针对整个 Java Bean 的解析接管，可以使用 @JsonAdapter 注解。