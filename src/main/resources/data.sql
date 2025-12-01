INSERT INTO members (username, password, email, phone, role, created_at)
VALUES (
           'admin',
           '$2a$10$1l8nmvC2FlmUCjyKZhGPUukJuDvhIkW13xGhLPemowcHd24vD6EIK', -- 비밀번호: admin1234
           'admin@example.com',
           '01000000000',
           'ADMIN',
           NOW()
       );
