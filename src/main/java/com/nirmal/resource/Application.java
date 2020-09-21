package com.nirmal.resource;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Application.
 */
@SpringBootApplication
@RestController
@RequestMapping("/")
public class Application {

	/**
	 * Init.
	 * @return
	 */
	@PostConstruct
	@RequestMapping("/info")
	public String info(){
		Runtime runtime = Runtime.getRuntime();

		final NumberFormat format = NumberFormat.getInstance();

		final long maxMemory = runtime.maxMemory();
		final long allocatedMemory = runtime.totalMemory();
		final long freeMemory = runtime.freeMemory();
		final long mb = 1024 * 1024;
		final String mega = " MB";
		StringBuffer sb = new StringBuffer();
		sb = sb.append("========================== Memory Info ==========================").append("\n\r")
		.append("Free memory: " + format.format(freeMemory / mb) + mega).append("\n")
		.append("Allocated memory: " + format.format(allocatedMemory / mb) + mega).append("\n")
		.append("Max memory: " + format.format(maxMemory / mb) + mega).append("\n")
		.append("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega).append("\n")
		.append("=================================================================\n");
		String val = sb.toString();
		System.out.println(val);
		return val;
	}

	/**
	 * Cpu async load, consistant response time.
	 *
	 * @param load     the load
	 * @param duration the duration
	 * @return the string
	 */
	@RequestMapping("/cpu/duration/async")
	public String cpu(@RequestParam(required=false,defaultValue="0.8") Double load,
	                  @RequestParam(required=false,defaultValue="2000") long duration) {
		System.out.println("Load:" + load+ ", Duration: "+duration);

		new Thread(){
			@Override
			public void run() {
				long startTime = System.currentTimeMillis();
				try {
					// Loop for the given duration
					while (System.currentTimeMillis() - startTime < duration) {
						// Every 100ms, sleep for the percentage of unladen time
						if (System.currentTimeMillis() % 100 == 0) {
							long millis = (long) Math.floor((1 - load) * 100);
							System.out.println(millis);
							Thread.sleep(millis);
						}
					}
					System.out.println("Request Completed " + new Date());
				} catch (InterruptedException e) {

				}
			}
		}.start();
		String val = "This is Document App" + new Date();
		System.out.println(val);
		return val;
	}

	/**
	 * CPU iterations
	 *
	 * @param iteration the iteration
	 * @return the string
	 * @throws InterruptedException the interrupted exception
	 */
	@RequestMapping("/cpu/iteration/sync")
	public String cpuForIteration(@RequestParam(required=false,defaultValue="2000") long iteration) throws InterruptedException {
		System.out.println("Iteration: "+iteration);

		// Loop for the given duration
		for (int i = 0; i < iteration; i++) {
			Math.floor((1 - Math.random()) * 100L);
		}
		System.out.println("Request Completed "+ new Date());
		String val = "This is Document App" + new Date();
		System.out.println(val);
		return val;
	}

	/**
	 * Memory Jumps.
	 *
	 * @param iteration the iteration
	 * @param pause     the pause
	 * @return the string
	 * @throws InterruptedException the interrupted exception
	 */
	@RequestMapping("/memory")
	public String memory(@RequestParam(required=false,defaultValue="30") long iteration,
	                     @RequestParam(required=false,defaultValue="10") long pause) throws InterruptedException {
		System.out.println("Iteration:" + iteration+ ", Pause: "+pause + " ms");
		List arrayList = new ArrayList();
		for (int i = 0; i < iteration; i++) {
			byte b[] = new byte[1048576];
			arrayList.add(b);
			Thread.sleep(pause);
		}
		String val = "This is Document App" + new Date();
		System.out.println(val);
		return val;
	}

	/**
	 * Used to mimic system crash
	 *
	 * @param code the code
	 * @return the string
	 * @throws InterruptedException the interrupted exception
	 */
	@RequestMapping("/exit")
	public String exit(@RequestParam(required=false,defaultValue="-1") int code) throws InterruptedException {
		System.out.println("Invoking Exit: " + code);
		new Thread(){
			@Override
			public void run() {
				try { Thread.sleep(2000L); } catch (InterruptedException e) { }
				System.exit(code);
			}
		}.start();

		return "Success";
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
