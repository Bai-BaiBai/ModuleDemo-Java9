# java9新特性--模块系统

此demo使用的版本为java11

maven中的module与java的模块系统没有联系，每个模块的名称是唯一的

模块系统可以增加封装性，一个模块中只有被导出的包才可以被其他模块访问

默认情况下没有包被导出，通过模块声明文件(module-info.java)的exports来导出包
导出的包中的public和protect类型可以被依赖该模块的其他模块访问

***

exports [package]:
 - 只导出所声明的包中的类，子包中的类需要额外声明导出
 - export to [模块名称] ，指定可以访问的模块名称
 
requires [模块名称]:
  - 声明所依赖的包的名称
  - 不能出现重复模块
  - requires transitive [模块名称]:声明可以传递的依赖

**模块间的依赖没有传递关系**，例如B:requires A, C:requires B,但此时C不能使用A中的类，因为C不依赖A 

provide [接口全限定名] with [实现类全限定名]:
 - 此模块中实现了依赖模块中的接口，需要将它声明在此模块的配置文件中

runtime模块中添加了打包插件和assembly/runtime.xml打包配置信息