package nachos.belajar;

import nachos.machine.Machine;
import nachos.machine.SerialConsole;
import nachos.threads.Semaphore;

public class BelajarNachos {
	private SerialConsole sercon = Machine.console();
	private char temp;
	private Semaphore sem = new Semaphore(0);
	//Semaphore punya 2 value P dan V. P = 0 = Pause, V = 1 = resume
	
	public BelajarNachos() {
		//Proses input
		Runnable receive = new Runnable() {
			
			@Override
			public void run() {
				temp = (char) sercon.readByte();
				sem.V();
			}
		};
		
		//Proses output
		Runnable send = new Runnable() {
			
			@Override
			public void run() {
				sem.V();
			}
		};
		
		/*
		Mengatur interrupt yang terjadi ketika melakukan input ataupun output.
		Input, berarti interrupt yg receive langsung ter-trigger ketika proses input.
		Send, berarti interrupt yang send akan ter-trigger ketika proses output.
		*/
		sercon.setInterruptHandlers(receive, send); 
	}
	
	public String read() {
		String result = "";
		
		do {
			sem.P();
			if(temp != '\n') result += temp;
		} while(temp != '\n');
		
		return result;
	}
	
	public int readInt() {
		int res = -1;
		boolean flag = false;
		
		do {
			flag = false;
			try {
				res = Integer.parseInt(read());
			} catch (Exception e) {
				flag = true;
			}
		} while(flag);
		
		
		return res;
	}
	
	public void print(String str) {
		for(int i = 0; i < str.length(); i++) {
			sercon.writeByte(str.charAt(i));
			sem.P();
		}
	}
	
	public void println(String str) {
		print(str + '\n');
	}
}
