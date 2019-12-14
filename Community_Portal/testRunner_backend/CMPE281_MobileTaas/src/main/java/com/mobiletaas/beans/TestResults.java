package com.mobiletaas.beans;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


public class TestResults {
	private String appName;
	public TestResults(String appName, String status, String testType, String platform, Date duration, int totalTests,
			int failed, int passed) {
		super();
		this.appName = appName;
		this.status = status;
		this.testType = testType;
		this.platform = platform;
		this.duration = duration;
		this.totalTests = totalTests;
		this.failed = failed;
		this.passed = passed;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String testType;
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	private String projectARN;
	public String getProjectARN() {
		return projectARN;
	}
	public void setProjectARN(String projectARN) {
		this.projectARN = projectARN;
	}
	private String testPackageARN;
	public String getTestPackageARN() {
		return testPackageARN;
	}
	public void setTestPackageARN(String testPackageARN) {
		this.testPackageARN = testPackageARN;
	}
	private String devicePoolARN;
	public String getDevicePoolARN() {
		return devicePoolARN;
	}
	public void setDevicePoolARN(String devicePoolARN) {
		this.devicePoolARN = devicePoolARN;
	}
	
	Map<String,Integer> results =new HashMap<String,Integer>();
	public Map<String, Integer> getResults() {
		return results;
	}
	public void setResults(Map<String, Integer> results) {
		this.results = results;
	}
	private String platform ;
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	private Date duration;
	public Date getDuration() {
		return duration;
	}
	public void setDuration(Date date) {
		this.duration = date;
		
		
	}
	private int totalTests;
	public int getTotalTests() {
		return totalTests;
	}
	public void setTotalTests(int totalTests) {
		this.totalTests = totalTests;
	}
	private int failed;
	public int getFailed() {
		return failed;
	}
	public void setFailed(int failed) {
		this.failed = failed;
	}

	private int passed;
	public int getPassed() {
		return passed;
	}
	public void setPassed(int passed) {
		this.passed = passed;
	}
	
}
