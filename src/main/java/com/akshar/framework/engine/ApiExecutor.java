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

            int statusCode = response.statusCode();
            String responseBody = response.body();
            Integer expectedStatus = testCase.getExpectedStatusCode();
            String expectedText = testCase.getExpectedResponseText();

            if (expectedStatus != null && statusCode != expectedStatus) {
                result.setStatus(TestStatus.FAIL);
                result.setActualResult("API validation failed");
                result.setErrorMessage("Expected status " + expectedStatus + " but got: " + statusCode);
            } else if (expectedText != null && !expectedText.isBlank() && !responseBody.contains(expectedText)) {
                result.setStatus(TestStatus.FAIL);
                result.setActualResult("API validation failed");
                result.setErrorMessage("Expected text not found in response. Expected: " + expectedText);
            } else {
                result.setStatus(TestStatus.PASS);
                result.setActualResult("API validation passed. Status: " + statusCode);
                result.setErrorMessage(null);
            }

        } catch (Exception e) {
            result.setStatus(TestStatus.FAIL);
            result.setActualResult("API execution failed");
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
                throw new IllegalArgumentException("API endpoint must be an absolute URL (example: https://api.example.com/path).");
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