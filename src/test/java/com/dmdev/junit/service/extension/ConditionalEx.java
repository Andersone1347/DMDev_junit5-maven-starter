package com.dmdev.junit.service.extension;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.parallel.Execution;

public class ConditionalEx implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
         return System.getProperty("skip2") != null
                ? ConditionEvaluationResult.disabled("test is skipt")
                : ConditionEvaluationResult.enabled("test is enabled");
    }
}
