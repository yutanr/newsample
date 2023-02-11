package com.example.springbootsampleec.forms;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateForm {
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
    
    @NotNull
    private  MultipartFile image;
}