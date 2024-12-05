package com.example.mpr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SentimentReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private final IntWritable result = new IntWritable();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;

        // Additionner toutes les occurrences pour chaque type de sentiment
        for (IntWritable val : values) {
            sum += val.get();
        }

        // Ajouter un contrôle pour filtrer ou gérer les catégories comme "non classifié" si besoin
        if (!key.toString().equalsIgnoreCase("non classifié") || sum > 0) {
            result.set(sum);
            context.write(key, result); // Écrire le sentiment et le total
        }
    }
}

