package com.empresa.proyecto.services;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyecto.models.entity.Producto;
import com.empresa.proyecto.models.service.IProductoService;

@Service
public class PlnService {
	private static final String TITLE_FIELD = "title";
	private static final String INDEX_DIR = "./lucene_index";
	 private Directory indexDirectory;
	 private Analyzer analyzer;
	 private IndexWriterConfig writerConfig;
	 private IndexReader reader;
	 private IndexSearcher searcher;
	 
	 @Autowired
	 private IProductoService productoService; 
	
	  /*@PostConstruct
	  public void init() throws IOException {
		  	indexDirectory = new ByteBuffersDirectory();
	        analyzer = new StandardAnalyzer();
	        writerConfig = new IndexWriterConfig(analyzer);
	        reader = DirectoryReader.open(indexDirectory);
	        searcher = new IndexSearcher(reader);
	  }
	  
	  public List<String> buscarPorSimilitud(String consulta) throws ParseException, IOException {
		  
		  	int maxEdits = 2; // Número máximo de ediciones permitidas en un término similar
	        float minimumSimilarity = 0.5f; // Nivel mínimo de similitud para
	        Term term = new Term(TITLE_FIELD, consulta);
	        FuzzyQuery query = new FuzzyQuery(term, maxEdits);
	        
	        int hitsPerPage = 10;
	        TopDocs docs = searcher.search(query, hitsPerPage);
	        ScoreDoc[] hits = docs.scoreDocs;
	        
	        List<String> resultados = new ArrayList<>();
	        for (ScoreDoc hit : hits) {
	            Document doc = searcher.doc(hit.doc);
	            resultados.add(doc.get(TITLE_FIELD));
	        }
	        	
	        return resultados;
	    }
	  
	  @PreDestroy
	    public void close() throws IOException {
	        reader.close();
	        indexDirectory.close();
	    }*/
	 
	 public void generarBusqueda(String termino) throws IOException {
		 StandardAnalyzer analyzer = new StandardAnalyzer();
		 Directory index = new ByteBuffersDirectory();

		 IndexWriterConfig config = new IndexWriterConfig(analyzer);
		 IndexWriter w = new IndexWriter(index, config);
		 
		 List<Producto> productos = productoService.findAll(); 
		 for(Producto p:productos) {
			 System.out.println("XD");
			 addDoc(w,p.getNombre(),p.getDescripcion());
		 }
		 addDoc(w, "playera xdd", "193398817");
	
		 w.close();
		 // 2. query
	        String querystr =  termino;

	        // the "title" arg specifies the default field to use
	        // when no field is explicitly specified in the query.
	        //Query q = new QueryParser("title", analyzer).parse(querystr);
	        Term term = new Term("producto", querystr);
	        FuzzyQuery query = new FuzzyQuery(term, 2);
	        

	        // 3. search
	        int hitsPerPage = 10;
	        IndexReader reader = DirectoryReader.open(index);
	        IndexSearcher searcher = new IndexSearcher(reader);
	        TopDocs docs = searcher.search(query, hitsPerPage);
	        ScoreDoc[] hits = docs.scoreDocs;

	        // 4. display results
	        System.out.println("Found " + hits.length + " hits.");
	        for(int i=0;i<hits.length;++i) {
	            int docId = hits[i].doc;
	            Document d = searcher.doc(docId);
	            System.out.println((i + 1) + ". " + d.get("descripcion") + "\t" + d.get("producto"));
	        }

	        // reader can only be closed when there
	        // is no need to access the documents any more.
	        reader.close();
		 
	 }
	 
	 private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
		  Document doc = new Document();
		  doc.add(new TextField("producto", title, Field.Store.YES));
		  doc.add(new StringField("descripcion", isbn, Field.Store.YES));
		  w.addDocument(doc);
		}
	 


}
