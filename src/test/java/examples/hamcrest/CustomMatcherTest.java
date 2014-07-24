package examples.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CustomMatcherTest {
    @Test
    public void testSquareRootOfMinusOneIsNotANumber() {
        assertThat(Math.sqrt(-1), is(notANumber()));
    }

    @Ignore // example of failing test message
    @Test
    public void testOneIsANumber() {
        assertThat(1.0, is(notANumber()));
    }

    @Factory
    public static <T> Matcher<Double> notANumber() {
        return new IsNotANumber();
    }

    public static class IsNotANumber extends TypeSafeMatcher<Double> {
        @Override
        public boolean matchesSafely(Double number) {
            return number.isNaN();
        }

        public void describeTo(Description description) {
            description.appendText("not a number");
        }
    }
}
