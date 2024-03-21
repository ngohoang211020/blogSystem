package com.blogsystem.exception.handler;

import com.blogsystem.exception.RestClientErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RestClientErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().series() != HttpStatus.Series.SUCCESSFUL;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus statusCode = response.getStatusCode();
        String responseBody = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        if (statusCode.series() != HttpStatus.Series.SUCCESSFUL) {
            // Handle server error
            throw new RestClientErrorException("Cannot connect to 3rd API: " + statusCode + ", Response Body:" + responseBody);
        }
    }
}
