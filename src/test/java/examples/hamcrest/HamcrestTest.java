package examples.hamcrest;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * HamcrestTest contains examples of using Hamcrest matchers with assertThat.
 * <p>
 * For more examples, see https://github.com/hamcrest/JavaHamcrest unit tests under hamcrest-library/src/main/test.
 */
public class HamcrestTest {

    @Test
    public void isSugar() {
        assertThat("is is sugar", 1 + 2, is(equalTo(3))); // is can be removed; only there for readability
    }

    @Test
    public void anythingAlwaysMatches() {
        assertThat("foo", anything());
        assertThat(null, anything());
        assertThat(new Object(), anything());

        assertThat("foo", anything("description"));
    }

    @Test
    public void typeMatchers() {
        assertThat("foo", is(any(String.class)));
        assertThat("foo", is(instanceOf(String.class)));
        assertThat("foo", isA(String.class)); // reads best

        assertThat(List.class, typeCompatibleWith(List.class));
        assertThat(List.class, typeCompatibleWith(Collection.class));
        assertThat(LinkedList.class, typeCompatibleWith(List.class));
    }

    @Test
    public void logicalOperators() {
        assertThat("&& together multiple matchers",
                "foo", allOf(notNullValue(), isA(String.class), equalTo("foo")));
        assertThat("|| together multiple matchers",
                "foo", anyOf(startsWith("f"), startsWith("b"), startsWith("q")));
        assertThat("not operator",
                "foo", is(not(equalTo("bar"))));

        assertThat("foo", both(startsWith("f")).and(endsWith("o")));
        assertThat("foo", either(startsWith("f")).or(startsWith("b")));
    }

    @Test
    public void equality() {
        assertThat("foo", is(equalTo("foo")));

        assertThat("is can be used in place of equalTo", 1 + 2, is(3));

        assertThat(2 + 2, not(5));
        assertThat(2 + 2, not(lessThan(4)));

        assertThat("foo", comparesEqualTo("foo"));
    }

    @Test
    public void identity() {
        Object o1 = new Object();
        Object o2 = new Object();
        assertThat(o1, sameInstance(o1));
        assertThat(o1, not(sameInstance(o2)));
    }

    @Test
    public void testNull() {
        String s1 = null;
        String s2 = "foo";
        assertThat(s1, nullValue());
        assertThat(s1, nullValue(String.class));
        assertThat(s2, notNullValue());
        assertThat(s2, notNullValue(String.class));
    }

    @Test
    public void integerComparisons() {
        assertThat(1, lessThan(2));
        assertThat(1, lessThanOrEqualTo(1));
        assertThat(1, equalTo(1));
        assertThat(1, greaterThanOrEqualTo(1));
        assertThat(2, greaterThan(1));
    }

    @Test
    public void doubleComparisons() {
        assertThat(3.14, closeTo(3.15, .01));
    }

    @Test
    public void testToString() {
        List list = ImmutableList.of(1, 2, 3);
        assertThat("testing toString is often a code smell, but there is a matcher for it just in case",
                list, hasToString("[1, 2, 3]"));
    }

    @Test
    public void testStrings() {
        assertThat(null, isEmptyOrNullString());
        assertThat("", isEmptyOrNullString());
        assertThat("foo", not(isEmptyOrNullString()));

        assertThat(null, not(isEmptyString()));
        assertThat("", isEmptyString());
        assertThat("foo", not(isEmptyString()));

        assertThat("FOO", equalToIgnoringCase("foo"));
        assertThat(" foo ", equalToIgnoringWhiteSpace("foo"));

        assertThat("foo", startsWith("f"));
        assertThat("foo", endsWith("o"));

        assertThat("foo bar baz qux", stringContainsInOrder(ImmutableList.of("foo", "baz", "qux")));
    }

    @Test
    public void testJavaBeans() {
        assertThat("hasProperty can check the existence of a property",
                new Person("Bob"), hasProperty("name")); // requires getter
        assertThat("hasProperty can check the existence of a property",
                new Person("Bob"), not(hasProperty("email")));

        assertThat("hasProperty is very useful if you are working with a class with no equals implementation",
                new Person("Bob"), hasProperty("name", equalTo("Bob")));

        assertThat(new Bean("s", 0), samePropertyValuesAs(new Bean("s", 0)));
        assertThat(new Bean("s", 0), not(samePropertyValuesAs(new Bean("t", 0))));
        assertThat(new Bean("s", 0), not(samePropertyValuesAs(new Bean("s", 1))));

        assertThat(new SubBeanWithNoExtraProperties("s", 0), samePropertyValuesAs(new Bean("s", 0)));

        assertThat(new SubBeanWithExtraProperty("s", 0), not(samePropertyValuesAs(new Bean("s", 0))));
    }

    @Test
    public void testArrays() {
        assertThat(new Integer[]{}, emptyArray());

        Integer[] a = {1, 2, 3};
        assertThat(a, array(equalTo(1), equalTo(2), equalTo(3)));

        assertThat(a, arrayWithSize(3));
        assertThat(a, arrayWithSize(greaterThan(1)));

        assertThat(a, hasItemInArray(2));
        assertThat(a, hasItemInArray(greaterThan(2)));

        assertThat(a, arrayContaining(1, 2, 3));
        assertThat(a, not(arrayContaining(3, 2, 1)));

        assertThat(a, arrayContainingInAnyOrder(1, 2, 3));
        assertThat(a, arrayContainingInAnyOrder(3, 2, 1));

        // What about primitive arrays? Use Commons Lang ArrayUtils.
        int[] primitives = {1, 2, 3};
        assertThat(ArrayUtils.toObject(primitives), hasItemInArray(2));
    }

    @Test
    public void testCollections() {
        // TODO
    }

    @Test
    public void testIterables() {
        // TODO
    }

    @Test
    public void testMaps() {
        // TODO
    }

    @Test
    public void testXml() {
        // TODO
    }

    @Test
    public void testFiles() {
        // TODO
    }

    public static class Bean {
        private String s;
        private int i;

        public Bean(String s, int i) {
            this.s = s;
            this.i = i;
        }

        public String getS() {
            return s;
        }

        public int getI() {
            return i;
        }
    }

    public static class SubBeanWithNoExtraProperties extends Bean {
        public SubBeanWithNoExtraProperties(String s, int i) {
            super(s, i);
        }
    }

    public static class SubBeanWithExtraProperty extends Bean {
        public SubBeanWithExtraProperty(String s, int i) {
            super(s, i);
        }

        public String getExtra() {
            return "extra";
        }
    }

    public static class Person {
        private String name;
        public Person(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
}
