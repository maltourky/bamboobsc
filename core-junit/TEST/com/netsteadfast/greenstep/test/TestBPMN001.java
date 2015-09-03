package com.netsteadfast.greenstep.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.netsteadfast.greenstep.base.AppContext;

public class TestBPMN001 {
	
	@Test
	public void deployment() throws Exception {	
		
		InputStream inputStreamBpmn = new FileInputStream( "/home/git/bamboobsc/core-doc/TestProcess001.bpmn" );
		InputStream inputStreamPng = new FileInputStream( "/home/git/bamboobsc/core-doc/TestProcess001.png" );		
		RepositoryService repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
		Deployment deployment = repositoryService
				.createDeployment()
				.name("流程測試001")
				.addInputStream("TestProcess001.bpmn", inputStreamBpmn)
				.addInputStream("TestProcess001.png", inputStreamPng)				
				.deploy();
		System.out.println("ID: " + deployment.getId() + " , Name: " + deployment.getName());
		
	}
	
	@Test
	public void startProcess() throws Exception {
		
		RuntimeService runtimeService = (RuntimeService) AppContext.getBean("runtimeService");
		ProcessInstance process = runtimeService.startProcessInstanceById("myProcess:1:4");
		System.out.println("DeploymentId: " + process.getDeploymentId() );
		System.out.println("ActivityId: " + process.getActivityId() );
		System.out.println("Name: " + process.getName() );
		System.out.println("ProcessDefinitionId: " + process.getProcessDefinitionId());
		System.out.println("ProcessDefinitionKey: " + process.getProcessDefinitionKey());
		System.out.println("ProcessDefinitionName: " + process.getProcessDefinitionName());
		
	}
	
	@Test
	public void deleteProcess() throws Exception {
		
		RepositoryService repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
		repositoryService.deleteDeployment("1");
		//repositoryService.deleteDeployment("1", true); no need it
		
	}
	
	@Test
	public void queryTask() throws Exception {
		
		TaskService taskService = (TaskService) AppContext.getBean("taskService");
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("張三").list();
		if (tasks == null || tasks.size()<1 ) {
			System.out.println("no task.");
			return;
		}
		for (Task task : tasks) {
			this.printTask(task);
		}
		
	}
	
	@Test
	public void queryProcessDefinition() throws Exception {
		
		RepositoryService repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		List<ProcessDefinition> processDefs = processDefinitionQuery
			.processDefinitionKey("myProcess")
			.orderByProcessDefinitionVersion()
			.desc()
			.list();
		for (ProcessDefinition pd : processDefs) {
			System.out.println( pd.getId() + " , " + pd.getName() + " , " 
					+ pd.getKey() + " , " + pd.getVersion() );
			ProcessDefinitionImpl pdObj = (ProcessDefinitionImpl)repositoryService.getProcessDefinition(pd.getId());
			System.out.println(pdObj.getActivities());
		}
		
	}
	
	@Test
	public void queryHistory() throws Exception {
		
		HistoryService historyService = (HistoryService) AppContext.getBean("historyService");
		List<HistoricTaskInstance> taskInstances = historyService
				.createHistoricTaskInstanceQuery()
				.finished()
				.list();
		for (HistoricTaskInstance taskInst : taskInstances) {
			System.out.println(taskInst.getId() + " , " + taskInst.getName() + " , " 
					+ taskInst.getFormKey() + " , " + taskInst.getAssignee() );
		}
	}
	
	@Test
	public void queryFlowImage() throws Exception {
		
		String deploymentId = "1";
		RepositoryService repositoryService = (RepositoryService) AppContext.getBean("repositoryService");
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		for (String name : names) {
			System.out.println( name );
			if (name.endsWith(".png") || name.endsWith(".bmp")) {
				InputStream is = repositoryService.getResourceAsStream(deploymentId, name);
				FileUtils.copyInputStreamToFile(is, new File("/tmp/" + name));
			}
		}
		
	}
	
	@Test
	public void completeTask() throws Exception {
				
		TaskService taskService = (TaskService) AppContext.getBean("taskService");
		List<Task> tasks = taskService.createTaskQuery().list();
		if (tasks == null || tasks.size()<1 ) {
			System.out.println("no task.");
			return;
		}
		for (Task task : tasks) {
			taskService.setAssignee(task.getId(), "張三");
			taskService.complete(task.getId());
			this.printTask(task);
		}
		
	}
	
	private void printTask(Task task) throws Exception {
		System.out.println("ID: " + task.getId() + " , Name: " + task.getName() 
				+ " , FORM_KEY: " + task.getFormKey() 
				+ " , Assignee: " + task.getAssignee());
	}
	

}
