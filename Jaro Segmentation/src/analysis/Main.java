package analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import distance.JaroWinklerDistance;

public class Main {
	static String locationOfLogPerActivity = "output/logPerActivity/";
	static String locationOfLogStream = "output/logStream.txt";
	static String locationOfActivities = "activities.txt";
	static String locationOfSensors = "sensors.txt";
	static String locationOfOutputSimilarityPerActivity = "output/similarityPerActivity/";

	static HashMap<String, String> sensorList = new HashMap<String, String>();
	static ArrayList<String> activityList = new ArrayList<String>();

	static ArrayList<ArrayList<String>> instanceOfOneActivityList = new ArrayList<ArrayList<String>>();
	public static void main(String[] args) throws IOException {
		String tmpLine;
		String[] tmpLineSplited;
		File activityFile = new File(locationOfActivities);
		File sensorFile = new File(locationOfSensors);

		BufferedReader brActivity = new BufferedReader(new FileReader(activityFile));
		BufferedReader brSensor = new BufferedReader(new FileReader(sensorFile));

		while((tmpLine = brActivity.readLine()) != null){
			activityList.add(tmpLine);
		}

		while((tmpLine = brSensor.readLine()) != null){
			tmpLineSplited = tmpLine.split("\t");
			sensorList.put(tmpLineSplited[0], tmpLineSplited[1]);
		}

		File logPerActivityFile = new File(locationOfLogPerActivity);
		File[] activityFileList = logPerActivityFile.listFiles();
		File oneInstanceOfOneActivity;
		BufferedReader brLogsPerActivity;
		
		String activityName;

		for(int i = 0; i < activityFileList.length; i++){

			File[] instanceFileList = activityFileList[i].listFiles();
			activityName = activityFileList[i].getName();

			for(int j = 0; j < instanceFileList.length; j++){
//				System.out.println("doing instance: " + instanceFileList[j].getName());
				brLogsPerActivity = new BufferedReader(new FileReader(instanceFileList[j]));
				ArrayList<String> instance = new ArrayList<String>();
				String event;

				while((event = brLogsPerActivity.readLine()) != null){
					instance.add(event);
				}
				instanceOfOneActivityList.add(instance);
			}
			
//			float tmpDistance;
//			float sum = 0;
//			for(int x = 0; x < instanceOfOneActivityList.size(); x++){
//				for(int y = x + 1; y < instanceOfOneActivityList.size(); y++){
//					JaroWinklerDistance jwd = new JaroWinklerDistance();
//					tmpDistance = jwd.getDistance(instanceOfOneActivityList.get(x), instanceOfOneActivityList.get(y));
//					sum += tmpDistance;
//					System.out.println();
//				}
//			}
			
//			System.out.println(activityName + " : " + instanceOfOneActivityList.size());
//			for(int k = 0; k < instanceOfOneActivityList.size(); k++){
//				System.out.println(instanceOfOneActivityList.get(k).size());
//			}
//			instanceOfOneActivityList = new ArrayList<ArrayList<String>>();
		}	
	}
}
