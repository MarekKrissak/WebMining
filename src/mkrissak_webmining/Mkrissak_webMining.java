//Na priloženom logovacom súbore zopakujte postup, ktorý ste sa naučili za celý semester.
//
//    1. očistite dátový súbor od nepotrebných údajov (RequestMethod/Version, StatusCode, URL)
//    2. očistite dátový súbor od prístupov robotov vyhľadávacích služieb
//        2.1 identifikujte robotov na základe prístupu k súboru robots.txt (URL);
//        2.2 identifikujte robotov na základe poľa User-Agent (Agent).
//    3. vyselektujte z dátového súboru len vaše skúmané obdobie a vytvorte premennú UnixTime
//    4. identifikujte používateľov na základe IP adresy a poľa User-Agent
//    5. identifikujte sedenia na základe priemeru a kvartilu (použite hodnoty priemeru a kvartilu vypočítané v predchádzajúcich zadaniach)
//    6. extrahujte sekvenčné pravidlá pre identifikované sedenia:
//        6.1 A1 (cookie STT_Mean)
//        6.2 A2 (cookie STT_Quartile)
//        6.3 B1 (IP_Agent STT_Mean)
//        6.4 B2(IP_Agent STT_Quartile)
//    7. z extrahovaných sekvenčných pravidiel vytvorte dátovú maticu (Pravidlo, A1(0/1), A1(podpora), A1(spoľahlivosť), A2(0/1), A2(podpora), A2(spoľahlivosť), B1(0/1), B1(podpora), B1(spoľahlivosť), B2(0/1), B2(podpora), B2(spoľahlivosť))
//    8. vyhodnoťte a interpretujte dosiahnuté výsledky
package mkrissak_webmining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Marek Krissak
 */
public class Mkrissak_webMining {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //deklaracia a inicializacia premennych
        FileReader vstupnySubor;
        BufferedReader citackaVstupnehoSuboru;

        FileWriter vystupnySubor;
        BufferedWriter zapisovacDoVystupnehoSuboru;

        ArrayList<String> poleSIPAdresamiRobotov = new ArrayList<>();
        ArrayList<String> kopiaVstupnehoSuboru = new ArrayList<>();
        String citanyRiadok;
        String[] poleNaExtrakciuIPAdresy = new String[13];

        //otvor vstupny subor
        System.out.println("Otváram vstupný súbor");
        vstupnySubor = new FileReader("logOcistenyOdDatumov.log");
        citackaVstupnehoSuboru = new BufferedReader(vstupnySubor);
        System.out.println("Vstupný súbor otvorený");
        
        //vytvor a otvor vystupny subor
        System.out.println("Vytváram výstupný súbor");
        vystupnySubor = new FileWriter(new File("vystupnyTestovaciLog.log"));
        zapisovacDoVystupnehoSuboru = new BufferedWriter(vystupnySubor);
        System.out.println("Výstupný súbor vytvorený");

        //nacitaj vstupnySubor do premennej
        System.out.println("Načítavam vstupný súbor do premennej");
        while ((citanyRiadok = citackaVstupnehoSuboru.readLine()) != null) {
            kopiaVstupnehoSuboru.add(citanyRiadok);
            //najdi robotov a pridaj ich IP adresy do druheho pola
            System.out.println("Hľadám robotov");
            if (citanyRiadok.contains("robots.txt")) {
                System.out.println("Našiel som robota");
                poleNaExtrakciuIPAdresy = citanyRiadok.split(" ");
                poleSIPAdresamiRobotov.add(poleNaExtrakciuIPAdresy[0]);
            }
        }
        System.out.println("Vstupný súbor bol načítaný do premennej. "
                + "Ďalej pracujem s touto premennou");
        System.out.println("Všetky roboty boli nájdené");

        System.out.println("Odstraňujem robotov");
        //odstran robotov
        for (int i = 0; i < kopiaVstupnehoSuboru.size(); i++) {
            for (int j = 0; j < poleSIPAdresamiRobotov.size(); j++) {
                if (kopiaVstupnehoSuboru.get(i).contains(poleSIPAdresamiRobotov.get(j))) {
                    kopiaVstupnehoSuboru.remove(i);
                }
            }
        }
        System.out.println("Roboti boli odstránení.");

        System.out.println("Začínam mazať nechcené záznamy.");
        //mazeme nepotrebne veci
        for (int i = 0; i < kopiaVstupnehoSuboru.size(); i++) {
            //odstranenie metody POST
            if (kopiaVstupnehoSuboru.get(i).contains("POST")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s metódou POST");
            } //odstranenie png
            else if (kopiaVstupnehoSuboru.get(i).contains(".png")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .png");
            } //odstranenie css
            else if (kopiaVstupnehoSuboru.get(i).contains(".css")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .css");
            } //odstranenie js
            else if (kopiaVstupnehoSuboru.get(i).contains(".js")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .js");
            } //odstranenie jpg
            else if (kopiaVstupnehoSuboru.get(i).contains(".jpg")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .jpg");
            } //odstranenie flv
            else if (kopiaVstupnehoSuboru.get(i).contains(".flv")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .flv");
            } //odstranenie gif
            else if (kopiaVstupnehoSuboru.get(i).contains(".gif")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .gif");
            } //odstranenie ico
            else if (kopiaVstupnehoSuboru.get(i).contains(".ico")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .ico");
            } //odstranenie jpeg
            else if (kopiaVstupnehoSuboru.get(i).contains(".jpeg")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .jpeg");
            } //odstranenie swf
            else if (kopiaVstupnehoSuboru.get(i).contains(".swf")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .swf");
            } //odstranenie rss
            else if (kopiaVstupnehoSuboru.get(i).contains(".rss")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .rss");
            } //odstranenie xml
            else if (kopiaVstupnehoSuboru.get(i).contains(".xml")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .xml");
            } //odstranenie cur
            else if (kopiaVstupnehoSuboru.get(i).contains(".cur")) {
                kopiaVstupnehoSuboru.remove(i);
                System.out.println("Zmazal som záznam s príponou .cur");
            }
        }
        System.out.println("Nechcené záznamy boli zmazané.");

        //zapis premennej do vystupu
        System.out.println("Zapisujem do výstupného súboru.");
        for (int i = 0; i < kopiaVstupnehoSuboru.size(); i++) {
            zapisovacDoVystupnehoSuboru.write(kopiaVstupnehoSuboru.get(i));
            zapisovacDoVystupnehoSuboru.write("\n");
        }
        System.out.println("Hotovo.");
        
        //zatvor vstupny subor
        vstupnySubor.close();
        citackaVstupnehoSuboru.close();
        System.out.println("Vstupný súbor zatvorený.");

        //zatvor vystupny subor
        zapisovacDoVystupnehoSuboru.close();
        vystupnySubor.close();
        System.out.println("Výstupný súbor zatvorený.");
        
        System.out.println("Mazanie nepotrebných záznamov je dokončené.");
    }
}
