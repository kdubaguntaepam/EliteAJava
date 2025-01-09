package com.epam.ai.demo.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/resources/features",
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"},
        glue = "com.epam.ai.demo.stepdefinitions",
        monochrome = true)

public class RunTestsAT extends AbstractTestNGCucumberTests{
}
