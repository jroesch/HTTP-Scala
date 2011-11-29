/* HTTP.scala
  A simple HTTP client in Scala, suporting GET and POST 
  
  Jared Roesch, 2011 */
  
//public interface to the HTTP methods, will return a HTTPConn, or can perform get/posts
object HTTP {
  def apply(url: String): HTTPConnection = HTTPConnection(url)
  def get(url: String) = HTTPConnection(url).get
  def post(url: String, parameters: Map[String, String]) =
    HTTPConnection(url).post(parameters)
}

// allows factory like behavior with HTTPConnection
object HTTPConnection {
	def apply(url: String) = new HTTPConnection(url)
}

//implements the HTTPConnection class with a connection field and appropriate instance methods 
class HTTPConnection(url: String) {
	
	import collection.JavaConversions._
  import java.net._
	import java.io._
	import io.Source.fromInputStream
	//constructor action 
	private var connection = (new URL(url)).openConnection()

  var cookies = Map[String, String]()
  
  def storeCookies = {
		connection.getHeaderFields.lift("Set-Cookie") match {
			case Some(cookieList) => cookieList foreach { 
				c => val (name,value) = c span { _ != '='} 
				cookies += name -> (value drop 1)
			}
			case None => 
		}
  }
  
  def loadCookies() =
  	for ((name, value) <- cookies) connection.setRequestProperty("Cookie", name + "=" + value)

  def post(parameters: Map[String, String]) = {
		//try {
			//connection.setRequestProperty("User-Agent")
			//connection.setConnectTimeout()

			loadCookies

			connection.setDoOutput(true)
			connection.connect

			val postStream = new OutputStreamWriter(connection.getOutputStream())
			postStream.write(encodePostParameters(parameters).mkString("&"))
			postStream.flush
			postStream.close

			storeCookies
			fromInputStream(connection.getInputStream)
		//} catch {
			//case e: UnknownHostException => println(e.message + ":Could not connect to host.")
		//}
  }

  private def encodePostParameters(data: Map[String, String]) = 
 		for ((name,value) <- data) 
			yield URLEncoder.encode(name) + "=" + URLEncoder.encode(value)

  def get = {
		//connection.setRequestProperty()
		//connection.setConnectTimeout()
		loadCookies
		connection.connect
		storeCookies
		fromInputStream(connection.getInputStream)
	} 

}
 



