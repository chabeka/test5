package fr.urssaf.hectotest;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.SuperSlice;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SuperSliceQuery;

public class Dumper {

	Keyspace keyspace;
	PrintStream sysout;

	public Dumper(Keyspace k, PrintStream p) {
		keyspace = k;
		sysout = p;
	}
	
	public List<String> getKeys(String CFName, int count) throws Exception {
		StringSerializer stringSerializer = StringSerializer.get();
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();
		RangeSlicesQuery<String, String, byte[]> rangeSlicesQuery = HFactory.createRangeSlicesQuery(keyspace, stringSerializer, stringSerializer, bytesSerializer);
		rangeSlicesQuery.setColumnFamily(CFName);
		rangeSlicesQuery.setColumnNames("toto");
		QueryResult<OrderedRows<String, String, byte[]>> result = rangeSlicesQuery.execute();
		OrderedRows<String, String, byte[]> orderedRows = result.get();
		//sysout.println("Count " + orderedRows.getCount());
		ArrayList<String> list = new ArrayList<String>(count);
		for (Row<String, String, byte[]> row : orderedRows) {
			String key = row.getKey();
			list.add(key);
			//sysout.println("Key : " + key);
		}
		return list;
	}

	public List<String> getKeys(String CFName, String startKey, int count) throws Exception {
		StringSerializer stringSerializer = StringSerializer.get();
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();
		RangeSlicesQuery<String, String, byte[]> rangeSlicesQuery = HFactory.createRangeSlicesQuery(keyspace, stringSerializer, stringSerializer, bytesSerializer);
		rangeSlicesQuery.setColumnFamily(CFName);
		rangeSlicesQuery.setRange("", "", false, count);
		rangeSlicesQuery.setKeys(startKey, "");
		rangeSlicesQuery.setRowCount(count);
		rangeSlicesQuery.setReturnKeysOnly();
		QueryResult<OrderedRows<String, String, byte[]>> result = rangeSlicesQuery.execute();
		OrderedRows<String, String, byte[]> orderedRows = result.get();
		//sysout.println("Count " + orderedRows.getCount());
		ArrayList<String> list = new ArrayList<String>(count);
		for (Row<String, String, byte[]> row : orderedRows) {
			String key = row.getKey();
			String t = ConvertHelper.stringToHex(key);
			list.add(key);
			//sysout.println("Key : " + key);
		}
		return list;
	}

	/**
	 * Dump une column family
	 * @param CFName
	 * @param count
	 * @throws Exception
	 */
	public void dumpCF(String CFName, int count) throws Exception {
		StringSerializer stringSerializer = StringSerializer.get();
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();
		RangeSlicesQuery<byte[], String, byte[]> rangeSlicesQuery = HFactory
				.createRangeSlicesQuery(keyspace, bytesSerializer,
						stringSerializer, bytesSerializer);
		rangeSlicesQuery.setColumnFamily(CFName);
		//rangeSlicesQuery.setKeys(
		//		"SrMd8I1c￿0002dfa8-4085-45f9-9554-d036b105c968", "");
		rangeSlicesQuery.setRange("", "", false, count);
		rangeSlicesQuery.setRowCount(count);
		QueryResult<OrderedRows<byte[], String, byte[]>> result = rangeSlicesQuery
				.execute();
		dumpQueryResult(result);

	}
	
	/**
	 * Dump les colonnes d'une column family pour une clé donnée
	 * @param CFName
	 * @param key
	 * @throws Exception
	 */
	public void dumpCF(String CFName, String key) throws Exception {
		StringSerializer stringSerializer = StringSerializer.get();
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();
		RangeSlicesQuery<byte[], String, byte[]> rangeSlicesQuery = HFactory
				.createRangeSlicesQuery(keyspace, bytesSerializer,
						stringSerializer, bytesSerializer);
		rangeSlicesQuery.setColumnFamily(CFName);
		rangeSlicesQuery.setKeys(key.getBytes(), key.getBytes());
		rangeSlicesQuery.setRange("", "ZZZ", false, 1000);
		QueryResult<OrderedRows<byte[], String, byte[]>> result = rangeSlicesQuery
				.execute();
		dumpQueryResult(result);
	}

	void dumpCqlQueryResult(QueryResult<CqlRows<byte[], String, byte[]>> result) throws Exception {
		CqlRows<byte[], String, byte[]> orderedRows = result.get();
		if (orderedRows != null) {
			sysout.println("Count " + orderedRows.getCount());
			dumpRows(orderedRows);
		}
		else {
			sysout.println("Pas de résultat trouvé");
		}
	}

	private void dumpQueryResult(QueryResult<OrderedRows<byte[], String, byte[]>> result) throws Exception {
		OrderedRows<byte[], String, byte[]> orderedRows = result.get();
		sysout.println("Count " + orderedRows.getCount());
		dumpRows(orderedRows);
	}
	
	private void dumpRows(Iterable<Row<byte[], String, byte[]>> orderedRows) throws Exception {
		for (Row<byte[], String, byte[]> row : orderedRows) {
			String key = ConvertHelper.getReadableUTF8String(row.getKey());
			sysout.println("Key : " + key);
			ColumnSlice<String, byte[]> columnSlice = row.getColumnSlice();
			List<HColumn<String, byte[]>> columns = columnSlice.getColumns();
			dumpColumns(columns);
		}		
	}
	
	private void dumpColumns(List<HColumn<String, byte[]>> columns) throws Exception {
		for (HColumn<String, byte[]> column : columns) {
			String name = column.getName();
			byte[] value =  column.getValue();
			if (value.length > 200) {
				byte [] dst = new byte[200];
				System.arraycopy( value, 0, dst, 0, 200 );
				value = dst;
			}
			String s = "Name : " + name;
			String stringValue = ConvertHelper.getReadableUTF8String(value);
			s += " - StringValue : " + stringValue;
			if (value.length <=4) {
				int intValue = ConvertHelper.byteArrayToInt(value, 0);
				s += " - IntValue : " + intValue;
			}
			else if (value.length <=8) {
				long longValue = ConvertHelper.byteArrayToLong(value);
				s += " - longValue : " + longValue;
			}
			String hexValue = ConvertHelper.getHexString(value);
			s += " - hexValue : " + hexValue;
			sysout.println(s);
		}
	}

	public void dumpSCF(String CFName, int count) throws Exception {
		List<String> keys = getKeys(CFName, count);
		for (String key : keys) {
			dumpSCFRow(CFName, key, count);
		}
	}
	
	public void dumpSCF(String CFName, String startKey, int count) throws Exception {
		List<String> keys = getKeys(CFName, startKey, count);
		for (String key : keys) {
			dumpSCFRow(CFName, key, count);
		}
	}

	public void dumpSCF(String CFName, String startKey, int count, String scNameFilter) throws Exception {
		List<String> keys = getKeys(CFName, startKey, count);
		for (String key : keys) {
			dumpSCFRow(CFName, key, count, scNameFilter);
		}
	}

	/**
	 * Dump une ligne d'une super column family
	 * @param CFName
	 * @param count
	 * @throws Exception
	 */
	public void dumpSCFRow(String CFName, String key, int count) throws Exception {
		dumpSCFRow(CFName, key, count, null);
	}
	
	/**
	 * Dump une ligne d'une super column family
	 * @param CFName
	 * @param count
	 * @param scNameFilter : si non null : on affiche que les super-colonne dont le nom contient scNameFilter 
	 * @throws Exception
	 */
	public void dumpSCFRow(String CFName, String key, int count, String scNameFilter) throws Exception {
		
		StringSerializer stringSerializer = StringSerializer.get();
		BytesArraySerializer  bytesSerializer = BytesArraySerializer.get();
		SuperSliceQuery<String, String, String, byte[]> superSliceQuery = HFactory
			.createSuperSliceQuery(keyspace, stringSerializer, stringSerializer, stringSerializer, bytesSerializer);
		superSliceQuery.setColumnFamily(CFName);
		superSliceQuery.setRange("", "", false, count);
		superSliceQuery.setKey(key);
		QueryResult<SuperSlice<String, String, byte[]>> result = superSliceQuery.execute();
		SuperSlice<String,String,byte[]> superSlice = result.get();
		
		List<HSuperColumn<String, String, byte[]>> superColumns = superSlice.getSuperColumns();
		if (scNameFilter == null) {
			sysout.println("Clé de la super row : " + key);
			sysout.println("Nombre de super colonnes : " + superColumns.size());
		}
		
		// Parcours des super colonnes
		for (HSuperColumn<String, String, byte[]> superColumn : superColumns) {
			String superColumnName = superColumn.getName();
			if (scNameFilter != null) {
				if (!superColumnName.contains(scNameFilter)) {
					break;
				}
				else {
					sysout.println("Clé de la super row : " + key);
					sysout.println("Nombre de super colonnes : " + superColumns.size());
				}
			}
			sysout.println("SuperName : " + superColumnName);
			List<HColumn<String, byte[]>> columns = superColumn.getColumns();
			
			// Dump des colonnes
			dumpColumns(columns);
		}
		//sysout.println(result.getHostUsed().getHost());
	}
	
	
}
