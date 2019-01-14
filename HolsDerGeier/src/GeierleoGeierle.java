import java.util.ArrayList;

/**
 * 
 * @author (Tim Borsekowsky)
 * @version (V1.0 - 11.01.2019)
 */
public class GeierleoGeierle extends HolsDerGeierSpieler {

	private ArrayList<Integer> nochZuGewinnen = new ArrayList<Integer>();
	private ArrayList<Integer> vomGegnerNochNichtGelegt = new ArrayList<Integer>();
	private ArrayList<Integer> nochNichtGespielt = new ArrayList<Integer>();
	private int gegnerArray[] = new int[15];
	private int meinArray[] = new int[15];
	private int letzteKarte;
	private int letzteAusgespielteKarte;

	/**
	 * Hier definieren Sie den Konstruktor fuer Objekte Ihrer Klasse (falls Sie
	 * einen eigenen brauchen) IntelligentererGeier
	 */
	public GeierleoGeierle() {
	}

	public void reset() {
		nochZuGewinnen.clear();
		for (int i = 10; i > -6; i--)
			nochZuGewinnen.add(i);
		vomGegnerNochNichtGelegt.clear();
		for (int i = 15; i > 0; i--)
			vomGegnerNochNichtGelegt.add(i);
		for (int i = 15; i > 0; i--)
			nochNichtGespielt.add(i);
	}

	public int gibKarte(int naechsteKarte) {
		int ret = -99;
		int letzteKarteGegner = letzterZug();
		
		gegnerArray = clearArray(gegnerArray);
		meinArray = clearArray(meinArray);
		
		if (letzteKarteGegner != -99)
			vomGegnerNochNichtGelegt.remove(vomGegnerNochNichtGelegt.indexOf(letzteKarteGegner));
		
		nochZuGewinnen.remove(nochZuGewinnen.indexOf(naechsteKarte));
		ret = spielzug(naechsteKarte);
		if(letzteKarte == letzteKarteGegner) {
			
		ret = spielzug(naechsteKarte+letzteAusgespielteKarte);
		}
		
		nochNichtGespielt.remove(nochNichtGespielt.indexOf(ret));
		letzteKarte = ret;
		letzteAusgespielteKarte = naechsteKarte;
		return ret;
		
	}

	public int getMax(int[] array) {
		int max = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max)
				max = array[i];
		}
		return max;
	}

	public int getMin(int[] array) {
		int min = 99;
		for (int i = 0; i < array.length; i++) {
			if (array[i] < min && array[i] != 0)
				min = array[i];
		}

		return min;
	}

	public int getAverage(int[] array) {
		int avg = 0;
		int summe = 0;
		int teiler = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != 0) {
				summe += array[i];
				teiler++;
			}
		}
		avg = (int) (summe / teiler);
		return avg;

	}

	public int random(int[] array) {
		int rdm = (int) Math.random() * array.length;
		int ret = array[rdm];
		while (ret == 0) {
			rdm = (int) Math.random() * array.length;
		}
		return ret;

	}

	public boolean checkAvailable(int[] array, int zahl) {

		boolean check = false;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == zahl && array[i] != 0) {
				check = true;
				break;
			}
		}
		return check;
	}

	public int[] setRegion(int[] array, ArrayList<Integer> arraylist, int grenze1, int grenze2) {

		int zaehler = 0;
		for (int i = 0; i < arraylist.size(); i++) {
			if (arraylist.get(i) >= grenze1 && arraylist.get(i) <= grenze2) {
				array[zaehler] = arraylist.get(i);
				zaehler++;
			}
		}
		return array;
	}
	
	public boolean checkRegion(int[] array) {
		boolean check = false;
		int summe = 0;
		for (int i = 0; i < array.length; i++) {
			summe += array[i];
	}
		if(summe!=0)
			check = true;
		return check;
	}
	

	public int[] clearArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
		return array;
	}
	
	public int spielzug(int kartenwert) {
		int ret = -99;
		int letzteKarteGegner = letzterZug();

		if (kartenwert < 0) {

			gegnerArray = setRegion(gegnerArray, vomGegnerNochNichtGelegt, 6, 10);
			meinArray = setRegion(meinArray, nochNichtGespielt, 6, 10);
			if(checkRegion(meinArray)==false) {
				meinArray = setRegion(meinArray, nochNichtGespielt,1,15);
			}
			

			switch (kartenwert) {

			case -5:
				ret = getMax(meinArray);
				break;

			case -4:
				if (getMax(gegnerArray) <= getMax(meinArray) - 1 && checkAvailable(meinArray, getMax(meinArray) - 1)) {
					ret = getMax(meinArray) - 1;
				} else if (getMax(gegnerArray) > getMax(meinArray)) {
					if (checkAvailable(meinArray, getAverage(meinArray))) {
						ret = getAverage(meinArray);
					} else {
						ret = getMin(meinArray);
					}
				} else {
					ret = random(meinArray);
				}
				break;

			case -3:
				if (checkAvailable(meinArray, getAverage(meinArray))) {
					ret = getAverage(meinArray);
				} else {
					ret = getMin(meinArray);
				}
				break;
			
			case -2:
				if (getMin(gegnerArray) <= getMin(meinArray)) {
					ret = getMin(meinArray);
				} else if (getMin(gegnerArray) > getMin(meinArray)) {
					if (checkAvailable(meinArray, getAverage(meinArray) - 1)) {
						ret = getAverage(meinArray) - 1;
					} else {
						ret = getMin(meinArray);
					}
				}
				break;
			
			case -1:
				ret = getMin(meinArray);
				break;
			
			default:
				ret = getMax(meinArray);
				break;
			}
			
		}

		else if (kartenwert > 0 && kartenwert < 6) {

			gegnerArray = setRegion(gegnerArray, vomGegnerNochNichtGelegt, 1, 5);
			meinArray = setRegion(meinArray, nochNichtGespielt, 1, 5);
			if(checkRegion(meinArray)==false) {
				meinArray = setRegion(meinArray, nochNichtGespielt,1,15);
			}


			switch (kartenwert) {
			
			case 5:
				ret = getMax(meinArray);
				break;

			case 4:
				if (getMax(gegnerArray) <= getMax(meinArray) - 1 && checkAvailable(meinArray, getMax(meinArray) - 1)) {
					ret = getMax(meinArray) - 1;
				} else if (getMax(gegnerArray) > getMax(meinArray)) {
					if (checkAvailable(meinArray, getAverage(meinArray))) {
						ret = getAverage(meinArray);
					} else {
						ret = getMax(meinArray);
					}
				} else {
					ret = random(meinArray);
				}
				break;

			case 3:
				if (checkAvailable(meinArray, getAverage(meinArray))) {
					ret = getAverage(meinArray);
				} else {
					ret = getMin(meinArray);
				}
				break;

			case 2:
				if (getMin(gegnerArray) <= getMin(meinArray)) {
					ret = getMin(meinArray);
				} else if (getMin(gegnerArray) > getMin(meinArray)) {
					if (checkAvailable(meinArray, getAverage(meinArray) - 1)) {
						ret = getAverage(meinArray) - 1;
					} else {
						ret = getMin(meinArray);
					}
				}
				break;

			case 1:
				ret = getMin(meinArray);
				break;
			}


		} else if (kartenwert > 5 && kartenwert <= 10) {

			gegnerArray = setRegion(gegnerArray, vomGegnerNochNichtGelegt, 11, 15);
			meinArray = setRegion(meinArray, nochNichtGespielt, 11, 15);
			if(checkRegion(meinArray)==false) {
				meinArray = setRegion(meinArray, nochNichtGespielt,1,15);
			}

			
			switch (kartenwert) {

			case 10:
				ret = getMax(meinArray);
				break;

			case 9:
				if (getMax(gegnerArray) <= getMax(meinArray) - 1 && checkAvailable(meinArray, getMax(meinArray) - 1)) {
					ret = getMax(meinArray) - 1;
				} else if (getMax(gegnerArray) > getMax(meinArray)) {
					if (checkAvailable(meinArray, getAverage(meinArray))) {
						ret = getAverage(meinArray);
					} else {
						ret = getMax(meinArray);
					}
				} else {
					ret = random(meinArray);
				}
				break;
			case 8:
				if (checkAvailable(meinArray, getAverage(meinArray))) {
					ret = getAverage(meinArray);
				} else {
					ret = getMin(meinArray);
				}
				break;
			case 7:
				if (getMin(gegnerArray) <= getMin(meinArray)) {
					ret = getMin(meinArray);
				} else if (getMin(gegnerArray) > getMin(meinArray)) {
					if (checkAvailable(meinArray, getAverage(meinArray) - 1)) {
						ret = getAverage(meinArray) - 1;
					} else {
						ret = getMin(meinArray);
					}
				}
				break;
			case 6:
				ret = getMin(meinArray);
				break;
			}
		}else if(kartenwert > 10) {
			meinArray = setRegion(meinArray, nochNichtGespielt, 1, 15);
			ret=getMax(meinArray);
		}
			
		return ret;
		
	}
	

	
}
