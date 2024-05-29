package com.crio_mongo.Xmemes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crio_mongo.Xmemes.entity.Meme;

public interface MemeRepository extends MongoRepository<Meme,Integer> {

    public Meme findByNameAndCaptionAndUrl(String name, String caption, String url);


}

