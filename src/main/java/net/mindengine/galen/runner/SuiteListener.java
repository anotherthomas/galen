package net.mindengine.galen.runner;

import java.util.List;

import net.mindengine.galen.browser.Browser;
import net.mindengine.galen.suite.GalenSuite;
import net.mindengine.galen.validation.ValidationError;


public interface SuiteListener {

    void onAfterPage(GalenSuiteRunner galenSuiteRunner, GalenPageRunner pageRunner, Browser browser, List<ValidationError> errors);

    void onBeforePage(GalenSuiteRunner galenSuiteRunner, GalenPageRunner pageRunner, Browser browser);

    void onSuiteFinished(GalenSuiteRunner galenSuiteRunner, GalenSuite suite);

    void onSuiteStarted(GalenSuiteRunner galenSuiteRunner, GalenSuite suite);

}