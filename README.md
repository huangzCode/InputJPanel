# InputJPanel
javaSwing写的简易面板输入法

代码中的Dao层，可以根据自己熟悉的框架进行修改，只需要一个查询所有的操作就可以了。


存在问题：
+根据Sql查询出来的结果，常用的字可能在最后一栏显示，而前面显示的会有不常用字体，不方便用户；
  （已解决，在数据表中添加新的字段，记录该字段的使用次数）
+因为开的线程去查询数据库，所以并不是所有输入一次后就立即进行查询。
  （已解决使用时，将数据库集成到项目中，如果项目访问的是你电脑上数据库或者远程数据库，会有“卡顿”错觉）
