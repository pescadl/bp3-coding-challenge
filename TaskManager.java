import java.util.List;
import java.util.Date;

public class TaskManager{
	List<Task> taskList;

	public TaskManager(String filepath){
		JSONParser parser = new JSONParser();
		taskList = parser.parse(filepath);
	}

	// the current number of open tasks given a specific date
	public int getNumOpen(String date){
		int count = 0;
		Date now = reformatDate(date);

		for(Task task : taskList){
			Date opened = reformatDate((String)task.get("createDate"));
			if(opened.before(now) || opened.equals(now)){
				if(task.get("closeDate") == null){
					count++;
				}
				else{
					Date closed = reformatDate((String)task.get("closeDate"));
					if(closed.after(now)){
						count++;
					}
				}
			}
		}

		return count;
	}

	// the current number of closed tasks given a specific date
	public int getNumClosed(String date){
		int count = 0;
		Date now = reformatDate(date);

		for(Task task : taskList){
			if(task.get("closeDate") != null){
				Date closed = reformatDate((String)task.get("closeDate"));

				if(closed.before(now) || closed.equals(now)){
					count++;
				}
			}
		}

		return count;
	}

	// number of opened tasks in a time range
	public int getNumOpened(String startDate, String endDate){
		int count = 0;
		Date start = reformatDate(startDate);
		Date end = reformatDate(endDate);

		for(Task task : taskList){
			Date opened = reformatDate((String)task.get("createDate"));
			if((opened.equals(start) || opened.after(start)) && opened.before(end)){
				count++;
			}
		}

		return count;
	}

	// number of closed tasks in a time range
	public int getNumClosed(String startDate, String endDate){
		int count = 0;
		Date start = reformatDate(startDate);
		Date end = reformatDate(endDate);

		for(Task task : taskList){
			if(task.get("closeDate") != null){
				Date closed = reformatDate((String)task.get("closeDate"));
				if((closed.equals(start) || closed.after(start)) && closed.before(end)){
					count++;
				}
			}
		}

		return count;
	}

	// instanceId's most recent task
	public String getMostRecentTaskName(int instanceId){
		Date mostRecent = new Date(0,0,0,0,0,0);
		String mostRecentName = "";
		for(Task task : taskList){
			if((int)task.get("instanceId") == instanceId){
				if(reformatDate((String)task.get("createDate")).after(mostRecent)){
					mostRecent = reformatDate((String)task.get("createDate"));
					mostRecentName = (String)task.get("name");
				}
			}
		}

		return mostRecentName;
	}

	// instanceId's number of tasks
	public int getNumTasks(int instanceId){
		int count = 0;

		for(Task task : taskList){
			if((int)task.get("instanceId") == instanceId){
				count++;
			}
		}

		return count;
	}

	// assignee's total number of open tasks
	public int getAssigneeNumOpen(String assignee){
		int count = 0;

		for(Task task : taskList){
			if(((String)task.get("assignee")).equals(assignee)){
				if(task.get("closeDate") == null){
					count++;
				}
			}
		}

		return count;
	}

	// assignee's total number of closed tasks
	public int getAssigneeNumClosed(String assignee){
		int count = 0;
		
		for(Task task : taskList){
			if(((String)task.get("assignee")).equals(assignee)){
				if(task.get("closeDate") != null){
					count++;
				}
			}
		}

		return count;
	}

	// "yyyy-mm-ddThh:mm:ssZ" -> java.util.Date
	private Date reformatDate(String s){
		int year = Integer.parseInt(s.substring(0,4));
		int month = Integer.parseInt(s.substring(5,7));
		int day = Integer.parseInt(s.substring(8,10));
		int hour = Integer.parseInt(s.substring(11,13));
		int min = Integer.parseInt(s.substring(14,16));
		int sec = Integer.parseInt(s.substring(17,19));
		return new Date(year, month, day, hour, min, sec);
	}

}

