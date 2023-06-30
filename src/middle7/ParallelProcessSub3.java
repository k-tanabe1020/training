package middle7;

public class ParallelProcessSub3 implements ProcessSub{
    private String procName;
    private String result = "サブ処理③";
    public ParallelProcessSub3(String proc) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.procName = proc;
    }
    @Override
    public String process() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.procName + "-" + this.result;
    }

    @Override
    public String call() throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        return process();
    }
}
