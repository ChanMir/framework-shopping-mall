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
INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('게이밍 마우스', 29000, 'RGB LED가 포함된 고성능 게이밍 마우스', 'mouse.jpg', 100, 1);

INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('기계식 키보드', 79000, '청축 기반 LED 백라이트 기계식 키보드', 'keyboard.jpg', 50, 1);

INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('블루투스 이어폰', 59000, '노이즈 캔슬링 지원 블루투스 이어폰', 'earbuds.jpg', 70, 2);

INSERT INTO products (name, price, description, image_url, stock, category_id)
VALUES ('휴대용 보조배터리', 25000, '10000mAh 고속 충전 지원 보조배터리', 'battery.jpg', 120, 3);
