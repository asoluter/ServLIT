--Create table of users

CREATE TABLE public.users
(
    user_id SERIAL PRIMARY KEY NOT NULL,
    login TEXT NOT NULL,
    pass TEXT NOT NULL,
    mail TEXT NOT NULL,
    name TEXT NOT NULL,
    birth DATE NOT NULL,
    checked BOOLEAN NOT NULL,
    hash TEXT NOT NULL
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

--Create table of ansvers

CREATE TABLE public.answers
(
  ans_id SERIAL PRIMARY KEY NOT NULL,
  test_id INT NOT NULL,
  ans_text TEXT NOT NULL
);
ALTER TABLE public.ansvers
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

--Create table of right ansvers

CREATE TABLE public.rans
(
  rans_id SERIAL PRIMARY KEY NOT NULL,
  cont_id INT NOT NULL,
  test_id INT NOT NULL,
  rans INT NOT NULL
);
ALTER TABLE public.rans
ADD CONSTRAINT unique_rans_id UNIQUE (rans_id);