
# demo - annotate

annotation's demo , just for fun

1. get field's information in annotation
2. validate field's value using class in annotation

```java

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


```

执行结果

```java

i'm sentry
Validating parameter:testParam1 value:test1 validator:class com.mjoker73.demo.PositiveInteger

java.lang.AssertionError: Parameter testParam1 should be TEST1 (found test1)

```

