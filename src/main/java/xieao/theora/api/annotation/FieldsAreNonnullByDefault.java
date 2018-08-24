package xieao.theora.api.annotation;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation can be applied to a package, class indicate that the fields
 * in that element and nesting elements are nonnull by default. Unless there is:
 * <ul>
 * <li>An explicit nullness annotation
 * <li>there is a default annotation applied to a more tightly nested element.
 * </ul>
 */

@Documented
@Nonnull
@TypeQualifierDefault(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsAreNonnullByDefault {

}
