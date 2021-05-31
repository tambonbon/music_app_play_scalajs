import org.scalajs.dom.experimental.{BodyInit, HeadersInit, HttpMethod, ReferrerPolicy, RequestCache, RequestCredentials, RequestInit, RequestMode, RequestRedirect}

import scala.scalajs.js

object RequestInit {
  /**
   * Implementation for trait RequestInit
     */
  @inline
  @deprecated("use `new RequestInit {}` instead", "0.9.7")
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
    set("requestCache", requestCache)
    set("requestRedirect", requestRedirect)
    set("cache", requestCache)
    set("redirect", requestRedirect)
    set("integrity", integrity)
    set("window", window)

    result.asInstanceOf[RequestInit]
  }
}