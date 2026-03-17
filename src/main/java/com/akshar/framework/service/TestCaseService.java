package com.akshar.framework.service;

import com.akshar.framework.exception.ResourceNotFoundException;
import com.akshar.framework.model.TestCase;
import com.akshar.framework.repository.TestCaseRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.List;

@Service
public class TestCaseService {

    private static final Set<String> SUPPORTED_HTTP_METHODS = Set.of("GET", "POST", "PUT", "DELETE", "PATCH");

    private final TestCaseRepository testCaseRepository;

    public TestCaseService(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    public TestCase createTestCase(TestCase testCase) {
        validateTestCase(testCase);
        return testCaseRepository.save(testCase);
    }

    public TestCase updateTestCase(Long id, TestCase updatedTestCase) {
        TestCase existing = getTestCaseById(id);

        existing.setTestName(updatedTestCase.getTestName());
        existing.setDescription(updatedTestCase.getDescription());
        existing.setTestType(updatedTestCase.getTestType());
        existing.setStatus(updatedTestCase.getStatus());
        existing.setActive(updatedTestCase.getActive());
        existing.setClassName(updatedTestCase.getClassName());
        existing.setMethodName(updatedTestCase.getMethodName());
        existing.setEndpoint(updatedTestCase.getEndpoint());
        existing.setHttpMethod(updatedTestCase.getHttpMethod());
        existing.setRequestPayload(updatedTestCase.getRequestPayload());
        existing.setExpectedResponseText(updatedTestCase.getExpectedResponseText());
        existing.setUiTargetUrl(updatedTestCase.getUiTargetUrl());
        existing.setExpectedPageTitle(updatedTestCase.getExpectedPageTitle());
        existing.setTestSuite(updatedTestCase.getTestSuite());

        validateTestCase(existing);
        return testCaseRepository.save(existing);
    }

    public TestCase getTestCaseById(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test case not found with id: " + id));
    }

    public List<TestCase> getAllTestCases() {
        return testCaseRepository.findAll();
    }

    public List<TestCase> getAllActiveTestCases() {
        return testCaseRepository.findByActiveTrue();
    }

    public void deleteTestCase(Long id) {
        TestCase testCase = getTestCaseById(id);
        testCaseRepository.delete(testCase);
    }

    private void validateTestCase(TestCase testCase) {
        if (testCase.getTestName() == null || testCase.getTestName().isBlank()) {
            throw new IllegalArgumentException("Test name is required.");
        }

        if (testCase.getTestType() == null) {
            throw new IllegalArgumentException("Test type is required.");
        }

        if (testCase.getTestType().name().equals("UI")) {
            if (testCase.getUiTargetUrl() == null || testCase.getUiTargetUrl().isBlank()) {
                throw new IllegalArgumentException("UI target URL is required for UI tests.");
            }
        }

        if (testCase.getTestType().name().equals("API")) {
            if (testCase.getEndpoint() == null || testCase.getEndpoint().isBlank()) {
                throw new IllegalArgumentException("Endpoint is required for API tests.");
            }

            String endpoint = testCase.getEndpoint().trim();
            if (endpoint.contains("{") || endpoint.contains("}")) {
                throw new IllegalArgumentException("Endpoint cannot contain unresolved path placeholders. Use a fully resolved URL.");
            }
            validateAbsoluteHttpUrl(endpoint);

            if (testCase.getHttpMethod() != null && !testCase.getHttpMethod().isBlank()) {
                String method = testCase.getHttpMethod().trim().toUpperCase();
                if (!SUPPORTED_HTTP_METHODS.contains(method)) {
                    throw new IllegalArgumentException("Unsupported HTTP method: " + method);
                }
            }
        }
    }

    private void validateAbsoluteHttpUrl(String endpoint) {
        try {
            URI uri = new URI(endpoint);
            String scheme = uri.getScheme();
            String host = uri.getHost();

            if (scheme == null || host == null || host.isBlank()) {
                throw new IllegalArgumentException("Endpoint must be an absolute URL (e.g. https://api.example.com/path).");
            }
            if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
                throw new IllegalArgumentException("Endpoint URL scheme must be http or https.");
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Endpoint is not a valid URL.");
        }
    }
}
