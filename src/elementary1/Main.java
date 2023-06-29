package elementary1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author k_tanabe
 * 書籍購入費ランキング
 */
public class Main {

	//定数化されたメッセージ
	public static final String NUMBER_OF_PEOPLE_MESSAGE = "社員の人数を入力して下さい。(整数)";
	public static final String NAME_OF_PERSON_MESSAGE = "社員の名前をスペース区切りで入力して下さい。（半角英小文字）";
	public static final String NUMBER_OF_BOOK_MESSAGE = "購入した本の数を入力して下さい。(整数)";
	public static final String NAME_AND_BOOK_MESSAGE = "回分の社員の名前と本の金額をスペース区切りで入力して下さい。（例：ヒューマン 1500）";
	public static final String INPUT_ERROR_MESSAGE = "正しく入力して下さい。";
	public static final String AMOUNT_OF_MONEY_ERROR_MESSAGE = "金額を正しく入力して下さい。";
	public static final String NAME_OF_PERSON_ERROR_MESSAGE = "入力された社員と登録された社員が一致しません。";
	public static final String NUMBER_OF_PEOPLE_ERROR_MESSAGE = "入力された名前の数と社員の人数が一致しません。";
	public static final String BOOK_MESSAGE = "冊目";
	public static final String RESULT_MESSAGE = "結果";
	public static final char CHAR_SPACE_MESSAGE = ' ';
	public static final String SPACE_MESSAGE = " "; 
	public static final String SPACE_CHARACTER_MESSAGE = "";
	public static final String INTEGER_CHECK = "[+-]?\\d*(\\.\\d+)?";

	//社員人数の格納用
	static int menberCount = 0;
	//書籍数の格納用
	static int bookCount = 0;
	//各自の名前と購入金額の合計格納用
	static Map<String, Integer> result = new HashMap<>();

	/**
	 * 説明：入力された情報から書籍購入費の高い順に表示
	 * 補足：不適切な入力が行われた場合は再入力する用の入力チェック実施
	 * @param args
	 */
	public static void main(String[] args) {

		String str  = null;
		String message = null;
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		try {
			System.out.println(NUMBER_OF_PEOPLE_MESSAGE);
			//入力が不適切な場合は再入力
			while(true) {
				str = br.readLine();
				//入力チェック(チェックフラグ１)
				message = inputCheck(str,1);
				//エラーメッセージがあるかチェック
				if(message.isEmpty()){
					//人数を格納
					menberCount = Integer.parseInt(str);
					//ループを抜けて次の処理へ
					break;
				}else {
					System.out.println(message);
				}
			}
			System.out.println(NAME_OF_PERSON_MESSAGE);
			//入力が不適切な場合は再入力
			while(true) {
				str = br.readLine();
				//入力チェック(チェックフラグ2)
				message = inputCheck(str,2);
				//エラーメッセージがあるかチェック
				if(message.isEmpty()){
					String[] menbers = str.toLowerCase().split(SPACE_MESSAGE);
					for(int i = 0; i<menberCount; i++) {
						//マップに名前と金額（0円）を追加
						result.put(menbers[i], 0);
					}
					//ループを抜けて次の処理へ
					break;
				}else {
					System.out.println(message);
				}
			}
			System.out.println(NUMBER_OF_BOOK_MESSAGE);
			//入力が不適切な場合は再入力
			while(true) {
				str = br.readLine();
				//入力チェック(チェックフラグ3)
				message = inputCheck(str,3);
				//エラーメッセージがあるかチェック
				if(message.isEmpty()){
					//本数を格納
					bookCount = Integer.parseInt(str);
					//ループを抜けて次の処理へ
					break;
				}else {
					System.out.println(message);
				}
			}
			System.out.println(bookCount + NAME_AND_BOOK_MESSAGE);
			//購入回数分繰り返す
			for(int k =0; k<bookCount; k++) {
				//入力が不適切な場合は再入力
				while(true) {
					System.out.println(k+1 + BOOK_MESSAGE);
					str = br.readLine();
					//入力チェック(チェックフラグ4)
					message = inputCheck(str,4);
					//エラーメッセージがあるかチェック
					if(message.isEmpty()){
						//入力チェック(チェックフラグ5)
						message = inputCheck(str,5);
						//エラーメッセージがあるかチェック
						if(message.isEmpty()) {
							String[] menberAndPrice = str.toLowerCase().split(SPACE_MESSAGE);
							//マップに名前と金額を追加（編集)
							result.put(menberAndPrice[0], Integer.parseInt(menberAndPrice[1])+result.get(menberAndPrice[0]));
							//ループを抜けて次の処理へ
							break;
						}else {
							System.out.println(message);
						}
					}else {
						System.out.println(message);
					}
				}
			}
			br.close();
			System.out.println(RESULT_MESSAGE);
			//マップ内の値を順番に表示
			for (String key : sortMap(result).keySet()) {
				System.out.println(key);
			}
		}catch(Exception e) {
		} 

	}

	/**
	 * 不適切な入力かの入力チェックメソッド
	 * @param str　入力チェック対象用
	 * @param flg　入力チェックの種類選択用
	 * @return　エラーメッセージ
	 */
	public static String inputCheck(String str, int flg){

		String[] menberAndPrice = null;

		switch(flg){
		case 1,3: 
			if(str.isEmpty() || !str.matches(INTEGER_CHECK)) return INPUT_ERROR_MESSAGE;
		break;
		case 2: 
			if(str.isEmpty()) {
				return INPUT_ERROR_MESSAGE;
			}else if(spaceCount(str,CHAR_SPACE_MESSAGE) != menberCount-1) {
				return NUMBER_OF_PEOPLE_ERROR_MESSAGE;}
			break;
		case 4: 
			if(str.isEmpty() || spaceCount(str,CHAR_SPACE_MESSAGE) != 1) return INPUT_ERROR_MESSAGE;
			break;
		case 5: 
			menberAndPrice = str.toLowerCase().split(SPACE_MESSAGE);
			if(!result.containsKey(menberAndPrice[0])) {
				return NAME_OF_PERSON_ERROR_MESSAGE;
			}else if(!menberAndPrice[1].matches(INTEGER_CHECK)) {
				return AMOUNT_OF_MONEY_ERROR_MESSAGE;
			}
			break;
		}
		return SPACE_CHARACTER_MESSAGE;

	}

	/**
	 * 入力された名前の数をスペースの数でカウントするメソッド
	 * @param str　カウント対象文字列
	 * @param target　検索対象（スペース）
	 * @return　カウント数
	 */
	public static int spaceCount(String str, char target){

		int count = 0;

		for(char x: str.toCharArray()){
			if(x == target){
				count++;
			}
		}
		return count;
	}

	/**
	 * 書籍購入費の高い順に並べ替えするメソッド
	 * @param result　並び替え前のMap 
	 * @return　並び替え後の
	 */
	public static Map<String, Integer> sortMap(Map<String, Integer> result){

		List<Integer> list = new ArrayList<>(result.values());
		List<String> list2 = new ArrayList<>();
		Map<String,Integer> sortedResult = new LinkedHashMap<>();

		Collections.sort(list,Comparator.reverseOrder());
		for (int i=0; i<list.size(); i++) {
			for (Map.Entry<String,Integer> entry : result.entrySet()) {
				if (list.get(i)==entry.getValue() && list2.contains(entry.getKey())==false) {
					sortedResult.put(entry.getKey(),entry.getValue());
					list2.add(entry.getKey());
					break;
				}
			}
		}
		return sortedResult;
	}

}
