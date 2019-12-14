package com.mobiletaas.beans;

import java.util.Date;

import javax.persistence.Entity;

import org.springframework.web.multipart.MultipartFile;


public class TestConfig {
	
	MultipartFile appFile;
	public TestConfig(String appName2, MultipartFile appFile2, String testType2, MultipartFile testPackageFile2) {
		// TODO Auto-generated constructor stub
		this.appName = appName2;
		this.appFile = appFile2;
		this.testType= testType2;
		this.testPackageFile = testPackageFile2;
	}
	public TestConfig(String appName2, MultipartFile appFile2, String testType2) {
		this.appName = appName2;
		this.appFile = appFile2;
		this.testType= testType2;
		// TODO Auto-generated constructor stub
	}
	public MultipartFile getAppFile() {
		return appFile;
	}
	public void setAppFile(MultipartFile appFile) {
		this.appFile = appFile;
	}
	public MultipartFile getTestPackageFile() {
		return testPackageFile;
	}
	public void setTestPackageFile(MultipartFile testPackageFile) {
		this.testPackageFile = testPackageFile;
	}




	MultipartFile testPackageFile;
	
	private String appName;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	
	
	
	
	private String testType;
	
	

}
