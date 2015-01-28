package parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	static String locationOfLog = "activities_data_tab.txt";
	static String locationOfActivities = "activities.txt";
	static String locationOfSensors = "sensors.txt";
	
	static String locationOfLogPerActivity = "output/logPerActivity/";
	static String locationOfLogStream = "output/logStream.txt";
	
	// <id, name>
	static HashMap<String, String> sensorList = new HashMap<String, String>();
	static ArrayList<String> activityList = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException {
		
		// TODO Auto-generated method stub
		File logFile = new File(locationOfLog);
		File activityFile = new File(locationOfActivities);
		File sensorFile = new File(locationOfSensors);
		File output_logStream = new File(locationOfLogStream);
		
		File output_perActivityDir = new File(locationOfLogPerActivity);
		if(!output_perActivityDir.exists())
			output_perActivityDir.mkdirs();
		
		BufferedReader brLog = new BufferedReader(new FileReader(logFile));
		BufferedReader brActivity = new BufferedReader(new FileReader(activityFile));
		BufferedReader brSensor = new BufferedReader(new FileReader(sensorFile));
		BufferedWriter bwStreamLog = new BufferedWriter(new FileWriter(output_logStream));
		BufferedWriter bwPerActivity;
		
		String tmpLine;
		String[] tmpLineSplited;
		while((tmpLine = brActivity.readLine()) != null){
			activityList.add(tmpLine);
		}
		
		while((tmpLine = brSensor.readLine()) != null){
			tmpLineSplited = tmpLine.split("\t");
			sensorList.put(tmpLineSplited[0], tmpLineSplited[1]);
		}
		
		while((tmpLine = brLog.readLine()) != null){
			tmpLineSplited = tmpLine.split("\t");
			if(activityList.contains(tmpLineSplited[0])){
				bwStreamLog.write(tmpLineSplited[0] + "\n");
				File dir = new File(locationOfLogPerActivity + tmpLineSplited[0]);
				if(!dir.exists())
					dir.mkdirs();
				File [] dirList = dir.listFiles();
				bwPerActivity = new BufferedWriter(new FileWriter(new File(dir +"/" + Integer.toString(dirList.length) + ".txt")));
				
				tmpLineSplited = brLog.readLine().split("\t");
				for(int i = 0; i < tmpLineSplited.length; i++){
					bwStreamLog.write(tmpLineSplited[i] + "\n");
					bwPerActivity.write(tmpLineSplited[i] + "\n");
				}
				
				bwPerActivity.close();
			}
		}
		bwStreamLog.close();
		
//		for(int i = 0; i < activityList.size(); i++)
//			System.out.println(activityList.get(i));
//		for (String key : sensorList.keySet()) {
//			System.out.println(key + " " + sensorList.get(key));
//		}
	}

}
