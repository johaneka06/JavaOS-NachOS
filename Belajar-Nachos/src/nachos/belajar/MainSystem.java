package nachos.belajar;

public class MainSystem {

	BelajarNachos nacho = new BelajarNachos();
	
	public MainSystem() {
		nacho.println("Halo");
		nacho.println("Aku baru belajar nachos lho. Sampai part 2 (serial console) nih");
		
		String name = nacho.read();
		nacho.println("Yang diinput: " + name);
		
		int angka = nacho.readInt();
		nacho.println("Angka yang diinput: " + angka);
	}

}
