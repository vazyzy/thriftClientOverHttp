package ru.vazyzy

import com.twitter.finagle.builder.ClientBuilder
import com.twitter.finagle.http.Http
import com.twitter.util.Future
import org.apache.thrift.protocol.TJSONProtocol
import ru.vazyzy.HttpToThrift

/**
 * Client for Lead-Profile-Service
 */
object Client {
  def build(host: String, port: String): Future[FinagledClient] = {
      val httpClient = ClientBuilder()
        .codec(Http())
        .hosts("host:port")
        .hostConnectionLimit(1)
        .retries(2)
        .build()

      val thriftFilter = new HttpToThrift(host)
      val httpService = thriftFilter andThen httpClient
      Future.value(new FinagledClient(httpService, new TJSONProtocol.Factory()))
  }
}
