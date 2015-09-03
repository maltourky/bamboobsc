package com.netsteadfast.greenstep.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.netsteadfast.greenstep.base.AppContext;

public class TestBPMN001 {
	
	@Test
	public void deployment() throws Exception {	
		
		InputStream inputStreamBpmn = new FileInputStream( "/home/git/bamboobsc/core-doc/TestProcess001.bpmn" );
		InputStream inputStreamPng = new FileInputStream( "/home/git/bamboobsc/core-doc/TestProcess001.png" );		
		RepositoryService repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
		String deploymentId = repositoryService
				.createDeployment()
				.name("流程測試001")
				.addInputStream("TestProcess001.bpmn", inputStreamBpmn)
				.addInputStream("TestProcess001.png", inputStreamPng)				
				.deploy()
				.getId();
		System.out.println("ID: " + deploymentId);
		
	}
	
	@Test
	public void startProcess() throws Exception {
		
		RuntimeService runtimeService = (RuntimeService) AppContext.getBean("runtimeService");
		ProcessInstance process = runtimeService.startProcessInstanceById("myProcess:1:4");		
		System.out.println("DeploymentId: " + process.getDeploymentId() );
		System.out.println("ActivityId: " + process.getActivityId() );
		System.out.println("Name: " + process.getName() );
		
	}
	
	@Test
	public void completeTask() throws Exception {
				
		TaskService taskService = (TaskService) AppContext.getBean("taskService");
		List<Task> tasks = taskService.createTaskQuery().list();
		if (tasks == null ) {
			System.out.println("no task.");
			return;
		}
		for (Task task : tasks) {
			taskService.complete(task.getId());
			System.out.println("ID: " + task.getId());
			System.out.println("Name: " + task.getName());
		}
		
	}
	

}
