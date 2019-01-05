package com.huantt.commons.shortlink

import com.huantt.commons.httpclient.UrlBuilder
import okhttp3.HttpUrl
import okhttp3.Response
import sun.net.www.http.HttpClient

/**
 * @author huantt on 11/8/18
 */
class BitlyLinkShortener {

    static final String SHORTEN_ENDPOINT = "https://api-ssl.bitly.com/v3/shorten"

    static String generateLink(String token, String targetLink, String shortLinkDomain) {
        HttpUrl requestUrl = new UrlBuilder(SHORTEN_ENDPOINT)
                .addQueryParameter(QUERY_PARAMS.TARGET_LINK, targetLink)
                .addQueryParameter(QUERY_PARAMS.ACCESS_TOKEN, token)
                .addQueryParameter(QUERY_PARAMS.DOMAIN, shortLinkDomain)
                .build()

        Response response = HttpClient.get(requestUrl, null)
        JSONObject responseBody = new JSONObject(response.body().string())
        if (response.code() == 200 && response && responseBody.getString("status_txt") == "OK") {
            return responseBody
                    .getJSONObject("data")
                    .getString("url")
        } else {
            throw new RuntimeException("Error when generating shortlink. Response body:${responseBody}")
        }
    }

    static class QUERY_PARAMS {
        static final String TARGET_LINK = "longUrl"
        static final String ACCESS_TOKEN = "access_token"
        static final String DOMAIN = "domain"
    }

    static class SHORTLINK_DOMAIN {
        static final String BIT_LY = "bit.ly"
        static final String J_MP = "j.mp"
        static final String BITLY_COM = "bitly.com"
    }
}
