package com.example.mpr;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SentimentMapper extends Mapper<Object, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private final Text sentiment = new Text();

    // Listes de mots-clés pour chaque sentiment
    private static final List<String> positiveWords = Arrays.asList(
            "good", "happy", "excellent", "amazing", "love", "awesome", "fantastic", "great", "positive", "joy", "wonderful", "nice"
    );

    private static final List<String> negativeWords = Arrays.asList(
            "bad", "sad", "poor", "terrible", "hate", "awful", "horrible", "worst", "negative", "angry", "disgusting", "unhappy"
    );

    private static final List<String> neutralWords = Arrays.asList(
            "okay", "fine", "average", "neutral", "alright", "decent", "normal", "standard", "usual", "regular"
    );

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // Convertir chaque ligne en minuscules pour faciliter la recherche
        String line = value.toString().toLowerCase();

        // Vérifier les mots-clés dans chaque catégorie
        if (containsWord(line, positiveWords)) {
            sentiment.set("positif");
        } else if (containsWord(line, negativeWords)) {
            sentiment.set("négatif");
        } else if (containsWord(line, neutralWords)) {
            sentiment.set("neutre");
        } else {
            sentiment.set("non classifié"); // Si aucun mot-clé ne correspond
        }

        // Écrire la paire (sentiment, 1) dans le contexte
        context.write(sentiment, one);
    }

    // Méthode utilitaire pour vérifier si une ligne contient un mot de la liste
    private boolean containsWord(String line, List<String> words) {
        for (String word : words) {
            if (line.contains(word)) {
                return true;
            }
        }
        return false;
    }
}

