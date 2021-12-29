##系统说明
此模块存放业务API代码，更改数据库配置和redis配置可直接运行
测试步骤
1、使用接口测试工具(例如postman)访问登录接口
接口地址：http://localhost:port/base/login
请求方式：POST
请求参数：username=sysadmin 
          password=123456
返回数据：{
                 "msg": "success,return Token",
                 "userInfo": "{\"id\":\"1\",\"userName\":\"超级管理员\",\"userId\":\"sysadmin\"}",
                 "success": true,
                 "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyaWQiOiJzeXNhZG1pbiIsInN1YiI6InN5c2FkbWluIiwiZXhwIjoxNjE2NTUwMzM5fQ.7IdGY0Pdgz59ztQPYZelaY41VqRQ7OO1Gy8Dc3nt0gI9Oo4Li__fXRvFtikqYUxz1wpX-hbfkjNX9IO64QrAPQ",
                 "msgCode": 0
            }
2、调用测试业务接口(源代码：com.xk.api.DemoController)
接口地址：http://localhost:port/demo/api/getAll
请求方式：GET
请求参数(Headers)：Authorization=登录接口中返回的accessToken值 
返回数据：{
         "msgCode": 0,
         "msg": "成功",
         "data": [
             {
                 "userid": "user0",
                 "name": "name0",
                 "gender": "男",
                 "birthday": "2021-03-19T01:48:52.534+0000",
                 "mobile": "13112341230"
             },
             {
                 "userid": "user1",
                 "name": "name1",
                 "gender": "男",
                 "birthday": "2021-03-19T01:48:52.534+0000",
                 "mobile": "13112341231"
             },
             {
                 "userid": "user2",
                 "name": "name2",
                 "gender": "男",
                 "birthday": "2021-03-19T01:48:52.534+0000",
                 "mobile": "13112341232"
             },
             {
                 "userid": "user3",
                 "name": "name3",
                 "gender": "男",
                 "birthday": "2021-03-19T01:48:52.534+0000",
                 "mobile": "13112341233"
             },
             {
                 "userid": "user4",
                 "name": "name4",
                 "gender": "男",
                 "birthday": "2021-03-19T01:48:52.534+0000",
                 "mobile": "13112341234"
             },
             {
                 "userid": "user5",
                 "name": "name5",
                 "gender": "男",
                 "birthday": "2021-03-19T01:48:52.534+0000",
                 "mobile": "13112341235"
             },
             {
                 "userid": "user6",
                 "name": "name6",
                 "gender": "男",
                 "birthday": "2021-03-19T01:48:52.534+0000",
                 "mobile": "13112341236"
             },
             {
                 "userid": "user7",
                 "name": "name7",
                 "gender": "男",
                 "birthday": "2021-03-19T01:48:52.534+0000",
                 "mobile": "13112341237"
             },
             {
                 "userid": "user8",
                 "name": "name8",
                 "gender": "男",
                 "birthday": "2021-03-19T01:48:52.534+0000",
                 "mobile": "13112341238"
             },
             {
                 "userid": "user9",
                 "name": "name9",
                 "gender": "男",
                 "birthday": "2021-03-19T01:48:52.534+0000",
                 "mobile": "13112341239"
             }
         ],
         "object": null,
         "total": 10,
         "totalPage": 0,
         "currPage": 0
     }