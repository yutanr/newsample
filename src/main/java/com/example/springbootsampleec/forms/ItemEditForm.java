package com.example.springbootsampleec.forms;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEditForm {
    @NotNull
    @Size(min=1, max=200)
    private String name;
    
    @NotNull
    private int price;
    
    @NotNull
    private int stock;
    
    @NotNull
    @Size(min=1, max=1000)
    private String description;
 
}