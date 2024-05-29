package com.crio_mongo.Xmemes.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio_mongo.Xmemes.entity.Meme;
import com.crio_mongo.Xmemes.exception.MemeAlreadyExistsException;
import com.crio_mongo.Xmemes.exception.MemeConstraintViolationException;
import com.crio_mongo.Xmemes.exception.MemeNotFoundException;
import com.crio_mongo.Xmemes.repository.MemeRepository;
import static com.crio_mongo.Xmemes.entity.Meme.*;

@Service
public class MemeService {
    
    @Autowired
    private MemeRepository repository;

    private final Pattern urlPattern = Pattern.compile("(http|https):\\/\\/(www)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)");
    
    @Autowired
    private SequenceGeneratorService service;


    public List<Meme> getMemes() {
        List<Meme> memesPage = repository.findAll();
        if(memesPage.size()>100)
        {
            Collections.sort(memesPage,(a,b)->Integer.compare(b.getId(),a.getId()));
            List<Meme> memePage = new ArrayList<>();
            for(int i=0;i<100;i++)
            {
                memePage.add(memesPage.get(i));
            }

            return memePage;
        }
        return memesPage;
    }


    public Meme getMeme(Integer id) {
        Optional<Meme> meme = repository.findById(id);
        if (meme.isEmpty()) throw new MemeNotFoundException();
        return meme.get();
    }


    
    private void ensureURLMatchesPattern(Meme meme) {
        Matcher m = urlPattern.matcher(meme.getUrl());
        if (!m.matches()) throw new MemeConstraintViolationException(List.of("Posted URL is not in valid format"));
    }

     private void ensureMemeDoesNotAlreadyExist(Meme meme) {
        Meme previousMeme = repository.findByNameAndCaptionAndUrl(meme.getName(), meme.getCaption(), meme.getUrl());
        if (previousMeme != null) throw new MemeAlreadyExistsException();
    }

    public int saveMeme(Meme meme) {
        if (meme.getName() != null && meme.getName().trim().equals(""))
            throw new MemeConstraintViolationException(List.of("Name cannot be null"));
        ensureURLMatchesPattern(meme);
        ensureMemeDoesNotAlreadyExist(meme);
        meme.setId(service.getSequenceNumber(SEQUENCE_NAME));
        repository.save(meme);
        return meme.getId();
    }

    public void deleteMeme(Integer id) {
        Meme meme = getMeme(id);
        repository.delete(meme);
    }

}
