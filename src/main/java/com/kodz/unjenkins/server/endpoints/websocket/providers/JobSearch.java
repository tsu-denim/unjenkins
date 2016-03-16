package com.kodz.unjenkins.server.endpoints.websocket.providers;

import com.kodz.unjenkins.client.JenkinsConsumer;
import com.kodz.unjenkins.client.dto.View;
import com.kodz.unjenkins.client.proxy.JenkinsResource;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kurt on 3/16/16.
 */
public class JobSearch {
    static StandardAnalyzer analyzer = new StandardAnalyzer();
    static Directory index = new RAMDirectory();



    static View allJobs = new View();

    public static void initializeJobSearch(){
        try {
            index = new RAMDirectory();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            IndexWriter w = new IndexWriter(index, config);
            allJobs = JenkinsConsumer.jenkinsResource.getView("All");
            allJobs.getJobs().forEach(t -> addDoc(w, t.getName()));
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void addDoc(IndexWriter w, String title)  {
        Document doc = new Document();
        doc.add(new TextField("jobName", title, Field.Store.YES));

        try {
            w.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> query(String query){
        try {
            Query q = new QueryParser("jobName", analyzer).parse(query);
            int hitsPerPage = 25;
            IndexReader reader = DirectoryReader.open(index);
            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
            searcher.search(q, collector);
            List<ScoreDoc> hits = Arrays.asList(collector.topDocs().scoreDocs);
            List<String> results = hits.stream().map(t -> {
                try {
                    return searcher.doc(t.doc).get("jobName");
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            }).collect(Collectors.toList());
            ArrayList<String> returnList = new ArrayList<>();
            returnList.addAll(results);
            return returnList;

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
