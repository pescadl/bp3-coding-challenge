public class Test{
	public static void main(String[] args){
		TaskManager a = new TaskManager("task-2.json");

		System.out.println(a.getNumOpen("2016-02-22T22:24:56Z"));
		System.out.println(a.getNumClosed("2016-02-22T22:24:56Z"));
		System.out.println(a.getNumOpened("2015-02-22T22:24:56Z", "2015-04-22T22:24:56Z"));
		System.out.println(a.getNumClosed("2015-02-22T22:24:56Z", "2015-04-22T22:24:56Z"));
		System.out.println(a.getMostRecentTaskName(634));
		System.out.println(a.getNumTasks(634));
		System.out.println(a.getAssigneeNumOpen("Impact 2014"));
		System.out.println(a.getAssigneeNumClosed("Impact 2014"));
	}
}
