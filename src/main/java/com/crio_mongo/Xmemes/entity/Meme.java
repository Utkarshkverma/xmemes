package com.crio_mongo.Xmemes.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "_xmemes")
@NoArgsConstructor
@AllArgsConstructor
public class Meme {
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";
    
    @Id
    private int id;
    
    @NotBlank
    private String name;

    @NotBlank
    private String caption;

    @NotBlank
    private String url;

}


