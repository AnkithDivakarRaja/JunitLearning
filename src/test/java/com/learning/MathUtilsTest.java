package com.learning;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

//This is the default behavior, one new test class is created before each test method is executed
//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
//This creates a test class once and the same test instance is used to execute all the tests.
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MathUtilsTest {

    private MathUtils testMethod;
    private TestInfo testInfo;
    private TestReporter testReporter;

    @BeforeAll
    //Before all only executes against static methods since it runs this method even before the MathUtilsTest class instance
    //is created.
    static void preSetUp () {
        System.out.println("Pre set up");
    }

    @BeforeEach
    void setUp (TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        testMethod = new MathUtils();

        testReporter.publishEntry("Running " + testInfo.getTestMethod() + " with tags " + testInfo.getTags());
    }

    @AfterEach
    void cleanUp () {
        testReporter.publishEntry("Clean up");
    }

    @Nested
    @Tag("add")
    class AddTest {
        @Test
        @DisplayName("Test add +")
        void testAddPositive () {
            int result = testMethod.add(1, 3);
            assertEquals(4, result);
        }

        @Test
        @DisplayName("Test add -")
        void testAddNegative () {
            int result = testMethod.add(1, 3);
            //Use message supplier when building expensive messages
            assertEquals(4, result, () -> "Failed with result: " + result);
        }
    }

    @Test
    @Tag("multiply")
    @DisplayName("Multiply tests")
    void multiplyTest() {
        assertAll(
                () -> assertEquals(4, testMethod.multiply(1, 4)),
                () -> assertEquals(0, testMethod.multiply(0, 50)),
                () -> assertEquals(4, testMethod.multiply(2, 2))
        );
    }

    @Tag("multiply")
    @RepeatedTest(3)
    void repeatedMultiplication(RepetitionInfo repetitionInfo) {
        testReporter.publishEntry("Execution number" + repetitionInfo.getCurrentRepetition() +
                " of " + repetitionInfo.getTotalRepetitions());
        assertEquals(4, testMethod.multiply(2, 2));
    }

    @Test
    @Disabled
    @DisplayName("In progress test")
    void testInProgress() {
        fail("This test is still in progress");
    }
}