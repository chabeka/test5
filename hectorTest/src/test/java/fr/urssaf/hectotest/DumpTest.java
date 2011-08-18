package fr.urssaf.hectotest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.SuperRow;
import me.prettyprint.hector.api.beans.SuperRows;
import me.prettyprint.hector.api.beans.SuperSlice;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.MultigetSuperSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;
import me.prettyprint.hector.api.query.SuperSliceQuery;
import me.prettyprint.hom.EntityManagerImpl;


/**
 * Dump de données cassandra
 */
public class DumpTest
{

	Keyspace keyspace;
	Cluster cluster;
	PrintStream sysout;
	Dumper dumper;
	
	@Before  
	public void init() throws Exception {
		ConfigurableConsistencyLevel ccl = new ConfigurableConsistencyLevel();
		ccl.setDefaultReadConsistencyLevel(HConsistencyLevel.ONE);
		ccl.setDefaultWriteConsistencyLevel(HConsistencyLevel.QUORUM);
		cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("cer69imageint10.cer69.recouv:9160,cer69imageint9.cer69.recouv:9160" ));
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("cer69-saeint2.cer69.recouv:9160" ));
		keyspace = HFactory.createKeyspace("Docubase", cluster, ccl);
		sysout = new PrintStream(System.out, true, "UTF-8");
		dumper = new Dumper(keyspace, sysout);
    }
    
	 public static void main(String[] args) throws Exception
	 {
		 DumpTest app = new DumpTest();
		 app.init();
		 /*		 
		 app.testDumpDocInfo();
		 app.close();
		 */
		 app.sysout.println("±ΰσ");
		 System.out.println("±ΰσ");
	 }
	
	@After
	public void close() {
		//cluster.getConnectionManager().shutdown();
	}
	
	@Test
	public void testDumpDocInfo() throws Exception {
		dumper.dumpCF("DocInfo", 150);
	}
	
	@Test
	public void testDumpDocuments() throws Exception {
		dumper.dumpCF("Documents", 20);
	}
	
	@Test
	public void testDumpOneDocument() throws Exception {
		dumper.dumpCF("Documents", "0f5003e0-8698-4405-9804-a098ed6e5575_v1");
	}

	@Test
	public void testExtractOneDocument() throws Exception {
		ExtractOneDocument("0052ffca-a542-4aca-b0aa-4b4d037e9fe6", "c:\\temp\\test.pdf");
		ExtractOneDocument("017961ff-1899-40a7-8d3d-6d32729780ef", "c:\\temp\\test2.pdf");
		ExtractOneDocument("0125e102-a096-4db0-9746-edf0c314498a", "c:\\temp\\test3.pdf");
		ExtractOneDocument("0f5003e0-8698-4405-9804-a098ed6e5575", "c:\\temp\\test4.pdf");
	}

	/***
	 * Extrait le corps de la version 1 du document dont l'uuid est passé en paramètre
	 * @param uuid : uuid du document à extraire
	 * @param fileName : fichier à créer
	 * @throws Exception
	 */
	private void ExtractOneDocument(String uuid, String fileName) throws Exception {
		String key = uuid + "_v1";
		
		StringSerializer stringSerializer = StringSerializer.get();
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();
		RangeSlicesQuery<String, String, byte[]> rangeSlicesQuery = HFactory
				.createRangeSlicesQuery(keyspace, stringSerializer,
						stringSerializer, bytesSerializer);
		rangeSlicesQuery.setColumnFamily("Documents");
		rangeSlicesQuery.setKeys(key, key);
		rangeSlicesQuery.setRange("chunk_0", "chunk_9", false, 1000);
		QueryResult<OrderedRows<String, String, byte[]>> result = rangeSlicesQuery
				.execute();
		OrderedRows<String, String, byte[]> orderedRows = result.get();
		
		// On ne reçoit normalement qu'une seule ligne
		Row<String, String, byte[]> row = orderedRows.getByKey(key);
		
		sysout.println("Création du fichier " + fileName + "...");
		File someFile = new File(fileName);
		FileOutputStream fos = new FileOutputStream(someFile);
		
		ColumnSlice<String, byte[]> columnSlice = row.getColumnSlice();
		List<HColumn<String, byte[]>> columns = columnSlice.getColumns();
		for (HColumn<String, byte[]> column : columns) {
			String name = column.getName();
			sysout.println("Extracting " + name + " ...");
			byte[] value =  column.getValue();
			fos.write(value);
		}
		fos.flush();
		fos.close();		
	}

	@Test
	public void testDumpTermInfo() throws Exception {
		//String delimiter = "\u00ef\u00bf\u00bf";
		String delimiter = "\uffff";
		//String startKey = "DOCUBASE" + delimiter +  "srt";
		//String startKey = "DOCUBASE" + delimiter +  "caté";
		String startKey = "D";
		String filter = "0052ffca-a542-4aca-b0aa-4b4d037e9fe6";
		dumper.dumpSCF("TermInfo", startKey, 100000, filter);
	}
	
	@Test
	public void testDumpNotes() throws Exception {
		dumper.dumpCF("Notes", 15);
	}
	
	@Test
	public void testDumpAnnotations() throws Exception {
		dumper.dumpCF("Annotations", 15);
	}
	
	@Test
	public void testDumpRecordManager() throws Exception {
		dumper.dumpSCF("RecordManager", 15);
	}
	
	@Test
	public void testDumpRecordManagerRef() throws Exception {
		dumper.dumpCF("RecordManagerRef", 15);
	}
	
	@Test
	public void testDumpRecordManagerArchive() throws Exception {
		dumper.dumpCF("RecordManagerArchive", 15);
	}
	
	@Test
	public void testDumpBaseCategoriesReference() throws Exception {
		dumper.dumpCF("BaseCategoriesReference", 500);
	}
	
	@Test
	public void testDumpCategoriesReference() throws Exception {
		dumper.dumpCF("CategoriesReference", 15);
	}
	
	@Test
	public void testDumpBasesReference() throws Exception {
		dumper.dumpCF("BasesReference", 150);
	}
	
	@Test
	public void testDumpCompositeIndexesReference() throws Exception {
		dumper.dumpCF("CompositeIndexesReference", 15);
	}
	
	@Test
	public void testDumpIndexCriteriaReference() throws Exception {
		dumper.dumpCF("IndexCriteriaReference", 15);
	}
	
	@Test
	public void testDumpStaticDictionaries() throws Exception {
		dumper.dumpCF("StaticDictionaries", 15);
	}
	
	@Test
	public void testDumpIndexCounter() throws Exception {
		dumper.dumpCF("IndexCounter", 15);
	}
	
	@Test
	public void testDumpSystemEventLog() throws Exception {
		dumper.dumpCF("SystemEventLog", 15);
	}
	@Test
	public void testDumpDocEventLog() throws Exception {
		dumper.dumpCF("DocEventLog", 200);
	}
	@Test
	public void testDumpDocEventLogByTime() throws Exception {
		dumper.dumpCF("DocEventLogByTime", 15);
	}
	@Test
	public void testDumpUser() throws Exception {
		dumper.dumpCF("User", 15);
	}
	
	
	@Test
	public void testCQL() throws Exception {
		StringSerializer stringSerializer = StringSerializer.get();
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();
		CqlQuery<byte[],String,byte[]> cqlQuery = new CqlQuery<byte[],String,byte[]>(keyspace, bytesSerializer, stringSerializer, bytesSerializer);
		//String query = "select * from DocInfo where Key ='" + ConvertHelper.stringToHex("DOCUBASE") + "efbfbf" + ConvertHelper.stringToHex("28e154e8-ee91-44f8-ab50-c62e0f8c6898") + "'";
		//String query = "select * from DocInfo where Key ='" + ConvertHelper.stringToHex("DOCUBASE") + "efbfbf" + ConvertHelper.stringToHex("d6db9900-4e83-401f-ac82-21e88b804503") + "'";
		//String query = "select * from BasesReference where Key ='" + ConvertHelper.stringToHex("SAE") + "'";
		String query = "select * from Documents where Key ='" + ConvertHelper.stringToHex("d6db9900-4e83-401f-ac82-21e88b804503_v1") + "'";
		//String query = "select * from SystemEventLog WHERE KEY >'" + ConvertHelper.stringToHex("20110727132645016") + "' LIMIT 100";
		cqlQuery.setQuery(query);
		QueryResult<CqlRows<byte[],String,byte[]>> result = cqlQuery.execute();
		dumper.dumpCqlQueryResult(result);
	}
	
	@Test
	public void testDelete() throws Exception {
		StringSerializer keySerializer = StringSerializer.get();
		Mutator<String> mutator = HFactory.createMutator(keyspace, keySerializer);
		StringSerializer nameSerializer = keySerializer;
		String cf = "Documents";
		String key = "156432135";
		mutator.delete(key, cf, null, nameSerializer);
	}
	
	

    
	@Test
	public void testEntityManager() {

		EntityManagerImpl em = new EntityManagerImpl(keyspace,
				"fr.urssaf.hectotest");

		/*
		 * MyPojo pojo1 = new MyPojo(); pojo1.setId(UUID.randomUUID());
		 * pojo1.setLongProp1(123L);
		 * 
		 * em.save(pojo1);
		 */

		// do some stuff

		// MyPojo pojo2 = em.load(MyPojo.class, pojo1.getId());
		MyPojo pojo2 = em.load(MyPojo.class, UUID.randomUUID());

		// do some more stuff
		if (pojo2 == null) {
			sysout.println("Entity non trouvée");
		}
		else {
			sysout.println("Long prop = " + pojo2.getLongProp1());
		}
	}
}
