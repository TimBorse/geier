
public class StartGeier {

	public static void main(String[] args) {
		IntelligentererGeier spieler1 = new IntelligentererGeier();
		GeierleoGeierle spieler2 = new GeierleoGeierle();
		HolsDerGeier hdg = new HolsDerGeier();
		try {
			hdg.neueSpieler(spieler1, spieler2);
			hdg.ganzesSpiel();
		}catch(Exception e) {
			System.out.println("Fehler");
			e.printStackTrace();
		}
		
	}

}
