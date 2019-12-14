package com.mobiletaas.controllers;

import java.io.File;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.devicefarm.model.GetRunResult;
import com.amazonaws.services.devicefarm.model.Run;
import com.amazonaws.services.devicefarm.model.ScheduleRunResult;
import com.mobiletaas.beans.AWSDeviceFarmService;
import com.mobiletaas.beans.TestConfig;
import com.mobiletaas.beans.TestResults;



@RestController
@RequestMapping(method = RequestMethod.GET ,value="/")
public class RunRetrieveController {
	
	@GetMapping(value="/RunResults1")
	public TestResults showResults1(@RequestParam(name = "appName") String appName 
			) throws Exception 
	{
		
		TestConfig config;
		String testType = "APPIUM_JAVA_TESTNG";
		MultipartFile appFile2 = null;
		MultipartFile testPackage = null;
		/*
		 * if(testPackageFile.isPresent()) { testPackage = testPackageFile.get();
		 * 
		 * }
		 */
	
		//AWSDeviceFarmService deviceFarmFactory = new AWSDeviceFarmService();
		AWSDeviceFarmService.init();
		
		
		//GetRunResult response = deviceFarmFactory.getRunResults();
		TestResults res=new TestResults("AppName","Completed","Type"
				, "Android",new Date(),3,
				1,2);
		
		return res;
		
	}
	
	@GetMapping(value="/RunResults")
	public TestResults showResults(@RequestParam(name = "appName") String appName 
			) throws Exception 
	{
		
		TestConfig config;
		String testType = "APPIUM_JAVA_TESTNG";
		MultipartFile appFile2 = null;
		MultipartFile testPackage = null;
		/*
		 * if(testPackageFile.isPresent()) { testPackage = testPackageFile.get();
		 * 
		 * }
		 */
	
		AWSDeviceFarmService deviceFarmFactory = new AWSDeviceFarmService();
		AWSDeviceFarmService.init();
		
		Run test = deviceFarmFactory.getResults(appName, appFile2, testType,
				testPackage);
		
		//GetRunResult response = deviceFarmFactory.getRunResults();
		TestResults res=new TestResults(test.getName(),test.getStatus(),test.getType()
				, "Android",test.getStarted(),test.getCounters().getTotal(),
				test.getCounters().getPassed() , test.getCounters().getFailed());
		
		return res;
		
	}
	
	@GetMapping(value="/RunResultsDynamic")
	public TestResults showResultsDynamic(@RequestParam(name = "appName") String appName ,
			@RequestParam(name = "appFile") MultipartFile appFile,
			@RequestParam(name = "testType") String testType,
			@RequestParam(name = "testPackageFile") MultipartFile testPackageFile
			) throws Exception 
	{
		
		TestConfig config;
		//String testType = "APPIUM_JAVA_TESTNG";
		//MultipartFile appFile2 = null;
	//	MultipartFile testPackage = null;
		/*
		 * if(testPackageFile.isPresent()) { testPackage = testPackageFile.get();
		 * 
		 * }
		 */
	
		AWSDeviceFarmService deviceFarmFactory = new AWSDeviceFarmService();
		AWSDeviceFarmService.init();
		
		Run test = deviceFarmFactory.getResults(appName, appFile, testType,
				testPackageFile);
		
		//GetRunResult response = deviceFarmFactory.getRunResults();
		TestResults res=new TestResults(test.getName(),test.getStatus(),test.getType()
				, "Android",test.getStarted(),test.getCounters().getTotal(),
				test.getCounters().getPassed() , test.getCounters().getFailed());
		
		return res;
		
	}
}
