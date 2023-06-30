package middle7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelProcessMain {
    public static void main (String args[]){
    	
    	//スレッドを準備する
    	ExecutorService execService = null;
        execService = Executors.newCachedThreadPool();
        
        // 処理対象のリスト
        List<String> list = new ArrayList<>();
        // とりあえず5個あるものとする
        for(int i = 0; i < 5; i++){
            list.add("処理対象" + (i +1));
        }

        // 処理開始の時間(ミリ秒)取得
        long startTime = System.currentTimeMillis();

        // 処理結果受け取り用のリストを作成
        List<String> resultList = new ArrayList<>();
        
        // 処理対象を順番にサブ処理１～３に渡して処理実行
        for(String proc : list){
        	/**
        	* 修正前
            resultList.add(new ParallelProcessSub1(proc).process());
            resultList.add(new ParallelProcessSub2(proc).process());
            resultList.add(new ParallelProcessSub3(proc).process());
            */
        	
        	try {
        		//スレッドを実行する
        		Future<String> result1 = execService.submit(new ParallelProcessSub1(proc));
        		Future<String> result2 = execService.submit(new ParallelProcessSub2(proc));
        		Future<String> result3 = execService.submit(new ParallelProcessSub3(proc));
            	
        		//実行結果を取得する
            	resultList.add(result1.get());
            	resultList.add(result2.get());
            	resultList.add(result3.get());
            	
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        	
            System.out.println(proc + "実行済み");
        }
        //スレッドを終了する
        execService.shutdown();

        // 実行結果を取得する
        for(String result : resultList){
            try{
                System.out.println(result);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        // 処理終了の時間(ミリ秒)取得
        long endTime = System.currentTimeMillis();

        // 実行時間を算出(ミリ秒)
        System.out.println("実行時間： " + (endTime -startTime));
    }

}
