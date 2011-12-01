package fr.urssaf.hectotest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
import me.prettyprint.cassandra.service.FailoverPolicy;
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
		ccl.setDefaultReadConsistencyLevel(HConsistencyLevel.QUORUM);
		ccl.setDefaultWriteConsistencyLevel(HConsistencyLevel.QUORUM);
		HashMap<String,String> credentials = new HashMap<String, String>() {{ put("username", "root");}{ put("password", "regina4932");}};
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("cer69imageint10.cer69.recouv:9160,cer69imageint9.cer69.recouv:9160" ));
		cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("cer69imageint9.cer69.recouv:9160" ));
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("10.203.34.39:9160" )); //noufnouf
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("hwi69givnsaecas1.cer69.recouv:9160,hwi69givnsaecas2.cer69.recouv:9160" ));
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("hwi69devsaecas1.cer69.recouv:9160,hwi69devsaecas2.cer69.recouv:9160" ));

		keyspace = HFactory.createKeyspace("Docubase", cluster, ccl, FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE, credentials);

		sysout = new PrintStream(System.out, true, "UTF-8");

		// Pour dumper sur un fichier plutôt que sur la sortie standard
		//sysout = new PrintStream("c:/temp/out.txt");
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
	public void testExtractOneDocInfo() throws Exception {
		extractOneDocInfo  ("00001972-c16e-491b-922addd2e7bf20e7");
		//extractOneDocInfo("bf15608f-1f17-4819-944d-e377c7bd726d");
		//extractOneDocInfo("6fd809ec-8fd7-44f3-9d6f-ee655fa7e54a");		
	}

	private void extractOneDocInfo(String uuid) throws Exception {
		byte[] key = ConvertHelper.stringToBytesWithDocubaseDelimiter(uuid + "|||0.0.0");
		dumper.dumpCF("DocInfo",key);	}
	
	@Test
	public void testGetDocCount() throws Exception {
		int count = dumper.getKeysCount("DocInfo");
		sysout.println("Nombre de clés dans DocInfo : " + count);
		count = dumper.getKeysCount("Documents");
		sysout.println("Nombre de clés dans Documents : " + count);
	}
	
	@Test
	public void testDumpDocuments() throws Exception {
		dumper.dumpCF("Documents", 150);
	}
	
	@Test
	public void testDumpOneDocument() throws Exception {
		dumper.dumpCF("Documents", "dcd998fe-0445-48a5-baa1-22f27433b983_v1");
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
		// sm_life_cycle_reference_date - sm_uuid 
		byte[] startKey = ConvertHelper.stringToBytesWithDocubaseDelimiter("DOCUBASE|||sm_uuid|||fff");
		String filter = "0052ffca-a542-4aca-b0aa-4b4d037e9fe6";
		//dumper.dumpSCF("TermInfo", startKey, 100000, filter);
		//dumper.dumpCF("TermInfo", 10000);
		sysout.println("début");
		dumper.dumpCF_StartKey("TermInfo", startKey, 100);
		//dumper.dumpCF("TermInfo", 50);
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
		dumper.dumpCF("CategoriesReference", 100);
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
	public void testDumpJobs() throws Exception {
		dumper.dumpCF("Jobs", 15);
	}
	@Test
	public void testDumpLifeCycleRules() throws Exception {
		dumper.dumpCF("LifeCycleRules", 15);
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
		dumper.dumpCF("DocEventLog", 30);
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
	public void testDumpUserGroup() throws Exception {
		dumper.dumpCF("UserGroup", 15);
	}
	@Test
	public void testDumpUserSearchFilters() throws Exception {
		dumper.dumpCF("UserSearchFilters", 15);
	}
	@Test
	public void testDumpVersions() throws Exception {
		dumper.dumpCF("Versions", 15);
	}
	
	
	@Test
	public void testCQL() throws Exception {
		StringSerializer stringSerializer = StringSerializer.get();
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();
		CqlQuery<byte[],String,byte[]> cqlQuery = new CqlQuery<byte[],String,byte[]>(keyspace, bytesSerializer, stringSerializer, bytesSerializer);
		//String query = "select * from DocInfo where Key ='" + ConvertHelper.stringToHex("DOCUBASE") + "efbfbf" + ConvertHelper.stringToHex("dd258958-24ff-486e-84b8-4f6b2714aaff") + "'";
		//String query = "select * from DocInfo where Key ='" + ConvertHelper.stringToHex("DOCUBASE") + "efbfbf" + ConvertHelper.stringToHex("d6db9900-4e83-401f-ac82-21e88b804503") + "'";
		//String query = "select * from BasesReference where Key ='" + ConvertHelper.stringToHex("SAE") + "'";
		String query = "select * from Documents where Key ='" + ConvertHelper.stringToHex("dd258958-24ff-486e-84b8-4f6b2714aaff_v1") + "'";
		//String query = "select * from SystemEventLog WHERE KEY >'" + ConvertHelper.stringToHex("20110819000000000") + "' LIMIT 100";
		//String query = "select * from DocEventLogByTime WHERE KEY >'" + ConvertHelper.stringToHex("20110819000000000") + "' LIMIT 100";
		//String query = "select * from DocEventLog WHERE KEY >'" + ConvertHelper.stringToHex("d6db9900-4e83-401f-ac82-21e88b804503") + "' LIMIT 50";
		cqlQuery.setQuery(query);
		QueryResult<CqlRows<byte[],String,byte[]>> result = cqlQuery.execute();
		dumper.dumpCqlQueryResult(result);
	}
	
	@Test
	public void testExtractDocEventForOneDoc() throws Exception {
		QueryResult<CqlRows<byte[],String,byte[]>> result = getDocEventForOneDoc("dd258958-24ff-486e-84b8-4f6b2714aaff");
		//QueryResult<CqlRows<byte[],String,byte[]>> result = getDocEventForOneDoc("d6db9900-4e83-401f-ac82-21e88b804503");
		dumper.dumpCqlQueryResult(result);
	}
	
	protected QueryResult<CqlRows<byte[],String,byte[]>> getDocEventForOneDoc(String uuid) throws Exception {
		StringSerializer stringSerializer = StringSerializer.get();
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();
		CqlQuery<byte[],String,byte[]> cqlQuery = new CqlQuery<byte[],String,byte[]>(keyspace, bytesSerializer, stringSerializer, bytesSerializer);
		String query = "select * from DocEventLog " +
				" WHERE KEY >'" + ConvertHelper.stringToHex(uuid) + "'" + 
				" AND KEY < '" + ConvertHelper.stringToHex(uuid) + "FF'";
		cqlQuery.setQuery(query);
		QueryResult<CqlRows<byte[],String,byte[]>> result = cqlQuery.execute();
		return result;
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
