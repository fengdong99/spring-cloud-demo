package com.xuehai.web.tool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 队列
 */
public class QueueTopicUtil {

	private static volatile QueueTopicUtil blockingQueue = null;
	private final int queueSize = 100;
	private final int subQueueSize = 1000;

	/**
	 * @see Integer.MAX_VALUE 基于链表, FIFO(先进先出)
	 */
	LinkedBlockingQueue<Map<String, Object>> queue = new LinkedBlockingQueue<>(queueSize);
	
	/**
	 * 主题列表
	 */
	Set<String> topicKey = new HashSet<>();
	
	/**
	 * 队列分发器
	 */
	ConcurrentHashMap<String, LinkedBlockingQueue<Object>> dispatcherQueue = new ConcurrentHashMap<String, LinkedBlockingQueue<Object>>();

	private static AtomicLong num = new AtomicLong(0);

	
	private static final String topicA = "topicA";
	private static final String topicB = "topicB";
	
	QueueTopicUtil() {}

	public ExecutorService getExecutorService() {
		return ThreadPool.service;
	}

	/**
	 * 单列：静态内部类法
	 *
	 */
	private static class ThreadPool {
		private final static ExecutorService service = Executors.newFixedThreadPool(20);
	}

	/**
	 * 单例：双重检查锁
	 */
	public static QueueTopicUtil getInstance() {
		if (blockingQueue == null) {
			synchronized (QueueTopicUtil.class) {
				if (blockingQueue == null) {
					blockingQueue = new QueueTopicUtil();
				}
			}
		}
		return blockingQueue;
	}

	public int getQueueSize() {
		return queue.size();
	}
	
	public ConcurrentHashMap<String, LinkedBlockingQueue<Object>> getDispatcherQueue() {
		return dispatcherQueue;
	}

	/**
	 * 阻塞
	 */
	public void put(Map<String, Object> obj) {
		try {
			queue.put(obj);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 阻塞
	 */
	public synchronized void put(final String topic,Object object) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put(topic, object);
			queue.put(map);
			System.out.println("生产者：" + map);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 非阻塞
	 */
	public synchronized boolean add(final String topic,Object object) throws InterruptedException {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put(topic, object);
			System.out.println("生产者：" + map); 
			return queue.add(map);
		} catch (Exception e) {
			System.out.println("队列已满！");
		}
		return false;
	}

	/**
	 * consume
	 */
	public Map<String, Object> take() throws InterruptedException {
		return queue.take();
	}

	class produce implements Runnable {
		private String threadName;
		private String topicName;
		private Object obj;

		produce() {}

		produce(String threadName,String topicName, Object obj) {
			this.threadName = threadName;
			this.topicName = topicName;
			this.obj = obj;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				QueueTopicUtil.getInstance().put(topicName, "原子数序列:"+ num.getAndIncrement() + ";主题:" + topicName  + ";线程名:" + threadName);
			}
		}

	}

	class dispatcher implements Callable<Map<String, Object>> {
		private Map<String, Object> map;

		dispatcher() {}

		dispatcher(Map<String, Object> map) {
			this.map = map;
		}

		@Override
		public Map<String, Object> call() throws Exception {
			LinkedBlockingQueue<Object> subQueue ;
			while(true) {
				
//				Thread.sleep(10);
				Map<String, Object> take = QueueTopicUtil.getInstance().take();
				String key = take.keySet().iterator().next();
				try {
					if(topicKey.contains(key)) {
						subQueue = dispatcherQueue.get(key);
						subQueue.add(take.get(key));//add(非阻塞)
						dispatcherQueue.put(key, subQueue);
					} else {
						topicKey.add(key);
						subQueue = new LinkedBlockingQueue<>(subQueueSize);
						subQueue.add(take.get(key));
						dispatcherQueue.put(key, subQueue);
					}
					System.out.println(key +"; 队列大小：" + subQueueSize + "; 已使用队列空间：" + (subQueueSize - subQueue.size()) + "; 剩余队列空间：" + subQueue.size() );
				} catch (Exception e) {
					System.out.println("error Queue full ; key:"+ key + " data：" + take.get(key));
					e.printStackTrace();
				} finally {
					//DOTO
				}
			}
		}

	}
	
	class consumeTopic implements Runnable {
		private String topic;

		consumeTopic() {
		}

		consumeTopic(String topic) {
			this.topic = topic;
		}

		@Override
		public void run() {
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			while (true) {
				try {
					LinkedBlockingQueue<Object> queue = dispatcherQueue.get(topic);
					Object take = queue.take();
					System.out.println("消费者：" + topic + " ; " + take);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}
	
	public void monitoring() {
		for (String key : topicKey) {
			LinkedBlockingQueue<Object> queueList = QueueTopicUtil.getInstance().getDispatcherQueue().get(key);
			System.out.println( "主队列剩余空间：" + (queueSize -  QueueTopicUtil.getInstance().getQueueSize()) + "; 主题个数："+ topicKey.size() + "; 主题名称："+ key +"; 队列大小：" +  queueList.size());
		}
	}
	

	public static void main(String[] args) throws Exception {
		
		Executors.newFixedThreadPool(2).submit(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					QueueTopicUtil.getInstance().monitoring();
				}
			}
		});
		Executors.newFixedThreadPool(2).submit(QueueTopicUtil.getInstance().new produce("thread1",topicA, ""));
		Executors.newFixedThreadPool(2).submit(QueueTopicUtil.getInstance().new produce("thread2",topicA, ""));
		Executors.newFixedThreadPool(2).submit(QueueTopicUtil.getInstance().new produce("thread3",topicB, ""));
		Executors.newFixedThreadPool(2).submit(QueueTopicUtil.getInstance().new produce("thread4",topicB, ""));

		Executors.newFixedThreadPool(10).submit(QueueTopicUtil.getInstance().new dispatcher());
		
		QueueTopicUtil.getInstance().getExecutorService().submit(QueueTopicUtil.getInstance().new consumeTopic(topicA));
		QueueTopicUtil.getInstance().getExecutorService().submit(QueueTopicUtil.getInstance().new consumeTopic(topicB));
		
	

	}
	
	public void test3() throws InterruptedException, ExecutionException {
		
		Map<String, Object> map = new HashMap<>();
		map.put("a", "1");
		Future<Map<String, Object>> submit = QueueTopicUtil.getInstance().getExecutorService().submit(QueueTopicUtil.getInstance().new dispatcher(map));
		System.out.println("阻塞返回：" + submit.get());
		
	}

	public void test1() throws InterruptedException {
		int i = 0;
		while (true) {
			Thread.sleep(1000);
			i++;
			if (i == 5) {
				System.out.println(i);
				break;
			}
		}

		System.out.println("end");
	}

}
