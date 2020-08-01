package nachos.networkLink;

import nachos.machine.Machine;
import nachos.machine.MalformedPacketException;
import nachos.machine.NetworkLink;
import nachos.machine.Packet;
import nachos.threads.Semaphore;

public class MainSystem {
	
	private NetworkLink nl = Machine.networkLink();
	private Console console = new Console();
	private Semaphore sem = new Semaphore(0); //Manage critical resource yang dipakai
	
	public MainSystem() {
		System.out.println("Ini Main System");
		
		console.WriteLn("NL Address: " + nl.getLinkAddress()); //NL Address dimulai dari 0
		
		Runnable receive = new Runnable() {
			
			@Override
			public void run() {
				try {
					Packet recv = nl.receive();
					String msg = new String(recv.contents);
					System.out.println("Message: " + msg);
				} catch (Exception e) {
					console.WriteLn("Tidak ada pesan");
				}
				sem.V();
				
			}
		};
		
		Runnable send = new Runnable() {
			
			@Override
			public void run() {
				sem.V();
				
			}
		};
		
		nl.setInterruptHandlers(receive, send);
		
		MainMenu();
	}
	
	private void MainMenu() {
		int opt = 0;
		do {
			console.WriteLn("Menu: ");
			console.WriteLn("1. Kirim Pesan");
			console.WriteLn("2. Terima Pesan");
			console.Write("Pilih: ");
			opt = console.nextInt();
			
			if(opt == 1) Kirim();
			else Terima();
		} while(opt <=  2);
	}
	
	private void Kirim() {
		console.Write("Input destinasi: ");
		int target = console.nextInt();
		
		console.Write("Pesan: ");
		String msg = console.nextStr();
		
		try {
			Packet p = new Packet(target, nl.getLinkAddress(), msg.getBytes());
			nl.send(p);
			sem.P();
		} catch (MalformedPacketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void Terima() {
		try {
			Packet recv = nl.receive();
			String msg = new String(recv.contents);
			console.WriteLn("Message: " + msg);
		} catch (Exception e) {
			console.WriteLn("Tidak ada pesan");
		}
		
	}

}
