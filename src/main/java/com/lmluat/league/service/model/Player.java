package com.lmluat.league.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 1000, message = "Last name must have at least 3 characters")
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 1000, message = "First name must have at least 3 characters")
    private String firstName;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;

    @Size(min = 3)
    private String homeTown;

    @Size(min = 3, max = 100, message = "Ingame Name must have at least 3 characters")
    private String ingameName;
}
