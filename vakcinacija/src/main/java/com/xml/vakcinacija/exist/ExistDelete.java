package com.xml.vakcinacija.exist;

import javax.xml.transform.OutputKeys;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xml.vakcinacija.utils.AuthenticationUtilities;

public class ExistDelete {

	public static void izbrisiResurs(String collectionId, String documentId) throws Exception {
		AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();
		XMLResource res;
        Collection col = null;
		
        System.out.println("\t- collection ID: " + collectionId);
        
    	System.out.println("[INFO] Loading driver class: " + conn.driver);
        Class<?> cl = Class.forName(conn.driver);
        
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        
        DatabaseManager.registerDatabase(database);
        try {
            col = DatabaseManager.getCollection(conn.uri + collectionId, conn.user, conn.password);
            col.setProperty(OutputKeys.INDENT, "yes");

            res = (XMLResource) col.getResource(documentId + ".xml");
            if (res != null) {
                col.removeResource(res);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException ex) {
                    ex.printStackTrace();
                }
            }
        }
	}
}
