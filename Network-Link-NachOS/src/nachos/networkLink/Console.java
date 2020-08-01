package nachos.networkLink;

import nachos.machine.Machine;
import nachos.machine.SerialConsole;
import nachos.threads.Semaphore;

//Class for scanner and reader
public class Console {
	
	private SerialConsole sercon = Machine.console();
	private char temp;
	private Semaphore sem = new Semaphore(0);
	
	public Console() {
		Runnable receive = new Runnable() {
			
			@Override
			public void run() {
				temp = (char) sercon.readByte();
				sem.V();
				
			}
		};
		
		Runnable send = new Runnable() {
			
			@Override
			public void run() {
				sem.V();
				
			}
		};
		
		sercon.setInterruptHandlers(receive, send);
	}
	
	public String nextStr() {
		String s = "";
		do {
			sem.P();
			if(temp != '\n') s += temp;
		} while(temp != '\n');
		return s;
	}
	
	public int nextInt() {
		int res = -1;
		boolean flag = false;
		
		do {
			flag = false;
			try {
				res = Integer.parseInt(nextStr());
			} catch (Exception e) {
				flag = true;
			}
		}while(flag);
		
		return res;
	}
	
	public void Write(String s) {
		for(int i = 0; i < s.length(); i++) {
			sercon.writeByte(s.charAt(i));
			sem.P();
		}
	}
	
	public void WriteLn(String s) {
		Write(s + '\n');
	}

}
