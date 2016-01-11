#!/usr/bin/env bash

#登录
curl -X POST -v http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=12345678&username=admin1 &grant_type=password&scope=read%20write&client_secret=f506d105142e2928e2e37675b560ff75&client_id=clientapp"


#获取比赛
curl -X POST -v http://localhost:8080/comp/filter -H "Authorization:bearer 52c4037f-861c-44a4-a76d-192403091b99" -d "startTime=0&endTime=14351607120041"

#获取自己管理的球队
curl -X POST -v http://localhost:8080/user/managed_teams -H "Authorization:bearer 52c4037f-861c-44a4-a76d-192403091b99"




