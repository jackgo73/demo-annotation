package com.mjoker73.demo;

import com.mjoker73.demo.annotation.ParameterA;
import com.mjoker73.demo.annotation.ParameterB;
import com.mjoker73.demo.main.SentryService;
import org.junit.Test;

public class MainTest {


    @ParameterA(info = {"i1", "i2"}, description = "i'm sentry", validateWith = PositiveInteger.class)
    String testParam1 = "test1";

    @ParameterB(cnt = 100, description = "i'm watcher")
    Integer testParam2;

    @Test
    public void testCase1() throws Exception {
        MainTest mainTest = new MainTest();

        SentryService sentryService = SentryService.newBuilder().addObject(mainTest).build();
        sentryService.init();
        String d1 = sentryService.getPADescription("testParam1");
        System.out.println(d1);

        sentryService.validateStringField("testParam1");
    }
}
