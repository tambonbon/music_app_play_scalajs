import org.scalajs.dom.experimental.{BodyInit, Fetch, Headers, HeadersInit, HttpMethod, ReferrerPolicy, RequestCache, RequestCredentials, RequestInit, RequestMode, RequestRedirect}
import play.api.libs.json.{JsError, JsSuccess, Json, Reads, Writes}

import scala.concurrent.ExecutionContext
import scala.scalajs.js
import scala.scalajs.js.Thenable.Implicits.thenable2future
import scala.util.Success

object FetchJson {
  def fetchPost[A, B](url: String, csrfToken: String,
                      data: A, success: B => Unit, error: JsError => Unit)(implicit
                                                                           writes: Writes[A], reads: Reads[B], ec: ExecutionContext): Unit = {
    val headers = new Headers()
    headers.set("Content-Type", "application/json")
    headers.set("Csrf-Token", csrfToken)
    Fetch.fetch(url, RequestInit(method = HttpMethod.POST,
      headers = headers, body = Json.toJson(data).toString))
      .flatMap(res => res.text())
      .map { data =>
        Json.fromJson[B](Json.parse(data)) match {
          case JsSuccess(b, path) =>
            success(b)
          case e @ JsError(_) =>
            error(e)
        }
      }
  }

  def fetchGet[B](url: String, success: B => Unit, error: JsError => Unit)(implicit
                                                                           reads: Reads[B], ec: ExecutionContext): Unit = {
    Fetch.fetch(url)
      .flatMap(res => res.text())
      .map { data =>
        Json.fromJson[B](Json.parse(data)) match {
          case JsSuccess(b, path) =>
            success(b)
          case e @ JsError(_) =>
            error(e)
        }
      }
  }
}

object RequestInit {

  /**
   * Create a RequestInit with some minimal typesafety on attributes.
   *
   * This method creates as light weight a RequestInit literal as possible
   * from the provided parameters, so as to allow the browser to work through
   * its default setting strategy.
   *
   * It is actually quite difficult to work out the defaults, which parameters
   * work together and which don't. Check the rules here in case of doubt:
   * - [[https://fetch.spec.whatwg.org/#requests ¶3.1.5 Requests]]
   * - [[https://fetch.spec.whatwg.org/#request-class ¶6.3 Request class]], especially the constructor function
   * of the whatwg Fetch spec.
   *
   * //todo: it would help a lot if there were a way to make this fully type safe
   */
  @inline
  def apply(method: js.UndefOr[HttpMethod] = js.undefined,
            headers: js.UndefOr[HeadersInit] = js.undefined,
            body: js.UndefOr[BodyInit] = js.undefined,
            referrer: js.UndefOr[String] = js.undefined, //should be USVString
            referrerPolicy: js.UndefOr[ReferrerPolicy] = js.undefined,
            mode: js.UndefOr[RequestMode] = js.undefined,
            credentials: js.UndefOr[RequestCredentials] = js.undefined,
            requestCache: js.UndefOr[RequestCache] = js.undefined,
            requestRedirect: js.UndefOr[RequestRedirect] = js.undefined,
            integrity: js.UndefOr[String] = js.undefined, //see [[https://w3c
            // .github.io/webappsec-subresource-integrity/ integrity spec]]
            window: js.UndefOr[Null] = js.undefined): RequestInit = {
    val result = js.Dynamic.literal()

    @inline
    def set[T](attribute: String, value: js.UndefOr[T]) = value.foreach { x =>
      result.updateDynamic(attribute)(x.asInstanceOf[js.Any])
    }

    set("method", method)
    set("headers", headers)
    set("body", body)
    set("referrer", referrer)
    set("referrerPolicy", referrerPolicy)
    set("mode", mode)
    set("credentials", credentials)
    set("cache", requestCache)
    set("redirect", requestRedirect)
    set("integrity", integrity)
    set("window", window)

    result.asInstanceOf[RequestInit]
  }
}


