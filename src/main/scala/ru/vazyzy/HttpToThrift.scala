package ru.vazyzy
import com.twitter.finagle.Service
import com.twitter.finagle.Filter
import com.twitter.finagle.http.RequestBuilder
import com.twitter.finagle.thrift.ThriftClientRequest
import com.twitter.util.Future
import org.jboss.netty.buffer.ChannelBuffers
import org.jboss.netty.handler.codec.http._
import scala.util.Random

class HttpToThrift(host: String) extends Filter[ThriftClientRequest, Array[Byte],
                                                     HttpRequest, HttpResponse] {

  /**
   * Wrap ThriftClient [ThriftClientRequest, Array[Byte] to HttpClient[HttpRequest, HttpResponse]
   * @param thriftRequest Thrift client request
   * @param httpService Http client
   * @return ThriftMessage in Array of Bytes
   */
  def apply(thriftRequest: ThriftClientRequest,
            httpService: Service[HttpRequest, HttpResponse]): Future[Array[Byte]] = {

    val headers: Map[String, String] = Map(
      (HttpHeaders.Names.USER_AGENT, "thrift/scala"),
      (HttpHeaders.Names.CONTENT_LENGTH, thriftRequest.toString))

    val request = RequestBuilder()
      .url(host)
      .addHeaders(headers)
      .buildPost(ChannelBuffers.copiedBuffer(thriftRequest.message))

    val respFuture = httpService(request)
    respFuture.flatMap(httpResp => {
      httpResp.getStatus match {
        case HttpResponseStatus.OK => Future.value(httpResp.getContent.array())
        case _ => Future.exception(new Exception(f"Bad response: $httpResp"))
      }
    })
  }
}
