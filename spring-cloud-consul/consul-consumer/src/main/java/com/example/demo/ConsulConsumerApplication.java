package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulConsumerApplication {

	@Autowired
	private LoadBalancerClient loadBalancer;

	@RequestMapping("/call")
	public String call() {
		String serverName = "consul-service";
		ServiceInstance serviceInstance = loadBalancer.choose(serverName);
		String url = serviceInstance.getUri().toString();

		String callServiceResult = new RestTemplate().getForObject(url + "/getProducer", String.class);

		System.out.println("ServiceId：" + serviceInstance.getServiceId());
		System.out.println("callServiceResult:"+callServiceResult);
		return callServiceResult;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsulConsumerApplication.class, args);
	}
}
