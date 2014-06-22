package excalibur.game.logic.networkadapter;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadController {
	public static Queue<Thread> threads = new LinkedList<Thread>();
	public static void addThread(Thread thread){
		threads.offer(thread);
		if (threads.size()==1) {
			thread.start();
		} 				
	}
	public static boolean nextThread(){
		threads.poll();
		if (threads.size()>=1) {
			System.out.println("阻塞的进程开始跑");
			threads.peek().start();
//			threads.poll().start();
			return true;
		} else {
			return false;
		}
	}
}
