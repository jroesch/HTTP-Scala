  A basic HTTP Client that I wrote while learning scala does basic HTTP GETs 
and POSTs with a nice clean syntax. For example you can simply perform a get 
by:

  ```scala
  HTTP.get("www.google.com")
  ```
  
  and a post with:
  
  ```scala
  HTTP.post("www.somesite.com/post",Map(key -> value, key2 -> value2))
  ```
  finally you can hold on to the HTTPConnection object with:
  
  ```scala
  HTTP("www.google.com")
  ```
  which you can than get/post to:
  ```scala
  val google = HTTP("www.google.com")  
  google.get //=> returns response body
  ```
that is all provides just a bare minimium of features with a really simply 
syntax for HTTP methods.
