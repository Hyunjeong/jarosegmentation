package analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import distance.JaroDistance;
import distance.JaroWinklerDistance_Suff;

public class Main {
	static String locationOfLogPerActivity = "output/logPerActivity/";
	static String locationOfLogStream = "output/logStream.txt";
	static String locationOfActivities = "activities.txt";
	static String locationOfSensors = "sensors.txt";
	static String locationOfOutputSimilarityPerActivity = "output/similarityPerActivity/";

	static String locationOfExperimentStream = "experimetStream/";

	static HashMap<String, String> sensorList = new HashMap<String, String>();
	static ArrayList<String> activityList = new ArrayList<String>();

	static ArrayList<ArrayList<String>> instanceOfOneActivityList = new ArrayList<ArrayList<String>>();
	public static void computeSimilarityOfInstancesWithinOneActivity() throws IOException{
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

		File dirOutput = new File(locationOfOutputSimilarityPerActivity);
		if(!dirOutput.exists()){
			dirOutput.mkdirs();
		}

		String activityName;
		BufferedWriter brOutputSummary = new BufferedWriter(new FileWriter(new File(locationOfOutputSimilarityPerActivity + "summary.txt")));
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

			File outputSimilarityPerOneActivity = new File(locationOfOutputSimilarityPerActivity + activityName + ".txt");
			BufferedWriter bwSimilarityPerOneActivity = new BufferedWriter(new FileWriter(outputSimilarityPerOneActivity));
			float tmpDistance;
			float sum = 0;
			float numOfPair = 0;
			for(int x = 0; x < instanceOfOneActivityList.size(); x++){
				for(int y = x + 1; y < instanceOfOneActivityList.size(); y++){
					JaroWinklerDistance_Suff jwd = new JaroWinklerDistance_Suff();
					tmpDistance = jwd.getDistance(instanceOfOneActivityList.get(x), instanceOfOneActivityList.get(y), 0.1f, 0.1f);
					sum += tmpDistance;
					bwSimilarityPerOneActivity.write(tmpDistance + " ");
					numOfPair += 1.0f;
				}
			}
			bwSimilarityPerOneActivity.write("\naverage: " + Float.toString((sum / numOfPair)));
			bwSimilarityPerOneActivity.close();
			brOutputSummary.write(Float.toString((sum / numOfPair)) + "\n");
			//			System.out.println(activityName + " : " + instanceOfOneActivityList.size());
			//			for(int k = 0; k < instanceOfOneActivityList.size(); k++){
			//				System.out.println(instanceOfOneActivityList.get(k).size());
			//			}
			instanceOfOneActivityList = new ArrayList<ArrayList<String>>();
		}
		brOutputSummary.close();
	}
	// param : activity name
	public static void main(String[] args) throws IOException {
		String inputActivity;
		int lengthOfInstance;
		int lengthOfStream;
		computeSimilarityOfInstancesWithinOneActivity();

		// input file의 제일 첫 줄은 스트림에서 해당 activity의 길이
		// 두 번째 줄은 전체 스트림의 길이
		File inputDir = new File(locationOfExperimentStream);
		File[] inputList = inputDir.listFiles();
		File streamDir;
		File[] streamList;
		File logDir;
		File[] logList;

		BufferedReader brInputStream;
		BufferedWriter bwOutput;

		BufferedReader brLog;
		// Grooming, ...
		for(int i = 0; i < inputList.length; i++){
			inputActivity = inputList[i].getName();
			streamDir = new File(locationOfExperimentStream + inputActivity);
			streamList = streamDir.listFiles();
			logDir = new File(locationOfLogPerActivity + inputActivity);
			logList = logDir.listFiles();

			// 0, ...
			for(int j = 0; j < streamList.length; j++){
				bwOutput = new BufferedWriter(new FileWriter(new File("output_" + inputActivity + "_"+ streamList[j].getName())));
				// log 하나씩 비교
				for(int l = 0; l < logList.length; l++){
					brInputStream = new BufferedReader(new FileReader(new File(streamList[j].getPath())));
					lengthOfInstance = Integer.valueOf(brInputStream.readLine());
					lengthOfStream = Integer.valueOf(brInputStream.readLine());
					
					ArrayList<String> input = new ArrayList<String>();
					brLog = new BufferedReader(new FileReader(new File(logList[l].getPath())));
					ArrayList<String> log = new ArrayList<String>();
					String tmp;
					while((tmp = brLog.readLine()) != null){
						log.add(tmp);
					}
					
//					for(int m = 0; m < lengthOfInstance / 2; m++){
//						input.add(brInputStream.readLine());
//					}

					// window 늘려가면서 비교 
					for(int k = 0; k < lengthOfStream; k++){

//					for(int k = lengthOfInstance / 2; k < lengthOfStream; k++){
						input.add(brInputStream.readLine());
						JaroDistance jd = new JaroDistance();
						bwOutput.write(jd.getDistance(input, log) + "\t");
					}
					bwOutput.write("\n");
				}
				bwOutput.close();
			}
		}

	}
}
