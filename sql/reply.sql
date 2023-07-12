## 댓글 테이블 설정
CREATE TABLE reply(
	reply_id int primary key auto_increment,
    blog_id int not null,
    reply_writer varchar(40) not null,
    reply_content varchar(200) not null,
    published_at datetime default now(),
    updated_at datetime default now()
);
# 외래키 설정(트래픽을 잘 버틸 수 있게)
# blog_id에는 기존에 존재하는 글의 blog_id만 들어가야 한다.
alter table reply add constraint fk_reply foreign key (blog_id) references blog(blog_id);

# 더미 데이터 입력(테스트 DB에서만 사용합니다.)
INSERT INTO reply VALUES(null, 2, "댓글쓴이", "1빠댓글", now(), now()),
(null, 2, "미미", "2빠댓글", now(), now()),
(null, 2, "릴리", "3빠댓글", now(), now()),
(null, 2, "슈슈", "4빠댓글", now(), now()),
(null, 3, "개발고수", "1빠댓글", now(), now());