/*
 * Copyright (C) 2014 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

import com.imsweb.geocoder.exception.NotAuthorizedException;

/**
 * Entry point for Geocoder.
 */
public final class Geocoder {

    private static final String _GEOCODER_FORMAT = "tsv";
    private static final String _GEOCODER_VERSION = "4.04";
    private static final String _GEOCODER_VERBOSE = "true";

    private GeocodingService _geocodingService;

    /**
     * Creates a client API root object
     * @param baseUrl base URL for API
     * @param apiKey API key
     * @param proxyHost URL of proxy host
     * @param proxyPort Proxy port
     */
    private Geocoder(String baseUrl, String apiKey, String proxyHost, Integer proxyPort, Long connectTimeout, Long readTimeout) {
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
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
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
     * @param input input for API call
     * @return results from the API call
     * @throws IOException thrown if any problems occurred making API call including non-200 status codes
     */
    public List<GeocodeOutput> geocode(GeocodeInput input) throws IOException {
        Call<ResponseBody> call = getCall(input);
        return GeocodeOutput.toResults(call);
    }

    Call<ResponseBody> getCall(GeocodeInput input) throws IOException {
        Map<String, String> params = input.toQueryParams();
        params.put("format", _GEOCODER_FORMAT);
        params.put("version", _GEOCODER_VERSION);
        params.put("verbose", _GEOCODER_VERBOSE);
        return _geocodingService.geocode(params);
    }

    /**
     * Class to build a connection to API
     */
    public static class Builder {

        // default base URL
        private static final String _GEOCODER_URL = "https://naaccr-geo-dev.geoservices.tamu.edu/Services/Geocode/WebService";

        // environment variable for URL and API key
        private static final String _ENV_URL = "GEOCODER_URL";
        private static final String _ENV_API_KEY = "GEOCODER_KEY";
        private static final String _ENV_PROXY_HOST_KEY = "GEOCODER_PROXY_HOST";
        private static final String _ENV_PROXY_PORT_KEY = "GEOCODER_PROXY_PORT";

        private String _url;
        private String _apiKey;
        private String _proxyHost;
        private Integer _proxyPort;
        private Long _connectTimeout = 10L;
        private Long _readTimeout = 10L;

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

        public Builder connectTimeout(long seconds) {
            _connectTimeout = seconds;
            return this;
        }

        public Builder readTimeout(long seconds) {
            _readTimeout = seconds;
            return this;
        }

        public Geocoder connect() {
            return new Geocoder(_url, _apiKey, _proxyHost, _proxyPort, _connectTimeout, _readTimeout);
        }
    }

}
