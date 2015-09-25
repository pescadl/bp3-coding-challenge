import java.io.File;
import java.io.IOException;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class JSONParser{

	public JSONParser(){/* do nothing */}

	public List<Task> parse(String filepath){
		StringBuilder input = new StringBuilder();
		try{
			Scanner sc = new Scanner(new File(filepath));
			while(sc.hasNextLine()){
				input.append(sc.nextLine().trim());
			}
		}
		catch(IOException e){/* do nothing */}

		return parseBrackets(input);
	}

	public List<Task> parseBrackets(StringBuilder input){
		List<Task> taskList = new ArrayList<>();
		
		boolean inBrackets = false;
		int bracketStack = 0;

		StringBuilder output = new StringBuilder();

		for(int i=0; i<input.length(); i++){
			if(input.charAt(i) == '}'){
				bracketStack--;
				if(bracketStack == 0){
					taskList.add(parseElement(output));

					output.delete(0,output.length());
					inBrackets = false;
				}
			}

			if(inBrackets){
				output.append(input.charAt(i));
			}

			if(input.charAt(i) == '{'){
				bracketStack++;
				inBrackets = true;
			}
		}

		return taskList;
	}

	public Task parseElement(StringBuilder input){
		Task task = new Task();
		String[] array = input.toString().split(",");

		Pattern pattern = Pattern.compile("\"(.+?)\": (.+),?");
		
		for(int i=0; i<array.length; i++){
			Matcher matcher = pattern.matcher(array[i]);

			while(matcher.find()){
				String s = matcher.group(2);
				Object o = new Object();
				if(s.charAt(0) == '\"'){
					o = s.substring(1, s.length()-1);
				}
				else if(s.charAt(0) == '{'){
					o = s;
				}
				else if(Character.isDigit(s.charAt(0))){
					o = Integer.parseInt(s);
				}
				else if(s.equals("null")){
					o = null;
				}
				task.add(matcher.group(1), o);
			}
		}

		return task;
	}
}

