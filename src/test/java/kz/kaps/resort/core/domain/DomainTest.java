package kz.kaps.resort.core.domain;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

public class DomainTest {

    @Test
    public void adImageTest() {
        assertPojoMethodsForAll(Image.class)
                .testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void faceteFilterTest() {
        assertPojoMethodsForAll(FaceteFilter.class)
                .testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void priceTagTest() {
        assertPojoMethodsForAll(PriceTag.class)
                .testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void passwordResetTokenTest() {
        assertPojoMethodsForAll(PasswordResetToken.class)
                .testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }

    @Test
    public void userTest() {
        assertPojoMethodsForAll(User.class)
                .testing(Method.CONSTRUCTOR, Method.GETTER, Method.SETTER).areWellImplemented();
    }

}
