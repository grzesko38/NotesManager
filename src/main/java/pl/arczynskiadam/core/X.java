package pl.arczynskiadam.core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class X {
	private static final Logger log = Logger.getLogger(X.class);
	
	@Autowired
	private SessionFactory sessionFactory;

    public X() throws IOException
    {
        log.info( "Starting application..." );
        
//        Session sess=sessionFactory.openSession();
//
//        
//        
//        sess.createSQLQuery(readFile("/WEB-INF/sql_scripts/ddl.sql", StandardCharsets.UTF_8));
//        
//        sess.close();
    }
    
    static String readFile(String path, Charset encoding) 
	  throws IOException 
	{
	  byte[] encoded = Files.readAllBytes(Paths.get(path));
	  return new String(encoded, encoding);
	}
}
