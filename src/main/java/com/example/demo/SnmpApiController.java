package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SnmpApiController {

	

		@RequestMapping(value="/{ipaddressinfo:.*}",method=RequestMethod.GET)
		public void fetchingsnmpSystemInfo(HttpServletResponse response,@PathVariable("ipaddressinfo") String ipaddressinfo) throws IOException {
		
			
			 PrintWriter out = response.getWriter();
				SNMPManager client = new SNMPManager(ipaddressinfo+"/161");
			client.start();
			String output="";

			{
				Process p;
				String[] cmd2 = {"/bin/bash","-c","echo tcs123| snmpget -v 2c -c public "+ipaddressinfo+" 1.3.6.1.2.1.1.5.0"};
				p = Runtime.getRuntime().exec(cmd2);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(p.getInputStream()));
					String inputLine;
			        StringBuffer str = new StringBuffer();

			        while ((inputLine = br.readLine()) != null) {
			        	str.append(inputLine);
			        }
			        output =  after(str.toString(),": ");
			        output = output.replaceAll("\"", "");
			        out.print(output);
			        out.close();
			        
			} 
			
			
		}
		
		static String after(String value, String a) {
	        // Returns a substring containing all characters after a string.
	        int posA = value.lastIndexOf(a);
	        if (posA == -1) {
	            return "";
	        }
	        int adjustedPosA = posA + a.length();
	        if (adjustedPosA >= value.length()) {
	            return "";
	        }
	        return value.substring(adjustedPosA);
	    }

}
