package com.pavan.task;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features",
		glue = "com.pavan.task.steps",
		tags = "not @skip",
		plugin = {"pretty", "html:target/cucumber-report.html"},
		publish = true
)

public class TestRunner {
}