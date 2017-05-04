/*
 * Copyright (C) 2014 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import com.imsweb.geocoder.exception.NotAuthorizedException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Properties;

/**
 * Entry point for Geocoder.
 */
public final class Geocoder {

    private GeocodingService _geocodingService;

    /**
     * Creates a client API root object
     * @param baseUrl base URL for API
     * @param apiKey API key
     */
    private Geocoder(String baseUrl, String apiKey, String proxyHost, Integer proxyPort) {
        if (!baseUrl.endsWith("/"))
            baseUrl += "/";

        if (apiKey == null || apiKey.isEmpty())
            throw new NotAuthorizedException("API Key was not supplied.");

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    HttpUrl url = original.url()
                            .newBuilder()
                            .addQueryParameter("apiKey", apiKey)    // always supply the API key
                            .addQueryParameter("format", "tsv")     // always parse from tsv
                            .addQueryParameter("verbose", "true")   // get verbose reply
                            .addQueryParameter("version", "4.02")   // set API version
                            .build();

                    // add the api key to all requests
                    Request request = original.newBuilder()
                            .header("Accept", "text/plain")
                            .method(original.method(), original.body())
                            .url(url)
                            .build();

                    return chain.proceed(request);
                })
                .proxy(proxyHost != null ? new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)) : null)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .build();

        // create cached service entities
        _geocodingService = retrofit.create(GeocodingService.class);
    }

    /**
     * Main geocoding method
     */
    public List<GeocodeOutput> geocode(GeocodeInput input) throws IOException {
        return GeocodeOutput.toResults(_geocodingService.geocode(input.toQueryParams()).execute().body());
    }

    /**
     * Class to build a connection to API
     */
    public static class Builder {

        // default base URL
        private static final String _GEOCODER_URL = "https://geo.naaccr.org/Services/Geocode/WebService";

        // environment variable for URL and API key
        private static final String _ENV_URL = "GEOCODER_URL";
        private static final String _ENV_API_KEY = "GEOCODER_KEY";
        private static final String _ENV_PROXY_HOST_KEY = "GEOCODER_PROXY_HOST";
        private static final String _ENV_PROXY_PORT_KEY = "GEOCODER_PROXY_PORT";

        private String _url;
        private String _apiKey;
        private String _proxyHost;
        private Integer _proxyPort;

        /**
         * Return a list of user properties from the local .naaccr-geocoder file
         */
        private Properties getProperties() {
            Properties props = new Properties();

            File config = new File(System.getProperty("user.home"), ".naaccr-geocoder");
            if (config.exists()) {
                try (FileInputStream in = new FileInputStream(config)) {
                    props.load(in);
                }
                catch (IOException e) {
                    // error reading
                }
            }

            return props;
        }

        /**
         * Constructor defaults url and key using the key stored in ~/.naaccr-geocoder or the environment variable GEOCODER_KEY
         */
        public Builder() {
            Properties props = getProperties();

            // if the URL is specified (either in properties file or environment), use it, otherwise use the default
            _url = props.getProperty("url");
            if (_url == null)
                _url = System.getenv(_ENV_URL);
            if (_url == null)
                _url = _GEOCODER_URL;

            // if the apikey does not exist, try to read it from the environment
            _apiKey = props.getProperty("apikey");
            if (_apiKey == null)
                _apiKey = System.getenv(_ENV_API_KEY);

            // read proxy information
            _proxyHost = props.getProperty("proxyHost");
            if (_proxyHost == null && System.getenv(_ENV_PROXY_HOST_KEY) != null)
                _proxyHost = System.getenv(_ENV_PROXY_HOST_KEY);

            _proxyPort = props.getProperty("proxyPort") != null ? Integer.valueOf(props.getProperty("proxyPort")) : null;
            if (_proxyPort == null && System.getenv(_ENV_PROXY_PORT_KEY) != null)
                _proxyPort = Integer.valueOf(System.getenv(_ENV_PROXY_PORT_KEY));
        }

        public Builder url(String url) {
            _url = url;
            return this;
        }

        public Builder apiKey(String apiKey) {
            _apiKey = apiKey;
            return this;
        }

        public Builder proxyHost(String host) {
            _proxyHost = host;
            return this;
        }

        public Builder proxyPort(Integer port) {
            _proxyPort = port;
            return this;
        }

        public Geocoder connect() {
            return new Geocoder(_url, _apiKey, _proxyHost, _proxyPort);
        }
    }

}
