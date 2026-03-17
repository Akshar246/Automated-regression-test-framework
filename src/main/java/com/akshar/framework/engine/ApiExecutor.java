package com.akshar.framework.engine;

import com.akshar.framework.model.TestCase;
import com.akshar.framework.model.TestResult;
import com.akshar.framework.model.TestRun;
import com.akshar.framework.model.TestStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ApiExecutor implements TestExecutor {

    @Override
    public TestResult execute(TestCase testCase, TestRun testRun) {
        long start = System.currentTimeMillis();
        TestResult result = new TestResult();
        result.setTestRun(testRun);

        try {
            String endpoint = testCase.getEndpoint() == null ? null : testCase.getEndpoint().trim();
            if (endpoint == null || endpoint.isBlank()) {
                throw new IllegalArgumentException("API endpoint is missing");
            }
            if (endpoint.contains("{") || endpoint.contains("}")) {
                throw new IllegalArgumentException("API endpoint contains unresolved path placeholders. Use a fully resolved URL.");
            }
            validateAbsoluteHttpUrl(endpoint);

            String method = testCase.getHttpMethod() == null
                    ? "GET"
                    : testCase.getHttpMethod().trim().toUpperCase();

            String payload = testCase.getRequestPayload();
            HttpResponse<String> response;
            switch (method) {
                case "GET":
                case "POST":
                case "PUT":
                case "DELETE":
                case "PATCH":
                    response = executeHttpRequest(method, endpoint, payload);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            }

            String responseBody = response.body();
            result.setActualResult(responseBody);

            String expected = testCase.getExpectedResponseText();

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                if (expected == null || expected.isBlank() || responseBody.contains(expected)) {
                    result.setStatus(TestStatus.PASS);
                } else {
                    result.setStatus(TestStatus.FAIL);
                    result.setErrorMessage("Expected text not found");
                }
            } else {
                result.setStatus(TestStatus.FAIL);
                result.setErrorMessage("Status code: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(TestStatus.FAIL);
            String message = e.getMessage() == null ? "Unexpected API execution error" : e.getMessage();
            result.setErrorMessage(e.getClass().getSimpleName() + ": " + message);
        } finally {
            result.setDurationInMs(System.currentTimeMillis() - start);
        }

        return result;
    }

    private void validateAbsoluteHttpUrl(String endpoint) {
        try {
            URI uri = new URI(endpoint);
            String scheme = uri.getScheme();
            String host = uri.getHost();

            if (scheme == null || host == null || host.isBlank()) {
                throw new IllegalArgumentException("API endpoint must be an absolute URL (e.g. https://api.example.com/path).");
            }
            if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                throw new IllegalArgumentException("API endpoint URL scheme must be http or https.");
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("API endpoint is not a valid URL.");
        }
    }

    private HttpResponse<String> executeHttpRequest(String method, String endpoint, String payload) throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type", "application/json");

        if (("POST".equals(method) || "PUT".equals(method) || "PATCH".equals(method))
                && payload != null && !payload.isBlank()) {
            builder.method(method, HttpRequest.BodyPublishers.ofString(payload));
        } else {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        }

        return httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }
}
