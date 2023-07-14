# 블로그 테이블 생성 구문
CREATE TABLE blog(
	blog_id int auto_increment primary key,
	writer varchar(16) not null,
    blog_title varchar(200) not null,
    blog_content varchar(4000) not null,
    published_at datetime default now(),
    updated_at datetime default now(),
    blog_count int default 0
);
# 더미데이터 입력용 구문(테스트(dynamic_blog_test) DB에서만 사용합니다.)
INSERT INTO blog VALUES
    (null, '1번유저', '1번제목', '1번본문', now(), now(), null),
    (null, '2번유저', '2번제목', '2번본문', now(), now(), null),
    (null, '3번유저', '3번제목', '3번본문', now(), now(), null);

# 더미데이터 입력용 구문(개발(dynamic_blog_dev) DB에서만 사용합니다.)
INSERT INTO blog VALUES (null, "더미데이터본문!!!", 0, "더미데이터제목!!!", now(), now(), "더미글쓴이");

INSERT INTO blog(blog_content, blog_count, blog_title, published_at, updated_at, writer)
	(SELECT blog_content, blog_count, blog_title, now(), now(), writer FROM blog); # 더미데이터를 2개씩 증식시키는 구문