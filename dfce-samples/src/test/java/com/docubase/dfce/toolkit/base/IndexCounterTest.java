package com.docubase.dfce.toolkit.base;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import net.docubase.toolkit.exception.ged.TagControlException;
import net.docubase.toolkit.model.ToolkitFactory;
import net.docubase.toolkit.model.base.BaseCategory;
import net.docubase.toolkit.model.document.Document;
import net.docubase.toolkit.model.reference.Category;
import net.docubase.toolkit.service.ServiceProvider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IndexCounterTest extends AbstractTestCaseCreateAndPrepareBase {

    @Before
    public void setupEach() {
	ServiceProvider.getStorageAdministrationService()
		.updateAllIndexesUsageCount();
    }

    private List<Document> storeNDocumentsAndUpdateIndexCounts(int nbDocuments)
	    throws TagControlException {
	java.util.List<Document> storedDocuments = new ArrayList<Document>();
	assertTrue("La base " + BASEID + " n'est pas d�marr�e.",
		base.isStarted());

	BaseCategory baseCategory0 = base.getBaseCategory(catNames[0]);
	BaseCategory baseCategory1 = base.getBaseCategory(catNames[1]);

	long deb = System.currentTimeMillis();
	for (int x = 0; x < nbDocuments; x++) {
	    File newDoc = getFile("doc1.pdf", IndexCounterTest.class);

	    assertTrue(newDoc.exists());

	    // On d�finit le Tag du futur document, li� � la base uBase.
	    Document document = ToolkitFactory.getInstance().createDocumentTag(
		    base);
	    document.setType("PDF");

	    // On dit que l'on veut mettre "Identifier" en valeur d'identifiant
	    // de la 1�re cat�gorie (d'indice 0)
	    String c0 = "Identifier" + UUID.randomUUID();
	    document.addCriterion(baseCategory0, c0);

	    // C1
	    document.addCriterion(baseCategory1, "C1val");

	    // Date de cr�ation du document (� priori avant son entr�e dans la
	    // GED, on retranche une heure)
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(System.currentTimeMillis());
	    cal.add(Calendar.HOUR, -1);
	    document.setCreationDate(cal.getTime());

	    document = storeDocument(document, newDoc);

	    storedDocuments.add(document);

	    // On v�rifie que le document a pass� le controle.
	    assertNotNull(document);
	}
	System.out
		.println("TPS = " + (System.currentTimeMillis() - deb) + "ms");
	ServiceProvider.getStorageAdministrationService()
		.updateAllIndexesUsageCount();
	return storedDocuments;
    }

    @Test
    public void testDocInsertionCounts() throws TagControlException {

	Category category0Reference = ServiceProvider
		.getStorageAdministrationService().getCategory(catNames[0]);
	Category category1Reference = ServiceProvider
		.getStorageAdministrationService().getCategory(catNames[1]);

	Integer c0TotalIndexUseCount = category0Reference
		.getTotalIndexUseCount();
	Integer c0DistinctIndexUseCount = category0Reference
		.getDistinctIndexUseCount();

	Integer c1TotalIndexUseCount = category1Reference
		.getTotalIndexUseCount();
	Integer c1DistinctIndexUseCount = category1Reference
		.getDistinctIndexUseCount();

	int nbDocuments = 1;
	storeNDocumentsAndUpdateIndexCounts(nbDocuments);

	category0Reference = ServiceProvider.getStorageAdministrationService()
		.getCategory(catNames[0]);
	category1Reference = ServiceProvider.getStorageAdministrationService()
		.getCategory(catNames[1]);

	Assert.assertEquals(
		Integer.valueOf(c0TotalIndexUseCount + nbDocuments),
		category0Reference.getTotalIndexUseCount());
	Assert.assertEquals(
		Integer.valueOf(c0DistinctIndexUseCount + nbDocuments),
		category0Reference.getDistinctIndexUseCount());
	Assert.assertEquals(
		Integer.valueOf(c1TotalIndexUseCount + nbDocuments),
		category1Reference.getTotalIndexUseCount());
	Assert.assertEquals(Integer.valueOf(c1DistinctIndexUseCount + 1),
		category1Reference.getDistinctIndexUseCount());
    }

    @Test
    public void testDocDeleteCounts() throws Exception {
	// assertTrue("La base " + BASEID + " n'est pas d�marr�e.",
	// base.isStarted());
	Category category0Reference = ServiceProvider
		.getStorageAdministrationService().getCategory(catNames[0]);
	Category category1Reference = ServiceProvider
		.getStorageAdministrationService().getCategory(catNames[1]);

	int nbDocuments = 1;
	Collection<Document> storedDocuments = storeNDocumentsAndUpdateIndexCounts(nbDocuments);

	category0Reference = ServiceProvider.getStorageAdministrationService()
		.getCategory(catNames[0]);
	category1Reference = ServiceProvider.getStorageAdministrationService()
		.getCategory(catNames[1]);

	Integer c0TotalIndexUseCount = category0Reference
		.getTotalIndexUseCount();
	Integer c0DistinctIndexUseCount = category0Reference
		.getDistinctIndexUseCount();

	Integer c1TotalIndexUseCount = category1Reference
		.getTotalIndexUseCount();
	Integer c1DistinctIndexUseCount = category1Reference
		.getDistinctIndexUseCount();

	for (Document document : storedDocuments) {
	    ServiceProvider.getStoreService()
		    .deleteDocument(document.getUuid());
	}

	ServiceProvider.getStorageAdministrationService()
		.updateAllIndexesUsageCount();
	category0Reference = ServiceProvider.getStorageAdministrationService()
		.getCategory(catNames[0]);
	category1Reference = ServiceProvider.getStorageAdministrationService()
		.getCategory(catNames[1]);

	Assert.assertEquals(
		Integer.valueOf(c0TotalIndexUseCount - nbDocuments),
		category0Reference.getTotalIndexUseCount());
	Assert.assertEquals(
		Integer.valueOf(c0DistinctIndexUseCount - nbDocuments),
		category0Reference.getDistinctIndexUseCount());
	Assert.assertEquals(
		Integer.valueOf(c1TotalIndexUseCount - nbDocuments),
		category1Reference.getTotalIndexUseCount());
	Assert.assertEquals(Integer.valueOf(c1DistinctIndexUseCount - 1),
		category1Reference.getDistinctIndexUseCount());
    }
}