INSERT INTO members (username, password, email, phone, role, created_at,active)
VALUES (
           'admin',
           '$2a$10$1l8nmvC2FlmUCjyKZhGPUukJuDvhIkW13xGhLPemowcHd24vD6EIK', -- 비밀번호: admin1234
           'admin@example.com',
           '01000000000',
           'ADMIN',
           NOW(),
            TRUE
       );
INSERT INTO members (username, password, email, phone, role, created_at,active)
VALUES (
           'test',
           '$2a$10$n13PttciPaI4PEXCxG4pVedp19X4c5Zi6hsrD1FVZ23LnKQeHBXAa', -- 비밀번호: admin1234
           'test@example.com',
           '01011111111',
           'USER',
           NOW(),
           TRUE
       );
INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('갤럭시 S24', 1090000, '삼성 최신 플래그십 스마트폰', 'galaxy_s24.jpg', 30, 3);

INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('아이폰 15', 1190000, '애플 최신형 스마트폰', 'iphone15.jpg', 25, 3);
INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('맥북 에어 M2', 1490000, '애플 실리콘 기반 고성능 노트북', 'macbook_air_m2.jpg', 15, 4);

INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('LG 그램 17', 1890000, '초경량 대화면 노트북', 'lg_gram_17.jpg', 10, 4);
INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('남성 오버핏 후드티', 39000, '남녀공용 오버핏 후드티', 'mens_hoodie.jpg', 80, 5);

INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('남성 와이드 데님 팬츠', 49000, '트렌디한 와이드핏 청바지', 'mens_jeans.jpg', 60, 5);
INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('여성 크롭 니트', 29000, '겨울철 인기 크롭 스타일 니트', 'womens_knit.jpg', 70, 6);

INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('여성 롱 플레어 스커트', 35000, '겨울/봄 인기 롱 플레어 스커트', 'womens_skirt.jpg', 50, 6);
INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('게이밍 마우스', 29000, 'RGB LED가 포함된 고성능 게이밍 마우스', 'mouse.jpg', 100, 1);

INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('기계식 키보드', 79000, '청축 기반 LED 백라이트 기계식 키보드', 'keyboard.jpg', 50, 1);
INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('블루투스 스피커', 45000, '휴대용 고음질 블루투스 스피커', 'speaker.jpg', 90, 1);

INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('액티브 노이즈캔슬링 헤드셋', 129000, '게이밍 및 음악 감상용 고품질 헤드셋', 'headset.jpg', 40, 1);
INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('강화유리 액정보호필름', 12000, '9H 강화유리 스마트폰 보호필름', 'glass_film.jpg', 200, 3);

INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('고속 무선 충전기', 32000, '15W 고속 무선충전 패드', 'wireless_charger.jpg', 150, 3);

-- 상위 카테고리
INSERT INTO category (id, name, parent_id) VALUES (1, '전자제품', NULL);
INSERT INTO category (id, name, parent_id) VALUES (2, '패션', NULL);

-- 하위 카테고리
INSERT INTO category (id, name, parent_id) VALUES (3, '스마트폰', 1);
INSERT INTO category (id, name, parent_id) VALUES (4, '노트북', 1);
INSERT INTO category (id, name, parent_id) VALUES (5, '남성의류', 2);
INSERT INTO category (id, name, parent_id) VALUES (6, '여성의류', 2);
