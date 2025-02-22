package com.dmdev.junit.service;

import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DirectorySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

public class TestLauncher {
    public static void main(String[] args) {
        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(DiscoverySelectors.selectClass(UserServiceTest.class))
                .filters(TagFilter.excludeTags("log"))
                .build();
        launcher.execute(request,summaryGeneratingListener );

        try (var writer = new PrintWriter(System.out)) {
            summaryGeneratingListener.getSummary().printTo(writer);
        }
    }
}
