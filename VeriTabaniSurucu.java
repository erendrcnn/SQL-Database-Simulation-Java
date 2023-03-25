import java.util.Scanner;

public class VeriTabaniSurucu {
	public static void main(String[] args) {
		databaseSimulator veriTabanim = new databaseSimulator();

		Scanner keyboard = new Scanner(System.in);

		while(true){
			System.out.print("etuDB>> ");
			String input = keyboard.nextLine();
			String[] parameters = input.split(" ");

			if (input.equalsIgnoreCase("exit")) { break; }

			if ( parameters[0].equals("CREATE") && parameters[1].equals("TABLE") && parameters[2].equals("FROM")){
				try {
					veriTabanim.createTable(input);
					// System.out.println("BILGI: Veritabani icerisinde yeni bir tablo olusturuldu.");
				} catch (Exception e) {
					System.out.println("HATA: Belirtilen dosya ya da dosya yolu bulunamadi.");
				}
			} else if ( parameters.length >= 3 && parameters[0].equals("SELECT") && parameters[2].equals("FROM")) {
				System.out.println("============[ RESULTS ]============");
				try {
				for (String data: veriTabanim.query(input)) {
					if (!data.equals(""))
						System.out.println(data);
				}
				} catch (NullPointerException ignored){}
				System.out.println("===================================");
			} else if (parameters.length == 1) {
				try {
					veriTabanim.printSchema(input);
				} catch (NullPointerException ignored){}
			} else {
				System.out.println("HATA: Lutfen gecerli bir komut giriniz.\n" +
						           "      Komut Listesi: [CREATE, SELECT]");
			}


		}
	}
}