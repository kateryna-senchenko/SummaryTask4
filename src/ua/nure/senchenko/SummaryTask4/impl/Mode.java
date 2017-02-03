package ua.nure.senchenko.SummaryTask4.impl;

/**
 * enum that specifies a mode in which Services can be configured, contains field with db name
 */
public enum Mode {
	
	PROD("appDB"),
	TEST("testDB");
	
	private final String dbName;
	
	Mode(String dbName){
		this.dbName = dbName;
	}

	public String getDbName() {
		return dbName;
	}

}
