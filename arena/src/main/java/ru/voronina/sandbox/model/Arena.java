package ru.voronina.sandbox.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Arena {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @Nullable
    private String description;

    @NotNull
    private Integer complexity;
}
