##系统说明
此脚手架项目已依赖基础平台安全模块,具体的使用方法详见doc中的文档
   
##配置文件参数说明（以application-dev.properties为例）
#######################################################
#### 服务与应用配置
########################################################
server.port=8089   //应用端口
spring.application.name=XKES //应用名称

#######################################################
#### MYSQL数据库配置连接
########################################################
spring.jpa.database=MYSQL
spring.datasource.driverClassName=net.sf.log4jdbc.DriverSpy
spring.datasource.url=jdbc:log4jdbc:mysql://localhost:3306/xkes?useUnicode=true&characterEncoding=utf-8
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=none
spring.datasource.initialization-mode=never
spring.datasource.sqlScriptEncoding=UTF-8
spring.jpa.show-sql=false


#######################################################
#### Oracle数据库配置
########################################################
-#spring.jpa.database=oracle
-#spring.datasource.driverClassName=net.sf.log4jdbc.DriverSpy
-#spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
-#spring.datasource.username=XKES
-#spring.datasource.password=123456
-#spring.jpa.hibernate.ddl-auto=none
-#spring.datasource.sqlScriptEncoding=UTF-8
-#spring.jpa.show-sql=false
//使用oracle数据库时，springboot2.X默认支持12g以上版本，如果连接数据库低于该版本，需设置方言版本，如下设置：
-#spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.Oracle10gDialect


#######################################################
#### PostgreSQL数据库配置
########################################################
-#spring.datasource.platform=postgres
-#spring.datasource.driverClassName=org.postgresql.Driver
-#spring.datasource.url=jdbc:postgresql://localhost:5432/postgres?useSSL=false
-#spring.datasource.username=postgres
-#spring.datasource.password=123456
-#spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults = false
//方言配置须与数据库版本保持一致
-#spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL9Dialect
-#spring.jpa.show-sql=true

#######################################################
#### 文件上传配置
########################################################
spring.servlet.multipart.enabled=true 
spring.servlet.multipart.file-size-threshold=0
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
spring.servlet.multipart.resolve-lazily=false


#######################################################
#### 连接池配置
########################################################
spring.datasource.druid.initial-size=10
spring.datasource.druid.max-active=20
spring.datasource.druid.min-idle=5
spring.datasource.druid.max-wait=60000
//此为hibernate的ejb拦截器，用于增删改时记录操作人和操作时间，此配置不能更改
spring.jpa.properties.hibernate.ejb.interceptor=com.xk.platform.security.common.AuditInterceptor

#######################################################
#### REDIS 配置（用于存储字典及用户信息）
########################################################
#不开启redis，则配置如下
#xk.properties.redis-enable=false
#开启redis，则配置如下
spring.redis.database=12
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.timeout=100000ms
spring.redis.jedis.pool.max-idle=8
spring.redis.jedis.pool.min-idle=0
spring.redis.jedis.pool.max-active=8
spring.redis.jedis.pool.max-wait=-1ms
//在正式环境中建议redis端口不开放且设置redis密码，密码配置如下
-#spring.redis.password=yzdb123456

#######################################################
#### 日志配置
########################################################
logging.level.root=INFO
logging.level.org.springframework.web=INFO
logging.level.org.hibernate=INFO
logging.file=xkes/xkes.log
logging.config=classpath:logback-spring.xml

#######################################################
#### spring 静态资源缓存配置
########################################################
//正式环境建议开启此配置
#spring.resources.cache-period=864000
//正式环境建议设置为true
spring.thymeleaf.cache=false

#######################################################
#### 基础平台配置  文件上传服务说明详见doc/
########################################################
//授权文件存放目录
xk.properties.lic.file-dir=E:/XKESLIC
//授权文件名称
xk.properties.lic.file-name=XKES_XK_LZS.lic
//文件上传服务地址
xk.properties.resource.upload-server=http://localhost:8086
//文件上传秘钥（根据项目自定义配置）
xk.properties.resource.file-upload-key=XKES
//静态资源文件服务（nginx）地址
xk.properties.resource.file-server=http://localhost:8091/upload/
//固定配置，无需更改
xk.properties.jpa.enable=true
//用于前后端分离项目配置，无需更改
xk.properties.security.auth-type=XKESMobile
//spring扫描包，当项目包名未以com.xk开头时，例如包名为com.demo,则需启用如下配置
xk.properties.jpa.base-package=com.demo