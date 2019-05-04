package com.arek.garden;

import com.google.common.math.Stats;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.toMap;



@Component
public class TextProcessor {


    public Collection<String> findSentences(List<Sentence> sentences, final String word){
        return sentences.stream().filter( sentence -> {

            return sentence.words().contains(word);
        }).map(st-> st.text()).collect(Collectors.toList());
    }



    public Map<String, Number> statisticts(Map<String, Long>  words){

        Map<String, Number> result  = new HashMap<>();
        //DoubleStream doubleStream = words.values().stream().mapToDouble(a -> a);
        //OptionalDouble average = doubleStream.average();



        Stats stats = Stats.of(words.values());
        //double sdev = stats.sampleStandardDeviation();

        result.put("standardDeviation", stats.sampleStandardDeviation());
        result.put("average", stats.mean());
        result.put("distinctCount", stats.count());
        result.put("distinctCount", words.size());
        result.put("totalCount", stats.sum());

        int freq = 1;
        //long countToLearn = words.entrySet().stream().filter(a->a.getValue()>freq).count();
        //words.entrySet().stream().filter(a->a.getValue()>freq).collect(Collectors.toList()).to;

        return result;
    }

    public Map<String, Long> sort(   Map<String, Long>  words,  boolean asc,  int offset, int limit, int moreThan) {


        Comparator<Map.Entry<String, Long>> comparator;

        if(asc){
            comparator= Map.Entry.comparingByValue();

        }
        else{

            comparator= Collections.reverseOrder(Map.Entry.comparingByValue());

        }

        Map<String, Long> sortedWords = words
                .entrySet()
                .stream()
                .filter(a->a.getValue()>moreThan)
                .sorted(comparator)
                .skip(offset)
                .limit(limit)

                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
                                LinkedHashMap::new));
        ;

        //List<String> strings = Files.readAllLines(Paths.get(aaa));

        if(true==true){
            return sortedWords;
        }

        Map<String, Long> topRankWords = new LinkedHashMap<>();
        int i = 0;

        Iterator<Map.Entry<String, Long>> iterator = sortedWords.entrySet().iterator();

        return sortedWords.entrySet().stream().skip(offset).limit(limit).collect(Collectors.toMap(entry->entry.getKey(), entry->entry.getValue()));
/*
        while(iterator.hasNext() && i< limit){

            Map.Entry<String, Long> next = iterator.next();

            if(i<offset){
                i++;

                continue;
            }

            i++;

            topRankWords.put(next.getKey(), next.getValue());



        }

        return topRankWords;
  */
    }
}
