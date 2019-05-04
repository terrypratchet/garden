package com.arek.garden;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TextLoader {

    private Map<String, Long> words = null;
    private List<Sentence> sentences = null;

    public List<Sentence> getSentences(){
        if(sentences==null){
            try {
                load();
            }
            catch (Exception e){
                e.printStackTrace();
            }


        }
        return this.sentences;
    }

    public Map<String, Long> getWords(){
        if(words==null){
            try {
                load();
            }
            catch (Exception e){
                e.printStackTrace();
            }


        }
        return words;
    }
    private void load() throws Exception{
        System.out.println("loading...");
        String aaa = "/home/wyrwigrosz/git/garden/src/main/resources/start.txt";
        aaa="/home/wyrwigrosz/Downloads/jasonmirandawitheyesclosed.txt";

        aaa="/home/wyrwigrosz/Downloads/lperezrenesmee.txt";
        //Files.readAllBytes(new File(aaa));
        byte[] bytes = Files.readAllBytes(Paths.get(aaa));
        String all = new String(bytes);
        Document doc = new Document(all);

        this.words = new HashMap<>();
        this.sentences = doc.sentences();
        sentences.stream().forEach(sentence -> {
            sentence.words().forEach(word -> {
                if (words.containsKey(word)) {
                    words.put(word, words.get(word) + 1);
                } else {
                    words.put(word, 1L);
                }
            });

        });
        System.out.println("Loaded " + aaa);

    }


}
