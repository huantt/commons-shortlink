package com.huantt.commons.shortlink

import com.huantt.commons.httpclient.UrlBuilder
import okhttp3.HttpUrl
import okhttp3.Response
import sun.net.www.http.HttpClient

/**
 * @author huantt on 11/9/18
 */
class TinyLinkShortener {

    static final String SHORTEN_ENDPOINT = "http://tinyurl.com/api-create.php"
    static final String TARGET_LINK_PARAM = "url"

    static String generateLink(String targetLink) {
        HttpUrl requestUrl = new UrlBuilder(SHORTEN_ENDPOINT)
                .addQueryParameter(TARGET_LINK_PARAM, targetLink)
                .build()

        Response response = HttpClient.get(requestUrl, null)
        String responseBody = response.body().string()
        if (response.code() == 200) {
            return responseBody
        } else {
            throw new RuntimeException("Error when generating shortlink. Response body:${responseBody}")
        }
    }

}
