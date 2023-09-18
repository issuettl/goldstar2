select
x.sn, x.id, x.created as connected, y.created as signed, case when x.jammy_sn > 0 then 'O' else 'X' end as jammy, x.count
from (
SELECT
    b.sn, b.id, b.created, b.jammy_sn, count(a.member_sn) as count
FROM 
    lgecokr_goldstar_publ.tb_member as b
        left outer join 
    lgecokr_goldstar_publ.tb_sign as a
    on a.member_sn = b.sn
where b.sn > 2645
group by b.sn, b.id, b.created, b.jammy_sn
) x
left outer join (
select k.member_sn, k.id, k.created
from (
SELECT
    a.member_sn, b.id, b.created, row_number() over(partition by a.member_sn order by b.created asc) as n
FROM 
lgecokr_goldstar_publ.tb_sign as a
    right outer join lgecokr_goldstar_publ.tb_member as b
    on a.member_sn = b.sn
where a.member_sn > 2645
) k
where k.n = 1
) y
    on x.sn = y.member_sn
;





select
x.sn, x.id, x.created as connected, 
 case when x.gender = 'type1' then '남자'
    when x.gender = 'type2' then '여자'
    else '' end as gender
, case when x.age = 'type1' then '10대'
    when x.age = 'type2' then '20대'
    when x.age = 'type3' then '30대'
    when x.age = 'type4' then '40대'
    when x.age = 'type5' then '50대'
    when x.age = 'type6' then '60대이상'
    else '' end as age,
y.created as signed,
case when x.jammy_sn > 0 then 'O' else 'X' end as jammy, x.count
from (
SELECT
    b.sn, b.id, b.created, b.age, b.gender, b.jammy_sn, count(a.member_sn) as count
FROM 
    lgecokr_goldstar_publ.tb_member as b
        left outer join 
    lgecokr_goldstar_publ.tb_sign as a
    on a.member_sn = b.sn
where b.sn > 2645
and b.created >= '20230601000000'
and b.created <= '20230625235959'
group by b.sn, b.id, b.created, b.age, b.gender, b.jammy_sn
) x
left outer join (
select k.member_sn, k.id, k.created
from (
SELECT
    a.member_sn, b.id, b.created, row_number() over(partition by a.member_sn order by b.created asc) as n
FROM 
lgecokr_goldstar_publ.tb_sign as a
    right outer join lgecokr_goldstar_publ.tb_member as b
    on a.member_sn = b.sn
where a.member_sn > 2645
) k
where k.n = 1
) y
    on x.sn = y.member_sn
;







select
a.member_sn, b.id, a.created
, case when b.gender = 'type1' then '남자'
    when b.gender = 'type2' then '여자'
    else '' end as gender
, case when b.age = 'type1' then '10대'
    when b.age = 'type2' then '20대'
    when b.age = 'type3' then '30대'
    when b.age = 'type4' then '40대'
    when b.age = 'type5' then '50대'
    when b.age = 'type6' then '60대이상'
    else '' end as age
, case when a.worry_ty = 'type1' then '믿음과 신뢰의 고민'
    when a.worry_ty = 'type2' then '치열한 일상의 고민'
    when a.worry_ty = 'type3' then '건강한 삶에 대한 고민'
    when a.worry_ty = 'type4' then '미래에 대한 고민'
    when a.worry_ty = 'type5' then '도전과 용기에 대한 고민'
    when a.worry_ty = 'type6' then '잃어버린 꿈에 대한 고민'
    else '' end as worry_ty
, case when o.staff_check = 'present' then 'O' else 'X' end as mind_created
, case when o.ty = 'type1' then '엽채류'
       when o.ty = 'type2' then '허브류'
       when o.ty = 'type3' then '화훼류'
       else '' end as mind_ty
, o2.nm as mind_nm
, case when p.staff_check = 'present' then 'O' else 'X' end as indiv_created
, case when p.ty = 'type1' then '모던'
       when p.ty = 'type2' then '베이직'
       when p.ty = 'type3' then '트렌디'
       when p.ty = 'type4' then '힙'
       else '' end as indiv_ty
, p2.nm as indiv_nm
, q.created as style_created, q.ty as style_ty
, r.created as mood_created, r.ty as mood_ty
, t.created as refresh_created
, s.staff_created as life_created, s.status
from 
lgecokr_goldstar_publ.tb_sign a
    inner join
        lgecokr_goldstar_publ.tb_member b
            on a.member_sn = b.sn
    left outer join 
        lgecokr_goldstar_publ.tb_mind_part o
            on a.member_sn = o.sign_member_sn
            and a.created = o.sign_created
    left outer join
        lgecokr_goldstar_publ.tb_mind_member o1
            on o.sn = o1.part_sn
    left outer join
        lgecokr_goldstar_publ.tb_mind_answer o2
            on o1.answer_sn = o2.sn
    left outer join 
        lgecokr_goldstar_publ.tb_indiv_part p
            on a.member_sn = p.sign_member_sn
            and a.created = p.sign_created
    left outer join
        lgecokr_goldstar_publ.tb_indiv_member p1
            on p.sn = p1.part_sn
    left outer join
        lgecokr_goldstar_publ.tb_indiv_answer p2
            on p1.answer_sn = p2.sn
    left outer join 
        lgecokr_goldstar_publ.tb_style_part q
            on a.member_sn = q.sign_member_sn
            and a.created = q.sign_created
    left outer join 
        lgecokr_goldstar_publ.tb_mood_part r
            on a.member_sn = r.sign_member_sn
            and a.created = r.sign_created
    left outer join 
        lgecokr_goldstar_publ.tb_refresh_part t
            on a.member_sn = t.sign_member_sn
            and a.created = t.sign_created
    left outer join 
        lgecokr_goldstar_publ.tb_life_part s
            on a.member_sn = s.member_sn
            and a.created = s.`date`
            and s.status = 'status4'
            -- and s.staff_check = 'present'
where a.created in ('20230211', '20230212', '20230213', '20230214')
and a.worry_ty is not null
-- and s.staff_check is not null
order by a.created, a.member_sn
;





select
a.member_sn, b.id, a.created
, case when b.gender = 'type1' then '남자'
    when b.gender = 'type2' then '여자'
    else '' end as gender
, case when b.age = 'type1' then '10대'
    when b.age = 'type2' then '20대'
    when b.age = 'type3' then '30대'
    when b.age = 'type4' then '40대'
    when b.age = 'type5' then '50대'
    when b.age = 'type6' then '60대이상'
    else '' end as age
, case when a.worry_ty = 'type1' then '믿음과 신뢰의 고민'
    when a.worry_ty = 'type2' then '치열한 일상의 고민'
    when a.worry_ty = 'type3' then '건강한 삶에 대한 고민'
    when a.worry_ty = 'type4' then '미래에 대한 고민'
    when a.worry_ty = 'type5' then '도전과 용기에 대한 고민'
    when a.worry_ty = 'type6' then '잃어버린 꿈에 대한 고민'
    else '' end as worry_ty
, case when o.ty = 'type1' then '엽채류'
       when o.ty = 'type2' then '허브류'
       when o.ty = 'type3' then '화훼류'
       else '' end as mind_ty
, (select o2.nm from lgecokr_goldstar_publ.tb_mind_member o1 inner join lgecokr_goldstar_publ.tb_mind_answer o2 on o1.answer_sn = o2.sn where o.sn = o1.part_sn and o2.mind_sn = 1) as mind1
, (select o2.nm from lgecokr_goldstar_publ.tb_mind_member o1 inner join lgecokr_goldstar_publ.tb_mind_answer o2 on o1.answer_sn = o2.sn where o.sn = o1.part_sn and o2.mind_sn = 2) as mind2
, (select o2.nm from lgecokr_goldstar_publ.tb_mind_member o1 inner join lgecokr_goldstar_publ.tb_mind_answer o2 on o1.answer_sn = o2.sn where o.sn = o1.part_sn and o2.mind_sn = 3) as mind3
, (select o2.nm from lgecokr_goldstar_publ.tb_mind_member o1 inner join lgecokr_goldstar_publ.tb_mind_answer o2 on o1.answer_sn = o2.sn where o.sn = o1.part_sn and o2.mind_sn = 4) as mind4
, (select o2.nm from lgecokr_goldstar_publ.tb_mind_member o1 inner join lgecokr_goldstar_publ.tb_mind_answer o2 on o1.answer_sn = o2.sn where o.sn = o1.part_sn and o2.mind_sn = 5) as mind5
, (select o2.nm from lgecokr_goldstar_publ.tb_mind_member o1 inner join lgecokr_goldstar_publ.tb_mind_answer o2 on o1.answer_sn = o2.sn where o.sn = o1.part_sn and o2.mind_sn = 6) as mind6
, (select o2.nm from lgecokr_goldstar_publ.tb_mind_member o1 inner join lgecokr_goldstar_publ.tb_mind_answer o2 on o1.answer_sn = o2.sn where o.sn = o1.part_sn and o2.mind_sn = 7) as mind7
, (select o2.nm from lgecokr_goldstar_publ.tb_mind_member o1 inner join lgecokr_goldstar_publ.tb_mind_answer o2 on o1.answer_sn = o2.sn where o.sn = o1.part_sn and o2.mind_sn = 8) as mind8
, (select o2.nm from lgecokr_goldstar_publ.tb_mind_member o1 inner join lgecokr_goldstar_publ.tb_mind_answer o2 on o1.answer_sn = o2.sn where o.sn = o1.part_sn and o2.mind_sn = 9) as mind9
, case when o.staff_check = 'present' then 'O' else 'X' end as mind_created
, case when p.ty = 'type1' then '모던'
       when p.ty = 'type2' then '베이직'
       when p.ty = 'type3' then '트렌디'
       when p.ty = 'type4' then '힙'
       else '' end as indiv_ty
, (select p2.nm from lgecokr_goldstar_publ.tb_indiv_member p1 inner join lgecokr_goldstar_publ.tb_indiv_answer p2 on p1.answer_sn = p2.sn where p.sn = p1.part_sn and p2.indiv_sn = 1) as indiv1
, (select p2.nm from lgecokr_goldstar_publ.tb_indiv_member p1 inner join lgecokr_goldstar_publ.tb_indiv_answer p2 on p1.answer_sn = p2.sn where p.sn = p1.part_sn and p2.indiv_sn = 2) as indiv2
, (select p2.nm from lgecokr_goldstar_publ.tb_indiv_member p1 inner join lgecokr_goldstar_publ.tb_indiv_answer p2 on p1.answer_sn = p2.sn where p.sn = p1.part_sn and p2.indiv_sn = 3) as indiv3
, (select p2.nm from lgecokr_goldstar_publ.tb_indiv_member p1 inner join lgecokr_goldstar_publ.tb_indiv_answer p2 on p1.answer_sn = p2.sn where p.sn = p1.part_sn and p2.indiv_sn = 4) as indiv4
, (select p2.nm from lgecokr_goldstar_publ.tb_indiv_member p1 inner join lgecokr_goldstar_publ.tb_indiv_answer p2 on p1.answer_sn = p2.sn where p.sn = p1.part_sn and p2.indiv_sn = 5) as indiv5
, (select p2.nm from lgecokr_goldstar_publ.tb_indiv_member p1 inner join lgecokr_goldstar_publ.tb_indiv_answer p2 on p1.answer_sn = p2.sn where p.sn = p1.part_sn and p2.indiv_sn = 6) as indiv6
, case when p.staff_check = 'present' then 'O' else 'X' end as indiv_created
, case when q.ty = 'type1' then '시크한'
       when q.ty = 'type2' then '심플한'
       when q.ty = 'type3' then '유니크한'
       when q.ty = 'type4' then '차분한'
       else '' end as style_ty
, (select p2.nm from lgecokr_goldstar_publ.tb_style_member p1 inner join lgecokr_goldstar_publ.tb_style_answer p2 on p1.answer_sn = p2.sn where q.sn = p1.part_sn and p2.style_sn = 1) as style1
, (select p2.nm from lgecokr_goldstar_publ.tb_style_member p1 inner join lgecokr_goldstar_publ.tb_style_answer p2 on p1.answer_sn = p2.sn where q.sn = p1.part_sn and p2.style_sn = 2) as style2
, (select p2.nm from lgecokr_goldstar_publ.tb_style_member p1 inner join lgecokr_goldstar_publ.tb_style_answer p2 on p1.answer_sn = p2.sn where q.sn = p1.part_sn and p2.style_sn = 3) as style3
, (select p2.nm from lgecokr_goldstar_publ.tb_style_member p1 inner join lgecokr_goldstar_publ.tb_style_answer p2 on p1.answer_sn = p2.sn where q.sn = p1.part_sn and p2.style_sn = 4) as style4
, (select p2.nm from lgecokr_goldstar_publ.tb_style_member p1 inner join lgecokr_goldstar_publ.tb_style_answer p2 on p1.answer_sn = p2.sn where q.sn = p1.part_sn and p2.style_sn = 5) as style5
, (select p2.nm from lgecokr_goldstar_publ.tb_style_member p1 inner join lgecokr_goldstar_publ.tb_style_answer p2 on p1.answer_sn = p2.sn where q.sn = p1.part_sn and p2.style_sn = 6) as style6
, (select p2.nm from lgecokr_goldstar_publ.tb_style_member p1 inner join lgecokr_goldstar_publ.tb_style_answer p2 on p1.answer_sn = p2.sn where q.sn = p1.part_sn and p2.style_sn = 7) as style7
, (select p2.nm from lgecokr_goldstar_publ.tb_style_member p1 inner join lgecokr_goldstar_publ.tb_style_answer p2 on p1.answer_sn = p2.sn where q.sn = p1.part_sn and p2.style_sn = 8) as style8
, case when q.staff_check = 'present' then 'O' else 'X' end as style_created
, (select p2.nm from lgecokr_goldstar_publ.tb_mood_member p1 inner join lgecokr_goldstar_publ.tb_mood_answer p2 on p1.answer_sn = p2.sn where r.sn = p1.part_sn and p2.mood_sn = 1) as mood1
, (select p2.nm from lgecokr_goldstar_publ.tb_mood_member p1 inner join lgecokr_goldstar_publ.tb_mood_answer p2 on p1.answer_sn = p2.sn where r.sn = p1.part_sn and p2.mood_sn = 2) as mood2
, (select p2.nm from lgecokr_goldstar_publ.tb_mood_member p1 inner join lgecokr_goldstar_publ.tb_mood_answer p2 on p1.answer_sn = p2.sn where r.sn = p1.part_sn and p2.mood_sn = 3) as mood3
, (select p2.nm from lgecokr_goldstar_publ.tb_mood_member p1 inner join lgecokr_goldstar_publ.tb_mood_answer p2 on p1.answer_sn = p2.sn where r.sn = p1.part_sn and p2.mood_sn = 4) as mood4
, (select p2.nm from lgecokr_goldstar_publ.tb_mood_member p1 inner join lgecokr_goldstar_publ.tb_mood_answer p2 on p1.answer_sn = p2.sn where r.sn = p1.part_sn and p2.mood_sn = 5) as mood5
, (select p2.nm from lgecokr_goldstar_publ.tb_mood_member p1 inner join lgecokr_goldstar_publ.tb_mood_answer p2 on p1.answer_sn = p2.sn where r.sn = p1.part_sn and p2.mood_sn = 6) as mood6
, (select p2.nm from lgecokr_goldstar_publ.tb_mood_member p1 inner join lgecokr_goldstar_publ.tb_mood_answer p2 on p1.answer_sn = p2.sn where r.sn = p1.part_sn and p2.mood_sn = 7) as mood7
, case when r.staff_check = 'present' then 'O' else 'X' end as mood_created
, case when t.staff_check = 'present' then 'O' else 'X' end as refresh_created
, case when s.staff_check = 'present' then 'O' else 'X' end as life_created
from 
lgecokr_goldstar_publ.tb_sign a
    inner join
        lgecokr_goldstar_publ.tb_member b
            on a.member_sn = b.sn
    left outer join 
        lgecokr_goldstar_publ.tb_mind_part o
            on a.member_sn = o.sign_member_sn
            and a.created = o.sign_created
    left outer join 
        lgecokr_goldstar_publ.tb_indiv_part p
            on a.member_sn = p.sign_member_sn
            and a.created = p.sign_created
    left outer join 
        lgecokr_goldstar_publ.tb_style_part q
            on a.member_sn = q.sign_member_sn
            and a.created = q.sign_created
    left outer join 
        lgecokr_goldstar_publ.tb_mood_part r
            on a.member_sn = r.sign_member_sn
            and a.created = r.sign_created
    left outer join 
        lgecokr_goldstar_publ.tb_refresh_part t
            on a.member_sn = t.sign_member_sn
            and a.created = t.sign_created
    left outer join 
        lgecokr_goldstar_publ.tb_life_part s
            on a.member_sn = s.member_sn
            and a.created = s.`date`
            and s.status = 'status4'
            -- and s.staff_check = 'present'
where b.sn > 2645
and a.created >= '20230424'
and a.created <= '20230523'
and a.worry_ty is not null
-- and s.staff_check is not null
order by a.created, a.member_sn
