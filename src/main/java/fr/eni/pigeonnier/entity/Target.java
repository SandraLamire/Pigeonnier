package fr.eni.pigeonnier.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Target {

    @NotBlank
    private String ip;

    @NotBlank
    private String code;

}