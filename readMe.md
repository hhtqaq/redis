# 本工程是为了实现redis操作库统一


## 1 需求来源

&emsp;&emsp;当前公司的项目存在三套Redis库：

 1. jedis2.9.0--基本操作，支持集群；
 2. redisson 3.5.0--仅用到分布式锁；
 3. spring-data-redis--仅用到事件发布
            
## 2 需求描述

&emsp;&emsp;对比3者，选择一套库实现redis当前需要的所有功能：
 1. 集群的基础操作
 2. 分布式锁
 3. 事件发布 
            


