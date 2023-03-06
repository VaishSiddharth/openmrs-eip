package org.openmrs.eip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {
	
	public static final String OPENMRS_DATASOURCE_NAME = "openmrsDataSource";
	
	public static final String WATCHDOG_EXECUTOR_NAME = "OpenMRS DB connection watchdog";
	
	public static final int WATCHDOG_EXECUTOR_SHUTDOWN_TIMEOUT = 5000;
	
	public static final String BEAN_OPENMRS_DS_HEALTH_INDICATOR = "watcherOpenmrsDsHealthIndicator";
	
	public static final String LIQUIBASE_BEAN_NAME = "springLiquibase";
	
	public static final String COMMON_PROP_SOURCE_BEAN_NAME = "commonPropSource";
	
	public static final String PROP_PACKAGES_TO_SCAN = "jpaPackagesToScan";
	
	public static final String MGT_DATASOURCE_NAME = "mngtDataSource";
	
	public static final String HTTP_HEADER_AUTH = "Authorization";
	
	public static final String PROP_WATCHED_TABLES = "eip.watchedTables";
	
	public static final String MGT_TX_MGR_NAME = "mngtTransactionManager";
	
	public static final String EX_PROP_RESOURCE_NAME = "resourceName";
	
	public static final String EX_PROP_RESOURCE_ID = "resourceId";
	
	public static final String EX_PROP_SUB_RESOURCE_NAME = "subResourceName";
	
	public static final String EX_PROP_SUB_RESOURCE_ID = "subResourceId";
	
	public static final String EX_PROP_RESOURCE_REP = "resourceRepresentation";
	
	public static final String EX_PROP_IS_SUB_RESOURCE = "isSubResource";
	
	public static final String EX_PROP_CONCEPT_SOURCE = "conceptSource";
	
	public static final String EX_PROP_CONCEPT_CODE = "conceptCode";
	
	public static final String ROUTE_ID_GET_ENTITY_BY_ID = "get-entity-by-uuid-from-openmrs";
	
	public static final String ROUTE_ID_GET_CONCEPT_BY_MAPPING = "get-concept-by-mapping-from-openmrs";
	
	public static final String URI_GET_ENTITY_BY_ID = "direct:" + ROUTE_ID_GET_ENTITY_BY_ID;
	
	public static final String URI_GET_CONCEPT_BY_MAPPING = "direct:" + ROUTE_ID_GET_CONCEPT_BY_MAPPING;
	
	public static final List<String> ORDER_SUBCLASS_TABLES = Arrays.asList("test_order", "drug_order", "referral_order");
	
	public static final List<String> ORDER_TABLES;
	
	public static final List<String> SUBCLASS_TABLES;
	
	static {
		List<String> orderTables = new ArrayList();
		orderTables.add("orders");
		orderTables.addAll(ORDER_SUBCLASS_TABLES);
		ORDER_TABLES = Collections.unmodifiableList(orderTables);
		
		List<String> subclassTables = new ArrayList();
		subclassTables.add("patient");
		subclassTables.addAll(ORDER_SUBCLASS_TABLES);
		SUBCLASS_TABLES = Collections.unmodifiableList(subclassTables);
	}
	
}
