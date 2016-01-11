#获取movie这条路的posterId
SELECT p.id as poster_id FROM  qimei_poster p right join qimei_follow f on p.movie_id=f.movie_id where f.user_id=1 and f.type='MOVIE';


#获取actor这条路的posterId
select distinct(l.poster_id) from qimei_link_actor_poster l right join qimei_follow f on l.actor_id=f.actor_id where f.user_id=1 and f.type='ACTOR'; 


#选出所有的posterId
(SELECT p.id as poster_id FROM  qimei_poster p right join qimei_follow f on p.movie_id=f.movie_id where f.user_id=1 and f.type='MOVIE')
union
(select distinct(l.poster_id) from qimei_link_actor_poster l right join qimei_follow f on l.actor_id=f.actor_id where f.user_id=1 and f.type='ACTOR')
;

#组合前面两个表
select qimei_poster.* from qimei_poster inner join
(
(SELECT p.id as poster_id FROM  qimei_poster p right join qimei_follow f on p.movie_id=f.movie_id where f.user_id=1 and f.type='MOVIE')

union

(select distinct(l.poster_id) from qimei_link_actor_poster l right join qimei_follow f on l.actor_id=f.actor_id where f.user_id=1 and f.type='ACTOR')
)
pidtable
on qimei_poster.id=pidtable.poster_id

where qimei_poster.status='OK'
order by qimei_poster.create_time DESC
limit 0,3
;