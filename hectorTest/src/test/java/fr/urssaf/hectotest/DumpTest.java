package fr.urssaf.hectotest;

import static me.prettyprint.hector.api.ddl.ComparatorType.UTF8TYPE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.model.thrift.ThriftCountQuery;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.CompositeSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.FailoverPolicy;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.SuperRow;
import me.prettyprint.hector.api.beans.SuperRows;
import me.prettyprint.hector.api.beans.SuperSlice;
import me.prettyprint.hector.api.beans.AbstractComposite.ComponentEquality;
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
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("cnp69saecas1:9160, cnp69saecas2:9160, cnp69saecas3:9160, cnp31saecas1.cer31.recouv:9160" ));
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("cer69imageint10.cer69.recouv:9160,cer69imageint9.cer69.recouv:9160" ));
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("cer69imageint10.cer69.recouv:9160" ));
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("cer69imageint9.cer69.recouv:9160" ));
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("10.203.34.39:9160" )); //noufnouf
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("hwi69givnsaecas1.cer69.recouv:9160,hwi69givnsaecas2.cer69.recouv:9160" ));
		//cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("cer69-saeint1.cer69.recouv:9160" ));
		cluster = HFactory.getOrCreateCluster("Docubase", new CassandraHostConfigurator("hwi69devsaecas1.cer69.recouv:9160,hwi69devsaecas2.cer69.recouv:9160" ));

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
		dumper.printKeyInHex = true;
		dumper.dumpCF("DocInfo", 500);
	}

	@Test
	public void testDocInfoFreezer() throws Exception {
		dumper.printKeyInHex = true;
		dumper.dumpCF("DocInfoFreezer", 150);
	}

	@Test
	public void testExtractOneDocInfo() throws Exception {
		//extractOneDocInfo  ("32100f97-5d05-4d6c-b2e3-6e9cc8f2bf86");
		extractOneDocInfo  ("dd498a1d-66fa-4777-8eda-cfdafe19b2ce");
		//extractOneDocInfo("bf15608f-1f17-4819-944d-e377c7bd726d");
		//extractOneDocInfo("6fd809ec-8fd7-44f3-9d6f-ee655fa7e54a");		
	}

	private void extractOneDocInfo(String uuid) throws Exception {
		String startKey = "0010";
		String endKey = "000005" + ConvertHelper.stringToHex("0.0.0") + "00";
		byte[] key =  ConvertHelper.hexStringToByteArray(startKey + uuid.replace("-", "") + endKey);
		dumper.dumpCF("DocInfo",key);
	}
	
	@Test
	public void testGetDocCount() throws Exception {
		/*
		 
		//Méthode trop lente
		
		int count = dumper.getKeysCount("DocInfo");
		sysout.println("Nombre de clés dans DocInfo : " + count);
		count = dumper.getKeysCount("Documents");
		sysout.println("Nombre de clés dans Documents : " + count);
		*/
		
		List<byte[]> keys = dumper.getKeys("TermInfoRangeUUID", 1000);
		if (keys.size() == 1000) throw new Exception("Attention : trop de clés");
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();

		byte[] sliceStart = getTermInfoRangeUUIDSliceBytes("\u0000");
		byte[] sliceEnd = getTermInfoRangeUUIDSliceBytes("\uFFFF");

		int total = 0;
		for (byte[] key : keys) {
			String displayableKey = ConvertHelper.getReadableUTF8String(key);
			if (displayableKey.contains("SM_UUID")) {
				sysout.print("Nombre de colonnes pour la clé " + displayableKey + " ...");
				ThriftCountQuery<byte[], byte[]> cq = new ThriftCountQuery<byte[], byte[]>(keyspace, bytesSerializer, bytesSerializer);
			    cq.setColumnFamily("TermInfoRangeUUID").setKey(key);
			    cq.setRange(sliceStart, sliceEnd , 10000000);
			    QueryResult<Integer> r = cq.execute();
			    int count = r.get();
				sysout.println(count);
				total += count;
			}
		}
		sysout.print("Nombre total de documents : " + total);		
	}
	
	@Test
	public void testDumpDocuments() throws Exception {
		dumper.dumpCF("Documents", 150);
	}
	
	@Test
	public void testDumpOneDocument() throws Exception {
		dumper.dumpCF("Documents", "651b658e-e0ca-4dce-abf5-aed4d32557e3");
	}

	@Test
	public void testExtractOneDocument() throws Exception {
		ExtractOneDocument("651b658e-e0ca-4dce-abf5-aed4d32557e4", "c:\\temp\\test.csv");
		//ExtractOneDocument("017961ff-1899-40a7-8d3d-6d32729780ef", "c:\\temp\\test2.pdf");
		//ExtractOneDocument("0125e102-a096-4db0-9746-edf0c314498a", "c:\\temp\\test3.pdf");
		//ExtractOneDocument("0f5003e0-8698-4405-9804-a098ed6e5575", "c:\\temp\\test4.pdf");
	}

	/***
	 * Extrait le corps de la version 1 du document dont l'uuid est passé en paramètre
	 * @param uuid : uuid du fichier à extraire
	 * @param fileName : fichier à créer
	 * @throws Exception
	 */
	private void ExtractOneDocument(String uuid, String fileName) throws Exception {
		String key = uuid;
		
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
		if (row == null) throw new IllegalArgumentException("On n'a pas trouvé de fichier dont l'uuid est " + uuid);
		
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
		dumper.printColumnNameInComposite = true;
		dumper.deserializeValue = true;
		dumper.compositeDisplayTypeMapper = new boolean[]{true, true, false};

		//dumper.dumpCF("TermInfo", 10000);
		
		byte[] startKey = getTermInfoKey("srn", "0123406588");
		dumper.dumpCF_StartKey("TermInfo", startKey, 30);
	}
	
	@Test
	public void testDumpOneTermInfo() throws Exception {
		dumpOneTermInfo("srn", "0123406588");
		//dumpOneTermInfo("SM_LIFE_CYCLE_REFERENCE_DATE", "20120106124749571");
	}
	
	/**
	 * Calcule une clé pour la CF TermInfo
	 * @param categorieName : nom de la catégorie (ex : nre)
	 * @param value			: valeur de la catégorie
	 * @throws Exception
	 */
	private byte[] getTermInfoKey(String categorieName, String value) throws Exception {
		// Exemple de Clé de terminfo :
		// \x00\x00\x00\x00   \x03    nre   \x00\x00  \x04    1234    \x00
		// Les \x03 et \x04 sont les tailles de categorieName et value
		byte[] key = ConvertHelper.hexStringToByteArray("00000000" + ConvertHelper.getHexString((byte)categorieName.length()) 
				+ ConvertHelper.stringToHex(categorieName) 
				+ "0000" + ConvertHelper.getHexString((byte)value.length())
				+ ConvertHelper.stringToHex(value) + "00");
		return key;
	}
	
	/**
	 * Dump une ligne de la CF TermInfo
	 * @param categorieName : nom de la catégorie (ex : nre)
	 * @param value			: valeur de la catégorie
	 * @throws Exception
	 */
	private void dumpOneTermInfo(String categorieName, String value) throws Exception {
		byte[] key = getTermInfoKey(categorieName, value);
		dumper.printColumnNameInHex = true;		
		dumper.deserializeValue = true;
		dumper.dumpCF("TermInfo", key);		
	}
	
	
	@Test
	public void testDumpTermInfoRangeDate() throws Exception {
		dumper.deserializeValue = true;
		dumper.printColumnNameInComposite = true;
		dumper.compositeDisplayTypeMapper = new boolean[]{false, true, false};
		dumper.dumpCF("TermInfoRangeDate", 15);
	}
	@Test
	public void testDumpTermInfoRangeDateTime() throws Exception {
		dumper.deserializeValue = true;
		dumper.printColumnNameInComposite = true;
		dumper.compositeDisplayTypeMapper = new boolean[]{false, true, false};
		dumper.dumpCF("TermInfoRangeDatetime", 15);
	}

	@Test
	public void testDumpTermInfoRangeDouble() throws Exception {
		dumper.dumpCF("TermInfoRangeDouble", 15);
	}

	@Test
	public void testDumpTermInfoRangeFloat() throws Exception {
		dumper.dumpCF("TermInfoRangeFloat", 15);
	}
	@Test
	public void testDumpTermInfoRangeInteger() throws Exception {
		dumper.dumpCF("TermInfoRangeInteger", 15);
	}
	@Test
	public void testDumpTermInfoRangeLong() throws Exception {
		dumper.dumpCF("TermInfoRangeLong", 15);
	}
	@Test
	public void testDumpTermInfoRangeString() throws Exception {
		dumper.deserializeValue = true;
		dumper.printColumnNameInComposite = true;
		dumper.compositeDisplayTypeMapper = new boolean[]{false, true, false};
		dumper.dumpCF("TermInfoRangeString", 30);
	}
	@Test
	public void testDumpTermInfoRangeUUID() throws Exception {
		dumper.deserializeValue = true;
		dumper.printColumnNameInComposite = true;
		dumper.compositeDisplayTypeMapper = new boolean[]{false, true, false};
		
		//dumper.dumpCF("TermInfoRangeUUID", 15);
		
		//byte[]sliceStart = new byte[0];
		//byte[]sliceEnd = new byte[0];
		byte[] sliceStart = getTermInfoRangeUUIDSliceBytes("1234");
		byte[] sliceEnd = getTermInfoRangeUUIDSliceBytes("1235");
		dumper.dumpCF_slice("TermInfoRangeUUID", sliceStart, sliceEnd, 15);
	}
	
	private Composite getTermInfoRangeUUIDSliceComposite(String docUUID) {
		/*
		 La CF TermInfoRangeUUID est déclarée ainsi :
		 create column family TermInfoRangeUUID with comparator = 'CompositeType(UTF8Type, UUIDType, UTF8Type)'
		 
		 La 1ere partie du composite est l'uuid du document codé en UTF8
		 La 1ere partie du composite est l'uuid du document codé en bytes  !!!!
		 La 1ere partie du composite est une chaîne correspondant à la version du document ("0.0.0")
		 */
		
		Composite c = new Composite();
		c.addComponent(docUUID, StringSerializer.get());
		c.addComponent(ConvertHelper.hexStringToByteArray("00000000000000000000000000000000"), BytesArraySerializer.get());
		c.addComponent("", StringSerializer.get());
		return c;
	}

	private byte[] getTermInfoRangeUUIDSliceBytes(String docUUID) {
		Composite c = getTermInfoRangeUUIDSliceComposite(docUUID);
		return new CompositeSerializer().toBytes(c);
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
	public void testDumpIndexReference() throws Exception {
		dumper.dumpCF("IndexReference", 15);
	}

	@Test
	public void testDumpJobs() throws Exception {
		dumper.dumpCF("Jobs", 15);
	}
	@Test
	public void testDumpLifeCycleRules() throws Exception {
		dumper.dumpCF("LifeCycleRules", 2000);
	}

	@Test
	public void testDumpStaticDictionaries() throws Exception {
		dumper.dumpCF("StaticDictionaries", 15);
	}
	
	@Test
	public void testDumpIndexCounter() throws Exception {
		dumper.dumpCF("IndexCounter", 50);
	}
	
	@Test
	public void testDumpSystemEventLog() throws Exception {
		dumper.dumpCF("SystemEventLog", 150);
	}
	@Test
	public void SystemEventLogByTime() throws Exception {
		dumper.dumpCF("SystemEventLogByTime", 15);
	}	
	@Test
	public void testDumpDocEventLog() throws Exception {
		dumper.dumpCF("DocEventLog", 30);
	}
	@Test
	public void testDumpDocEventLogByTime() throws Exception {
		dumper.printColumnNameInHex = true;
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
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();
		CqlQuery<byte[],byte[],byte[]> cqlQuery = new CqlQuery<byte[],byte[],byte[]>(keyspace, bytesSerializer, bytesSerializer, bytesSerializer);
		//String query = "select * from DocInfo where Key ='" + ConvertHelper.stringToHex("DOCUBASE") + "efbfbf" + ConvertHelper.stringToHex("dd258958-24ff-486e-84b8-4f6b2714aaff") + "'";
		//String query = "select * from DocInfo where Key ='" + ConvertHelper.stringToHex("DOCUBASE") + "efbfbf" + ConvertHelper.stringToHex("d6db9900-4e83-401f-ac82-21e88b804503") + "'";
		//String query = "select * from BasesReference where Key ='" + ConvertHelper.stringToHex("SAE") + "'";
		String query = "select * from Documents where Key ='" + ConvertHelper.stringToHex("dd258958-24ff-486e-84b8-4f6b2714aaff_v1") + "'";
		//String query = "select * from SystemEventLog WHERE KEY >'" + ConvertHelper.stringToHex("20110819000000000") + "' LIMIT 100";
		//String query = "select * from DocEventLogByTime WHERE KEY >'" + ConvertHelper.stringToHex("20110819000000000") + "' LIMIT 100";
		//String query = "select * from DocEventLog WHERE KEY >'" + ConvertHelper.stringToHex("d6db9900-4e83-401f-ac82-21e88b804503") + "' LIMIT 50";
		cqlQuery.setQuery(query);
		QueryResult<CqlRows<byte[],byte[],byte[]>> result = cqlQuery.execute();
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
