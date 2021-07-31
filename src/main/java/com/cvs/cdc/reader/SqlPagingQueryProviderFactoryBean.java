/*
 * Copyright 2006-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cvs.cdc.reader;

import static com.cvs.cdc.reader.DatabaseType.DB2;
import static com.cvs.cdc.reader.DatabaseType.DB2VSE;
import static com.cvs.cdc.reader.DatabaseType.DB2ZOS;
import static com.cvs.cdc.reader.DatabaseType.DB2AS400;
import static com.cvs.cdc.reader.DatabaseType.DERBY;
import static com.cvs.cdc.reader.DatabaseType.H2;
import static com.cvs.cdc.reader.DatabaseType.HSQL;
import static com.cvs.cdc.reader.DatabaseType.MYSQL;
import static com.cvs.cdc.reader.DatabaseType.ORACLE;
import static com.cvs.cdc.reader.DatabaseType.POSTGRES;
import static com.cvs.cdc.reader.DatabaseType.SQLITE;
import static com.cvs.cdc.reader.DatabaseType.SQLSERVER;
import static com.cvs.cdc.reader.DatabaseType.SYBASE;
import static com.cvs.cdc.reader.DatabaseType.TERADATA;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.*;
import com.cvs.cdc.reader.DatabaseType;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Factory bean for {@link PagingQueryProvider} interface. The database type
 * will be determined from the data source if not provided explicitly. Valid
 * types are given by the {@link DatabaseType} enum.
 * 
 * @author Dave Syer
 * @author Michael Minella
 */
public class SqlPagingQueryProviderFactoryBean implements FactoryBean<PagingQueryProvider> {

	private DataSource dataSource;

	private String databaseType;

	private String fromClause;

	private String whereClause;

	private String selectClause;
	
	private String groupClause;

	private Map<String, Order> sortKeys;

	private Map<DatabaseType, AbstractSqlPagingQueryProvider> providers = new HashMap<>();


	/*{
		providers.put(DB2, new Db2PagingQueryProvider());
		providers.put(DB2VSE, new Db2PagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.DB2ZOS, new Db2PagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.DB2AS400, new Db2PagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.DERBY,new DerbyPagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.HSQL,new HsqlPagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.H2,new H2PagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.MYSQL,new MySqlPagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.ORACLE,new OraclePagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.POSTGRES,new PostgresPagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.SQLITE, new SqlitePagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.SQLSERVER,new SqlServerPagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.SYBASE,new SybasePagingQueryProvider());
		providers.put(com.cvs.cdc.reader.DatabaseType.TERADATA, new TeradataPagingQueryProvider());
	}*/
	
	/**
	 * @param groupClause SQL GROUP BY clause part of the SQL query string
	 */
	public void setGroupClause(String groupClause) {
		this.groupClause = groupClause;
	}

	/**
	 * @param databaseType the databaseType to set
	 */
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @param fromClause the fromClause to set
	 */
	public void setFromClause(String fromClause) {
		this.fromClause = fromClause;
	}

	/**
	 * @param whereClause the whereClause to set
	 */
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	/**
	 * @param selectClause the selectClause to set
	 */
	public void setSelectClause(String selectClause) {
		this.selectClause = selectClause;
	}

	@Override
	public String toString() {
		return "SqlPagingQueryProviderFactoryBean{" +
				"dataSource=" + dataSource +
				", databaseType='" + databaseType + '\'' +
				", fromClause='" + fromClause + '\'' +
				", whereClause='" + whereClause + '\'' +
				", selectClause='" + selectClause + '\'' +
				", groupClause='" + groupClause + '\'' +
				", sortKeys=" + sortKeys +
				", providers=" + providers +
				'}';
	}

	/**
	 * @param sortKeys the sortKeys to set
	 */
	public void setSortKeys(Map<String, Order> sortKeys) {
		this.sortKeys = sortKeys;
	}
	
	public void setSortKey(String key) {
		Assert.doesNotContain(key, ",", "String setter is valid for a single ASC key only");
		
		Map<String, Order> keys = new LinkedHashMap<>();
		keys.put(key, Order.ASCENDING);
		
		this.sortKeys = keys;
	}

	/**
	 * Get a {@link PagingQueryProvider} instance using the provided properties
	 * and appropriate for the given database type.
	 * 
	 * @see FactoryBean#getObject()
	 */
    @Override
	public PagingQueryProvider getObject() throws Exception {

		DatabaseType type;
		try {
			type = databaseType != null ? DatabaseType.valueOf(databaseType.toUpperCase()) : DatabaseType
					.fromMetaData(dataSource);
		}
		catch (MetaDataAccessException e) {
			throw new IllegalArgumentException(
					"Could not inspect meta data for database type.  You have to supply it explicitly.", e);
		}

		AbstractSqlPagingQueryProvider provider = providers.get(type);
		Assert.state(provider!=null, "Should not happen: missing PagingQueryProvider for DatabaseType="+type);

		provider.setFromClause(fromClause);
		provider.setWhereClause(whereClause);
		provider.setSortKeys(sortKeys);
		if (StringUtils.hasText(selectClause)) {
			provider.setSelectClause(selectClause);
		}
		if(StringUtils.hasText(groupClause)) {
			provider.setGroupClause(groupClause);
		}

		provider.init(dataSource);

		return provider;

	}

	/**
	 * Always returns {@link PagingQueryProvider}.
	 * 
	 * @see FactoryBean#getObjectType()
	 */
    @Override
	public Class<PagingQueryProvider> getObjectType() {
		return PagingQueryProvider.class;
	}

	/**
	 * Always returns true.
	 * @see FactoryBean#isSingleton()
	 */
    @Override
	public boolean isSingleton() {
		return true;
	}

}
