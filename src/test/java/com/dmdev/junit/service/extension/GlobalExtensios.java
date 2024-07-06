package com.dmdev.junit.service.extension;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class GlobalExtensios implements BeforeAllCallback, AfterTestExecutionCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        System.out.println("GlobalExtensios beforeAll");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        System.out.println("GlobalExtensios afterTestExecution");
    }
}
