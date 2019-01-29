package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulProducer1Application {
	@Autowired
	private LoadBalancerClient loadBalancer;

	@Value("${server.port}")
	String port ;
	@Value("${spring.application.name}")
	String name ;

	@RequestMapping("/getServer")
	public String getServer(){
		String serverName = "consul-service";
		ServiceInstance serviceInstance = loadBalancer.choose(serverName);
		return String.format("服务地址：%s;服务名称：%s",serviceInstance.getUri(),serviceInstance.getServiceId()) ;
	}

	@RequestMapping("/getProducer")
	public String getProducer(){
		return String.format("服务名称：%s ;服务端口：%s;",name,port) ;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsulProducer1Application.class, args);
	}
}
