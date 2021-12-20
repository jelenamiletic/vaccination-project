package com.xml.vakcinacija.exist;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;

import com.xml.vakcinacija.utils.AuthenticationUtilities;

public class ExistRetrieve {

	public static ResourceSet izvrsiXPathIzraz(String collectionId, String xpathExp, String namespace) throws Exception {
		ResourceSet result;
		AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		
        System.out.println("\t- collection ID: " + collectionId);
        
    	System.out.println("[INFO] Loading driver class: " + conn.driver);
        Class<?> cl = Class.forName(conn.driver);
        
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        
        DatabaseManager.registerDatabase(database);
        
        Collection col = null;
        XMLResource res = null;
        
        try {    
        	col = DatabaseManager.getCollection(conn.uri + collectionId);

            if (col == null) {
                return null;
            }
            XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            xpathService.setProperty("indent", "yes");

            xpathService.setNamespace("", namespace);
            result = xpathService.query(xpathExp);
        } finally {
            //don't forget to clean up!
            
            if(res != null) {
                try { 
                	((EXistResource)res).freeResources(); 
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
            
            if(col != null) {
                try { 
                	col.close(); 
                } catch (XMLDBException xe) {
                	xe.printStackTrace();
                }
            }
        }
        return result;
	}
}
