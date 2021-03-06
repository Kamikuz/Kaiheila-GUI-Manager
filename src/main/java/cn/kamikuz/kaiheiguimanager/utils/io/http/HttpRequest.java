/*
 * Hello Minecraft! Launcher
 * Copyright (C) 2021  huangyuhui <huanghongxun2008@126.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package cn.kamikuz.kaiheiguimanager.utils.io.http;

import cn.kamikuz.kaiheiguimanager.utils.Pair;
import cn.kamikuz.kaiheiguimanager.utils.function.ExceptionalBiConsumer;
import cn.kamikuz.kaiheiguimanager.utils.gson.JsonUtils;
import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static cn.kamikuz.kaiheiguimanager.utils.io.http.NetworkUtils.createHttpConnection;
import static cn.kamikuz.kaiheiguimanager.utils.io.http.NetworkUtils.resolveConnection;
import static cn.kamikuz.kaiheiguimanager.utils.Lang.mapOf;
import static cn.kamikuz.kaiheiguimanager.utils.gson.JsonUtils.GSON;
import static java.nio.charset.StandardCharsets.UTF_8;

public abstract class HttpRequest {
    protected final String url;
    protected final String method;
    protected final Map<String, String> headers = new HashMap<>();
    protected ExceptionalBiConsumer<URL, Integer, IOException> responseCodeTester;
    protected final Set<Integer> toleratedHttpCodes = new HashSet<>();
    protected boolean ignoreHttpCode;

    private HttpRequest(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public HttpRequest accept(String contentType) {
        return header("Accept", contentType);
    }

    public HttpRequest authorization(String token) {
        return header("Authorization", token);
    }

    public HttpRequest authorization(String tokenType, String tokenString) {
        return authorization(tokenType + " " + tokenString);
    }

    public HttpRequest authorization(Authorization authorization) {
        return authorization(authorization.getTokenType(), authorization.getAccessToken());
    }

    public HttpRequest header(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public HttpRequest ignoreHttpCode() {
        ignoreHttpCode = true;
        return this;
    }

    public abstract String getString() throws IOException;

    public <T> T getJson(Class<T> typeOfT) throws IOException, JsonParseException {
        return JsonUtils.fromNonNullJson(getString(), typeOfT);
    }

    public <T> T getJson(Type type) throws IOException, JsonParseException {
        return JsonUtils.fromNonNullJson(getString(), type);
    }

    public HttpRequest filter(ExceptionalBiConsumer<URL, Integer, IOException> responseCodeTester) {
        this.responseCodeTester = responseCodeTester;
        return this;
    }

    public HttpRequest ignoreHttpErrorCode(int code) {
        toleratedHttpCodes.add(code);
        return this;
    }

    public HttpURLConnection createConnection() throws IOException {
        HttpURLConnection con = createHttpConnection(new URL(url));
        con.setRequestMethod(method);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            con.setRequestProperty(entry.getKey(), entry.getValue());
        }
        return con;
    }

    public static class HttpGetRequest extends HttpRequest {
        public HttpGetRequest(String url) {
            super(url, "GET");
        }

        public String getString() throws IOException {
            HttpURLConnection con = createConnection();
            con = resolveConnection(con);
            return IOUtils.readFullyAsString(con.getInputStream(), StandardCharsets.UTF_8);
        }
    }

    public static final class HttpPostRequest extends HttpRequest {
        private byte[] bytes;

        public HttpPostRequest(String url) {
            super(url, "POST");
        }

        public HttpPostRequest contentType(String contentType) {
            headers.put("Content-Type", contentType);
            return this;
        }

        public HttpPostRequest json(Object payload) throws JsonParseException {
            return string(payload instanceof String ? (String) payload : GSON.toJson(payload), "application/json");
        }

        public HttpPostRequest form(Map<String, String> params) {
            return string(NetworkUtils.withQuery("", params), "application/x-www-form-urlencoded");
        }

        @SafeVarargs
        public final HttpPostRequest form(Pair<String, String>... params) {
            return form(mapOf(params));
        }

        public HttpPostRequest string(String payload, String contentType) {
            bytes = payload.getBytes(UTF_8);
            header("Content-Length", "" + bytes.length);
            contentType(contentType + "; charset=utf-8");
            return this;
        }

        public String getString() throws IOException {
            HttpURLConnection con = createConnection();
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                os.write(bytes);
            }

            if (responseCodeTester != null) {
                responseCodeTester.accept(new URL(url), con.getResponseCode());
            } else {
                if (con.getResponseCode() / 100 != 2) {
                    if (!ignoreHttpCode && !toleratedHttpCodes.contains(con.getResponseCode())) {
                        String data = NetworkUtils.readData(con);
                        throw new ResponseCodeException(new URL(url), con.getResponseCode(), data);
                    }
                }
            }

            return NetworkUtils.readData(con);
        }
    }

    public static HttpGetRequest GET(String url) {
        return new HttpGetRequest(url);
    }

    @SafeVarargs
    public static HttpGetRequest GET(String url, Pair<String, String>... query) {
        return GET(NetworkUtils.withQuery(url, mapOf(query)));
    }

    public static HttpPostRequest POST(String url) throws MalformedURLException {
        return new HttpPostRequest(url);
    }

    public interface Authorization {
        String getTokenType();
        String getAccessToken();
    }
}
