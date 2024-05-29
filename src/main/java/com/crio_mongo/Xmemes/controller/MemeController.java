package com.crio_mongo.Xmemes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crio_mongo.Xmemes.entity.Meme;
import com.crio_mongo.Xmemes.service.MemeService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping(value = "", produces = "application/json")
public class MemeController {
    
     @Autowired
     private MemeService memeService;

    @PostMapping("/memes")
    public String submitMeme(@RequestBody @Valid Meme meme) {
        return String.format("{\"id\" : %d}", memeService.saveMeme(meme));
    }

    @GetMapping("/memes")
    public List<Meme> getMemes() {
        return memeService.getMemes();
    }

    @GetMapping("/memes/{id}")
    public Meme getMeme(@PathVariable("id")  Integer id) {
        return memeService.getMeme(id);
    }

}
