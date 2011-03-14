package com.don.mavenInvoker;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;


public class MavenInvoker
{	
	public static void runMaven(File mavenHome, File pomFile, List<String> goals) {
		
		InvocationRequest request = new DefaultInvocationRequest();
		try {
			
			request.setPomFile(pomFile);
			request.setBaseDirectory(pomFile.getParentFile());
			request.setGoals(goals);
			request.setShowErrors(true);
			
			Invoker invoker = new DefaultInvoker();
			invoker.setMavenHome(mavenHome);
			
			InvocationResult result = invoker.execute(request);
			
			if( result.getExitCode() != 0) {
				throw new IllegalStateException();
			}
			
		} catch (MavenInvocationException e) {
			
		}
		
	}
    
	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        List<String> goals = new ArrayList<String>();
        goals.add("clean");
        goals.add("package");
        
        URL main = MavenInvoker.class.getResource("MavenInvoker.class");
        if(!"file".equals(main.getProtocol())) {
        	throw new IllegalStateException("Main class is not stored in a file");
        }
        File mainClass = new File( main.getPath()  );
        File mainClassDir = mainClass.getParentFile();
        
        
        runMaven( new File(mainClassDir.toString() + "/../../../../../tools/apache-maven-3.0.3") ,
        		 new File( mainClassDir.toString() + "/../../../../../testProject/pom.xml"), 
        		 goals);
        
        
        
    }
}
