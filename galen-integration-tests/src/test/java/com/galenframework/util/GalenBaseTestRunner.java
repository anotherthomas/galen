/*******************************************************************************
* Copyright 2015 Ivan Shubin http://galenframework.com
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*   http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
******************************************************************************/
package com.galenframework.util;

import static java.util.Arrays.asList;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.galenframework.GalenMain;
import com.galenframework.config.GalenProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;

public abstract class GalenBaseTestRunner {

    private static final Logger LOG = LoggerFactory.getLogger("GalenBaseLayoutTests");


    public static final TestDevice MOBILE = new TestDevice("mobile", new Dimension(450, 800), asList("mobile"));

    public static final TestDevice TABLET = new TestDevice("tablet", new Dimension(600, 800), asList("tablet"));

    public static final TestDevice DESKTOP = new TestDevice("desktop", new Dimension(1100, 800), asList("desktop"));

    public void verifyPage(String uri, TestDevice device, String specPath, File reportFolder) throws Exception {
        String completeUrl = "file://" + getAbsolutePathToResource(uri);
        String defaultBrowser = System.getProperty(GalenProperty.GALEN_DEFAULT_BROWSER.name(), "firefox");

        LOG.info("Opening url " + completeUrl + " in browser " + defaultBrowser);

        new GalenMain().execute(new String[]{
                "check",
                getAbsolutePathToResource(specPath),
                "--url", completeUrl,
                "--include", commaSeparated(device.getTags()),
                "--htmlreport", reportFolder.getAbsolutePath(),
                "--size", convertScreenSize(device.getScreenSize())
        });
    }

    private String convertScreenSize(Dimension screenSize) {
        return (int)screenSize.getWidth() + "x" + (int)screenSize.getHeight();
    }

    protected String commaSeparated(List<String> tags) {
        StringBuilder builder = new StringBuilder();
        boolean comma = false;
        for (String tag : tags) {
            if (comma) {
                builder.append(",");
            }
            comma = true;
            builder.append(tag);
        }
        return builder.toString();
    }

    protected String getAbsolutePathToResource(String uri) {
        return new File(getClass().getResource(uri).getFile()).getAbsolutePath();
    }

    @DataProvider
    public Object[][] devices() {
        return new Object[][] {
              { MOBILE },
              { TABLET },
              { DESKTOP }
        };
    }

    public static class TestDevice {

        private final String name;
        private final Dimension screenSize;
        private final List<String> tags;

        public TestDevice(String name, Dimension screenSize, List<String> tags) {
            this.name = name;
            this.screenSize = screenSize;
            this.tags = tags;
        }

        public String getName() {
            return name;
        }

        public Dimension getScreenSize() {
            return screenSize;
        }

        public List<String> getTags() {
            return tags;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("TestDevice [");
            if (name != null) {
                builder.append("name=");
                builder.append(name);
            }
            builder.append("]");
            return builder.toString();
        }
    }
}