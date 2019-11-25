--Create table of users

CREATE TABLE public.users
(
    user_id SERIAL PRIMARY KEY NOT NULL,
    login TEXT NOT NULL,
    pass TEXT NOT NULL,
    mail TEXT NOT NULL,
    name TEXT NOT NULL,
    birth DATE NOT NULL,
    checked BOOLEAN NOT NULL
);
ALTER TABLE public.users
 ADD CONSTRAINT unique_user_id UNIQUE (user_id);
ALTER TABLE public.users
 ADD CONSTRAINT unique_login UNIQUE (login);
ALTER TABLE public.users
 ADD CONSTRAINT unique_mail UNIQUE (mail);

--Create table of contests

CREATE TABLE public.contests
(
  cont_id SERIAL PRIMARY KEY NOT NULL,
  name TEXT NOT NULL,
  ending DATE NOT NULL,
  available BOOLEAN NOT NULL
);
ALTER TABLE public.contests
ADD CONSTRAINT unique_cont_id UNIQUE (cont_id);

--Create table of tests

CREATE TABLE public.tests
(
  test_id SERIAL PRIMARY KEY NOT NULL,
  cont_id INT NOT NULL,
  test_name TEXT NOT NULL,
  quest TEXT NOT NULL
);
ALTER TABLE public.tests
ADD CONSTRAINT unique_test_id UNIQUE (test_id);

--Create table of answers

CREATE TABLE public.answers
(
  ans_id SERIAL PRIMARY KEY NOT NULL,
  test_id INT NOT NULL,
  ans_text TEXT NOT NULL,
  correct BOOLEAN NOT NULL
);
ALTER TABLE public.answers
ADD CONSTRAINT unique_ans_id UNIQUE (ans_id);

--Create table of results

CREATE TABLE public.results
(
  result_id SERIAL PRIMARY KEY NOT NULL,
  cont_id INT NOT NULL,
  user_id INT NOT NULL,
  res_text TEXT NOT NULL,
  result INT NOT NULL,
  sent DATE
);
ALTER TABLE public.results
ADD CONSTRAINT unique_result_id UNIQUE (result_id);

-- Table users {
--   user_id int [pk]
--   login varchar
--   pass varchar
--   mail varchar
--   name varchar
--   birth date
--   checked boolean
-- }
--
-- Table contests {
--   cont_id int [pk]
--   name varchar
--   ending date
--   enabled boolean
-- }
--
-- Table tests {
--   test_id int [pk]
--   cont_id int [ref: > contests.cont_id]
--   test_name varchar
--   quest varchar
-- }
--
-- Table answers {
--   ans_id int [pk]
--   test_id int [ref: > tests.test_id]
--   ans_text varchar
--   correct boolean
-- }
--
-- Table results {
--   result_id int [pk]
--   cont_id int [ref: > contests.cont_id]
--   user_id int [ref: > users.user_id]
--   res_text varchar
--   result int
--   sent date
-- }
--
