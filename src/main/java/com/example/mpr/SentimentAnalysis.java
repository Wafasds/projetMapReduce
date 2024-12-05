package com.example.mpr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SentimentAnalysis {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: SentimentAnalysis <input path> <output path>");
            System.exit(-1);
        }

        // Configuration Hadoop
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Analyse de Sentiment");

        job.setJarByClass(SentimentAnalysis.class);

        // Mapper et Reducer
        job.setMapperClass(SentimentMapper.class);
        job.setReducerClass(SentimentReducer.class);

        // Types de sortie
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Chemins d'entrée et de sortie
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Lancer le job
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
