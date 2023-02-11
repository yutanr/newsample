package com.example.springbootsampleec.forms;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import javax.validation.constraints.NotNull;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;
}