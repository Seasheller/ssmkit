## 说明

```
- ssmkit
  - common      //该module是公共基础模块
    - dao
      - BaseDao.java     //基础DAO类接口，该类继承通用mapper的一些接口，提供基础的增删改查功能接口
    - entity
      - BaseEntity.java  //基础实体类，定义一些数据共有字段
    - service
      - impl
        - BaseServiceImpl.java //基础Service实现类 提供一些常用的增删改查功能
      - BaseService.java //基础Service类接口
    - utils
      - SpringSecurityUtil.java  // Spring Security 全局工具类
      - SpringUtil.java          // Spring 全局工具类
  - admin       //该module是web模块 
```
    