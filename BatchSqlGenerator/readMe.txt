此工具有4个参数:  
1. 模板sql,以{DATE}为占位符
2. 生成文件的前缀: 例如输入 client_flow_record, 则生成的文件为: client_flow_record_1.sql, client_flow_record_2.sql
3. 交易日串, 以逗号分隔
4. 分页之每个文件中sql条数. 

以执行bat命令启动工具即可.



-----------------------------------------  下面是参数demo

-- sql 参数　需要通过 {DATE} 作为占位符, sql输入完成后. 必须在新的一行输入 "end", 作为结束关键字命令.
select * from client_flow_record where create_at < '{DATE}';
end;

-- 文件名称
client_flow_record


-- 日期参数: 
20160311,20160314,20160315,20160316,20160317


-- 分页大小:
100
