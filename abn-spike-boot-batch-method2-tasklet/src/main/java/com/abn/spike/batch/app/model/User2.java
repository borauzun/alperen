package com.abn.spike.batch.app.model;

public class User2 {

	
	private String EXTERNAL_REF;// VARCHAR(15),
	private String EXTERNAL_UID; //VARCHAR(100),
	private String PROCESS_ID; //VARCHAR(100),
	private double BC_NUMBER; //DECIMAL(12,0),
	private String PROCESS_STATUS;//  CHAR(1),
	private String IBAN;// VARCHAR(34),
	private String THIRD_PARTY_URL;// VARCHAR(200),
	public String getEXTERNAL_REF() {
		return EXTERNAL_REF;
	}
	public void setEXTERNAL_REF(String eXTERNAL_REF) {
		EXTERNAL_REF = eXTERNAL_REF;
	}
	public String getEXTERNAL_UID() {
		return EXTERNAL_UID;
	}
	public void setEXTERNAL_UID(String eXTERNAL_UID) {
		EXTERNAL_UID = eXTERNAL_UID;
	}
	public String getPROCESS_ID() {
		return PROCESS_ID;
	}
	public void setPROCESS_ID(String pROCESS_ID) {
		PROCESS_ID = pROCESS_ID;
	}
	public double getBC_NUMBER() {
		return BC_NUMBER;
	}
	public void setBC_NUMBER(double bC_NUMBER) {
		BC_NUMBER = bC_NUMBER;
	}
	public String getPROCESS_STATUS() {
		return PROCESS_STATUS;
	}
	public void setPROCESS_STATUS(String pROCESS_STATUS) {
		PROCESS_STATUS = pROCESS_STATUS;
	}
	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}
	public String getTHIRD_PARTY_URL() {
		return THIRD_PARTY_URL;
	}
	public void setTHIRD_PARTY_URL(String tHIRD_PARTY_URL) {
		THIRD_PARTY_URL = tHIRD_PARTY_URL;
	}
	
	
}
