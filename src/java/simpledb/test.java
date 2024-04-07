package simpledb;
import java.io.*;

public class test {

    public static void main(String[] argv) {

        // 构造一个包含3列的表结构
        Type types[] = new Type[]{ Type.INT_TYPE, Type.INT_TYPE, Type.INT_TYPE };
        String names[] = new String[]{ "field0", "field1", "field2" };
        TupleDesc descriptor = new TupleDesc(types, names);

        // 创建表，并将其与some_data_file.dat关联，
        // 同时将表的模式信息告知catalog
        HeapFile table1 = new HeapFile(new File("data.dat"), descriptor);
        Database.getCatalog().addTable(table1, "test");

        // 构建查询：我们使用简单的SeqScan，通过其迭代器逐条提供元组
        TransactionId tid = new TransactionId();
        SeqScan f = new SeqScan(tid, table1.getId());

        try {
            // 执行查询
            f.open();
            while (f.hasNext()) {
                Tuple tup = f.next();
                System.out.println(tup);
            }
            f.close();
            Database.getBufferPool().transactionComplete(tid);
        } catch (Exception e) {
            System.out.println ("Exception : " + e);
        }
    }

}