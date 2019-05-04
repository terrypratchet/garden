package com.arek.garden;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;


@CrossOrigin(origins = "*", allowedHeaders = "*")


@RestController
public class MyRest {

    @Autowired
    private TextProcessor textProcessor;

    @Autowired
    private TextLoader textLoader;


    @RequestMapping("/sentences")
    public Collection<String> findSentences(String word){
        List<Sentence> sentences = textLoader.getSentences();
        return textProcessor.findSentences(sentences,word);

    }

    @RequestMapping("/greeting")
    public Map<String, Object> getSth(@RequestParam String sort, @RequestParam int limit,
                                    @RequestParam int offset, int moreThan){

        String aaa = "/home/wyrwigrosz/git/garden/src/main/resources/start.txt";
        //Files.readAllBytes(new File(aaa));


        try {

            boolean asc;
            if("asc".equals(sort)){
                asc = true;
            }
            else{
                asc = false;
            }
            Map<String, Long> words = textLoader.getWords();

            Map<String, Number> statisticts = textProcessor.statisticts(textLoader.getWords());

            Map<String, Object> r = new HashMap<>();
            r.put("words", textProcessor.sort(words,asc, offset, limit, moreThan));
            r.put("stat", statisticts);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
