package com.mobiletaas.beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.devicefarm.AWSDeviceFarm;
import com.amazonaws.services.devicefarm.AWSDeviceFarmClient;
import com.amazonaws.services.devicefarm.model.AWSDeviceFarmException;
import com.amazonaws.services.devicefarm.model.CreateDevicePoolRequest;
import com.amazonaws.services.devicefarm.model.CreateDevicePoolResult;
import com.amazonaws.services.devicefarm.model.CreateProjectResult;
import com.amazonaws.services.devicefarm.model.CreateUploadRequest;
import com.amazonaws.services.devicefarm.model.CreateUploadResult;
import com.amazonaws.services.devicefarm.model.GetRunRequest;
import com.amazonaws.services.devicefarm.model.GetRunResult;
import com.amazonaws.services.devicefarm.model.GetUploadRequest;
import com.amazonaws.services.devicefarm.model.GetUploadResult;
import com.amazonaws.services.devicefarm.model.Run;
import com.amazonaws.services.devicefarm.model.ScheduleRunRequest;
import com.amazonaws.services.devicefarm.model.ScheduleRunResult;
import com.amazonaws.services.devicefarm.model.ScheduleRunTest;
import com.amazonaws.services.devicefarm.model.Upload;
import com.amazonaws.services.devicefarm.model.UploadType;
import org.apache.http.entity.*;




public class AWSDeviceFarmService {
	
	private static final int TIMEOUT = 10;
	static AWSDeviceFarm deviceFarm;
	String devicePoolARN= "arn:aws:devicefarm:us-west-2::devicepool:082d10e5-d7d7-48a5-ba5c-b33d66efa1f5";
	
	
	
	
	public static AWSDeviceFarm getDeviceFarm() {
		return deviceFarm;
	}

	public static void setDeviceFarm(AWSDeviceFarm deviceFarm) {
		AWSDeviceFarmService.deviceFarm = deviceFarm;
	}

	//Initializing connection
	public static void init() throws Exception {

        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (C:\\Users\\dbnam\\.aws\\credentials), and is in valid format.",
                    e);
        }
        if(deviceFarm == null)
        {
        	 deviceFarm = AWSDeviceFarmClient.builder()
        	            .withCredentials(credentialsProvider)
        	            .withRegion("us-west-2")
        	            .build();
        }
       
    }
	
	public  File convert(MultipartFile file) throws IOException {
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile();
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}
	/*
	 * public String addFile(String title, MultipartFile file) throws IOException {
	 * AndroidFile appFile = new AndroidFile(title); appFile.setApplicationFile( new
	 * Binary(BsonBinarySubType.BINARY, file.getBytes())); appFile =
	 * fileRepo.insert(appFile); return appFile.getId(); }
	 */
	 
	/*
	 * public AndroidFile getFile(String id) { return fileRepo.findById(id).get(); }
	 */
	 public Upload upload(String projectArn,  Boolean synchronous,File file,String fileType)
		      throws InterruptedException, IOException, AWSDeviceFarmException {
		 // file= new File("C:/Users/dbnam/Downloads/HelloWorld_v1.0_apkpure.com.apk");
		 
		    CreateUploadRequest appUploadRequest =
		        new CreateUploadRequest()
		            .withName(UUID.randomUUID() + "_" + file.getName())
		            .withProjectArn(projectArn)
		            .withContentType("application/zip")
		            .withType(fileType);
		    Upload upload = deviceFarm.createUpload(appUploadRequest).getUpload();

		    CloseableHttpClient httpClient = HttpClients.createSystem();
		    HttpPut httpPut = new HttpPut(upload.getUrl());
		    httpPut.setHeader("Content-Type", upload.getContentType());
		    
		   // httpPut.setHeader("attachment; filename="file);

		//  File convFile = convert(file);
		@SuppressWarnings("deprecation")
		FileEntity entity = new FileEntity(file , "application/zip");
		  httpPut.setEntity(entity);

		   

		    Thread thread =
		        new Thread() {
		          public void run() {
		            HttpResponse response = null;
		            try {
		              response = httpClient.execute(httpPut);
		            } catch (IOException exception) {
		              throw new AWSDeviceFarmException(
		                  String.format("Error uploading file to AWS: %s", exception.getMessage()));
		            }
		            if (response.getStatusLine().getStatusCode() != 200) {
		              throw new AWSDeviceFarmException(
		                  String.format(
		                      "Upload returned non-200 responses: %d",
		                      response.getStatusLine().getStatusCode()));
		            }
		          }
		        };
		    thread.start();
		    //thread.run();
		    int progress = 0;
		/*
		 * while (thread.isAlive()) { int newProgress = entity.getProgress(); if
		 * (newProgress > progress) { // LOG.info("{}% completed {}", progress,
		 * file.getAbsolutePath()); progress = newProgress; } Thread.sleep(500); }
		 */
		    GetUploadResult describeUploadResult = null;
		    if (synchronous) {
		      while (true) {
		        GetUploadRequest describeUploadRequest = new GetUploadRequest().withArn(upload.getArn());
		         describeUploadResult = deviceFarm.getUpload(describeUploadRequest);
		        String status = describeUploadResult.getUpload().getStatus();

		        if ("SUCCEEDED".equalsIgnoreCase(status)) {
		         // LOG.info("Uploading {} succeeded: {}", file.getName(), describeUploadRequest.getArn());
		          break;
		        } else if ("FAILED".equalsIgnoreCase(status)) {
		        
		          throw new AWSDeviceFarmException(String.format("Upload %s failed!", upload.getName()));
		        } else {
		          try {
		            //LOG.info(
		              //  "Waiting for upload {} to be ready (current status: {})", file.getName(), status);
		            Thread.sleep(5000);
		          } catch (InterruptedException exception) {
		           // LOG.info("Thread interrupted while waiting for the upload to complete");
		            throw exception;
		          }
		        }
		      }
		    }
		    
			return describeUploadResult.getUpload();
		  }
	 
	//A project in Device Farm is a workspace that contains test runs. A run is a test of a single app against one or more devices.
	 public com.amazonaws.services.devicefarm.model.CreateProjectResult createNewProject(String projectName)
	 {
		 com.amazonaws.services.devicefarm.model.CreateProjectRequest request = new com.amazonaws.services.devicefarm.model.CreateProjectRequest()
				
				        .withName(projectName).withDefaultJobTimeoutMinutes(10);
				com.amazonaws.services.devicefarm.model.CreateProjectResult response = deviceFarm.createProject(request);
	return response;
	 }
	 public ScheduleRunResult startRun(String projectARN,String devicePoolARN,String testType,String testPackageARN)
	 {
		 
		 ScheduleRunRequest request = new ScheduleRunRequest()
			        .withProjectArn("arn:aws:devicefarm:us-west-2:767528473708:project:671d427d-545e-4eb6-862e-6ddac3552c85")
			        .withDevicePoolArn("arn:aws:devicefarm:us-west-2::devicepool:082d10e5-d7d7-48a5-ba5c-b33d66efa1f5")
			        .withName("TestRun")
			        .withTest(
			                new ScheduleRunTest().withType(testType).withTestPackageArn(
			                        "arn:aws:devicefarm:us-west-2:123456789101:test:EXAMPLE-GUID-123-456"));
			ScheduleRunResult response = deviceFarm.scheduleRun(request);
			return response;
	 }
	 // Passes results of the test run
	 public GetRunResult getRunResults()
	 { GetRunRequest request = new GetRunRequest()
			        .withArn("arn:aws:devicefarm:us-west-2:767528473708:run:671d427d-545e-4eb6-862e-6ddac3552c85/544d2df4-94db-451f-8752-a47e89a6b408");
			GetRunResult response = deviceFarm.getRun(request);
		return response;
		
	 }
		public File multiparttofile(MultipartFile multipart) throws IllegalStateException, IOException 
		{     File convfile = new File( multipart.getOriginalFilename());    
		multipart.transferTo(convfile);     
		return convfile; 
		} 

	 public Run getResults(String title, MultipartFile appFile, String testPackageType,  MultipartFile testPackagefile) throws AWSDeviceFarmException, InterruptedException, IOException {
		String projectARN = createNewProject(title).getProject().getArn();
		File appFile2;
		File testFile2;
		//File appFile2 = multiparttofile(appFile);
		if(appFile == null && testPackagefile == null)
		{
			appFile2 = new File("C:/Users/dbnam/Downloads/HelloWorld_v1.0_apkpure.com.apk");
			testFile2 = new File("C:/Users/dbnam/Downloads/aws-devicefarm-appium-0.1/target/zip-with-dependencies.zip");
		}
		else
		{
			appFile2 = multiparttofile(appFile);
			testFile2 = multiparttofile(appFile);
		}
		
		 CreateUploadRequest appUploadRequest =
			        new CreateUploadRequest()
			            .withName(UUID.randomUUID() + "_" + appFile2.getName())
			            .withProjectArn(projectARN)
			            .withContentType("application/octet-stream")
			            .withType("ANDROID_APP");
			    Upload upload = deviceFarm.createUpload(appUploadRequest).getUpload();

			    CloseableHttpClient httpClient = HttpClients.createSystem();
			    HttpPut httpPut = new HttpPut(upload.getUrl());
			    httpPut.setHeader("Content-Type", upload.getContentType());

			//  File convFile = convert(file);
			DocumentFileEntity entity = new DocumentFileEntity(appFile2 );
			  httpPut.setEntity(entity);

			   

			    Thread thread =
			        new Thread() {
			          public void run() {
			            HttpResponse response = null;
			            try {
			              response = httpClient.execute(httpPut);
			            } catch (IOException exception) {
			              throw new AWSDeviceFarmException(
			                  String.format("Error uploading file to AWS: %s", exception.getMessage()));
			            }
			            if (response.getStatusLine().getStatusCode() != 200) {
			              throw new AWSDeviceFarmException(
			                  String.format(
			                      "Upload returned non-200 responses: %d",
			                      response.getStatusLine().getStatusCode()));
			            }
			          }
			        };
			    thread.start();
			    //thread.run();
			    int progress = 0;
			  while (thread.isAlive()) {
			      int newProgress = entity.getProgress();
			      if (newProgress > progress) {
			     //   LOG.info("{}% completed {}", progress, file.getAbsolutePath());
			        progress = newProgress;
			      }
			      Thread.sleep(500);
			    }

			  String status;
			      while (true) {
			        GetUploadRequest describeUploadRequest = new GetUploadRequest().withArn(upload.getArn());
			        GetUploadResult describeUploadResult = deviceFarm.getUpload(describeUploadRequest);
			         status = describeUploadResult.getUpload().getStatus();

			        if ("SUCCEEDED".equalsIgnoreCase(status)) {
			         // LOG.info("Uploading {} succeeded: {}", file.getName(), describeUploadRequest.getArn());
			          break;
			        } else if ("FAILED".equalsIgnoreCase(status)) {
			        
			          throw new AWSDeviceFarmException(String.format("Upload %s failed!", upload.getName()));
			        } else {
			          try {
			            //LOG.info(
			              //  "Waiting for upload {} to be ready (current status: {})", file.getName(), status);
			            Thread.sleep(5000);
			          } catch (InterruptedException exception) {
			           // LOG.info("Thread interrupted while waiting for the upload to complete");
			            throw exception;
			          }
			        }
			      }
			    
		//Upload appUpload = upload(projectARN, true, appFile2,"ANDROID_APP");
		Upload testUpload = null ;
		 CreateUploadRequest testUploadRequest = null;
		 if(!(testPackageType.equalsIgnoreCase("BUILTIN_FUZZ")) && !(testPackageType.equalsIgnoreCase("BUILTIN_EXPLORER")) )
		 {
			// testFile2 = multiparttofile(testPackagefile);
			 String testPackagetype2 = testPackageType +"_TEST_PACKAGE";
			//testFile2= new File("/CMPE281_MobileTaas/zip-with-dependencies.zip");
			 testUpload = upload(projectARN, true, testFile2 ,testPackagetype2.toUpperCase());
		 }
		 /*else
		 {
			 
			 testUploadRequest= new CreateUploadRequest()
				            .withProjectArn(projectARN)
				            .withContentType("application/octet-stream")
				            .withType(testPackageType.toUpperCase())
				            .withName(title);
			 testUpload = deviceFarm.createUpload(testUploadRequest).getUpload();
		 }*/
			
		/*
		 * while(true) {
		 * if(deviceFarm.createUpload(testUploadRequest).getUpload().getStatus().
		 * equalsIgnoreCase("SUCCEEDED")) { break; } else { continue; } }
		 */
		 String testPackageARN;
		 String appARN;
		 if((status.equalsIgnoreCase("SUCCEEDED"))
				 && testUpload != null &&  (testUpload.getStatus().equalsIgnoreCase("SUCCEEDED")))
		 {
			  appARN = upload.getArn();
			  testPackageARN = testUpload.getArn();
			 
		 }
		 else
		 {
			 return null;
		 }
		 ScheduleRunRequest request;
		 if(testPackageARN.isEmpty())
		 {
			 request = new ScheduleRunRequest()
				        //You can get the Amazon Resource Name (ARN) of the project by using the list-projects CLI command.
				        .withProjectArn(projectARN)
				        //You can get the Amazon Resource Name (ARN) of the device pool by using the list-pools CLI command.
				        .withDevicePoolArn(devicePoolARN)
				        .withName("Run1")
				        .withTest(
				                new ScheduleRunTest().withType(testPackageType.toUpperCase()));
		 }
		 else
		 {
			 List<String> devices = new ArrayList<String>();
			 
			 request = new ScheduleRunRequest()
				     
				        .withProjectArn(projectARN)
				        .withAppArn(appARN)
				        //You can get the Amazon Resource Name (ARN) of the device pool by using the list-pools CLI command.
				        .withDevicePoolArn(devicePoolARN)
				        
				        .withName("Run1")
				        .withTest(
				                new ScheduleRunTest().withType(testPackageType.toUpperCase()).withTestPackageArn(
				                        testPackageARN));
		 }
		 
		 
		 ScheduleRunResult response = deviceFarm.scheduleRun(request);
		 Run runResult = waitForResult(response.getRun().getArn());	
		 
		/*
		 * while(true) { Run describeRunRequest = response.getRun();
		 * 
		 * runResult = new GetRunResult().withRun(describeRunRequest); //
		 * GetUploadResult describeUploadResult =
		 * deviceFarm.getUpload(describeUploadRequest); String runStatus =
		 * runResult.getRun().getStatus();
		 * 
		 * if ("COMPLETED".equalsIgnoreCase(runStatus)) { //
		 * LOG.info("Uploading {} succeeded: {}", file.getName(),
		 * describeUploadRequest.getArn()); break; } else if
		 * ("FAILED".equalsIgnoreCase(status)) {
		 * 
		 * throw new AWSDeviceFarmException(String.format("Upload %s failed!",
		 * upload.getName())); } else { try { //LOG.info( //
		 * "Waiting for upload {} to be ready (current status: {})", file.getName(),
		 * status); Thread.sleep(5000); } catch (InterruptedException exception) { //
		 * LOG.info("Thread interrupted while waiting for the upload to complete");
		 * throw exception; } }
		 * 
		 * }
		 */
		 return runResult;
		 
	 }
	 
	 
	 private Run waitForResult(String runArn) {
		    GetRunRequest getRunRequest = new GetRunRequest().withArn(runArn);
		    Run run = deviceFarm.getRun(getRunRequest).getRun();
		    
		    long startTime = System.currentTimeMillis();
		    long elapsedTime;
		    String status;
		    do {
		      long currentStartTime = System.currentTimeMillis();
		      elapsedTime = System.currentTimeMillis() - startTime;
		      run = deviceFarm.getRun(getRunRequest).getRun();
		      status = run.getStatus();
		     
		      if (elapsedTime > TIMEOUT * 60 * 1000) {
		        break;
		      }
		      do {
		        elapsedTime = System.currentTimeMillis() - currentStartTime;
		      } while (elapsedTime < 10000);
		    } while (!status.equals("COMPLETED"));
		    return run;
		  }


}
