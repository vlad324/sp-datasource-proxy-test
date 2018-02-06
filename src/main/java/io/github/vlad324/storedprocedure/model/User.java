package io.github.vlad324.storedprocedure.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author v.radkevich
 * @since 2/5/18
 */
@Getter
@Builder
@ToString
public class User {
    private final int id;
    private final String name;
}
