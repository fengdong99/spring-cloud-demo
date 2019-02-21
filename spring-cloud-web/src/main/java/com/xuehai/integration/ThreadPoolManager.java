package com.xuehai.integration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 负责管理线程池
 * @author
 */
public class ThreadPoolManager {
	private ExecutorService service;
	private static ThreadPoolManager manager;
	private Future<?> futrue;
	
	private ThreadPoolManager(){
		service = Executors.newFixedThreadPool(100);
	}
	
	public static ThreadPoolManager getInstance(){
		if(manager==null)
		{
			manager= new ThreadPoolManager();
		}
		return manager;
	}
	
	public void addTask(Runnable runnable){
		futrue = service.submit(runnable);
	}
	
	public void cacelTask(){
		if(futrue != null ){
			futrue.cancel(true);
		}
	}
	public void shutDown(){
		if(service != null ){
			service.shutdown();
		}
		manager = null;
	}
}
